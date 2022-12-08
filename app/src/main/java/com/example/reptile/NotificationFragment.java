package com.example.reptile;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.reptile.databinding.FragmentListBinding;
import com.example.reptile.databinding.FragmentNotificationBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding viewBinding;
    private String TAG = this.getClass().getSimpleName();

    private SharedPreferences preferences;
    String data_temp, data_hum, data_bright;

    Activity activity;

    //리스트뷰 변수
    ListViewAdapter adapter;

    // Channel에 대한 id 생성
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    // Channel을 생성 및 전달해 줄 수 있는 Manager 생성
    private NotificationManager mNotificationManager;

    // Notification에 대한 ID 생성
    private static int NOTIFICATION_ID = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            activity = (Activity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentNotificationBinding.inflate(getLayoutInflater());
        adapter = new ListViewAdapter(); //리스트에 adpater 연결
        preferences = getContext().getSharedPreferences("shared_noti", Context.MODE_PRIVATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //adapter.clearItem();
                SharedPreferences.Editor editor = preferences.edit();

                //editor.putString("tv1", change_target[0]);

                createNotificationChannel();
                while (true) {
                    data_temp = getJsonTempData();
                    data_hum = getJsonHumData();
                    data_bright = getJsonBrightData();
                    NOTIFICATION_ID = 0;

                    if (Integer.parseInt(data_temp) > 0) {
                        adapter.addItem(new NotiLVItem("RoRo 케이지의 온도를 확인하세요!"));
                        sendNotification("RoRo 케이지의 온도를 확인하세요!");
                        System.out.println("noti1");

                    }

                    try {
                        Thread.sleep(1000); //1분 (5분으로 수정)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (Integer.parseInt(data_hum) > 0){
                        NOTIFICATION_ID = 1;
                        adapter.addItem(new NotiLVItem("RoRo 케이지의 습도를 확인하세요!"));
                        sendNotification("RoRo 케이지의 습도를 확인하세요!");
                        System.out.println("noti2");

                    }
                    try {
                        Thread.sleep(1000); //1분 (5분으로 수정)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Integer.parseInt(data_bright) > 0) {
                        NOTIFICATION_ID = 2;
                        adapter.addItem(new NotiLVItem("RoRo 케이지의 조도를 확인하세요!"));
                        sendNotification("RoRo 케이지의 조도를 확인하세요!");
                        System.out.println("noti3");

                    }


                    //adapter.addItem(new ManLVItem(mid_list[i]));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("InrunonUiThread");
                            viewBinding.listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    try {
                        Thread.sleep(300000); //1분 (5분으로 수정)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        viewBinding.btnDelete.setOnClickListener(new View.OnClickListener() { //알림 지우기 버튼
            @Override
            public void onClick(View view) {
                adapter.clearItem();
                viewBinding.listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        return viewBinding.getRoot();
    }




    String getJsonTempData() {

        String response = "";
        String con = "";

        String queryUrl = "http://182.221.64.162:7579/Mobius/roro/temperature/la";
        // ae이름 받아올 수 있게 변경해야 함... 알림은 한 개의 케이지에 대해서만 뜨도록 하기...어쩔 수 없어

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URL url = new URL(queryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("X-M2M-RI", "dashboard_testing");
            connection.setRequestProperty("X-M2M-Origin", "dashboard_testing");

            int responseCode = connection.getResponseCode();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
            bufferedReader.close();

            response = stringBuffer.toString();

            System.out.println(response);

            JSONObject jsonObject = new JSONObject(response).getJSONObject("m2m:cin");
            con = jsonObject.optString("con");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }


    String getJsonHumData() {

        String response_hum = "";
        String con_hum = "";

        String queryUrl_hum = "http://182.221.64.162:7579/Mobius/roro/humidity/la";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // humidity
            URL url_hum = new URL(queryUrl_hum);
            HttpURLConnection connection_hum = (HttpURLConnection) url_hum.openConnection();

            connection_hum.setRequestMethod("GET");
            connection_hum.setRequestProperty("Accept", "application/json");
            connection_hum.setRequestProperty("X-M2M-RI", "dashboard_testing");
            connection_hum.setRequestProperty("X-M2M-Origin", "dashboard_testing");

            int responseCode_hum = connection_hum.getResponseCode();

            BufferedReader bufferedReader_hum = new BufferedReader(new InputStreamReader(connection_hum.getInputStream()));
            StringBuffer stringBuffer_hum = new StringBuffer();
            String inputLine_hum;

            while ((inputLine_hum = bufferedReader_hum.readLine()) != null) {
                stringBuffer_hum.append(inputLine_hum);
            }
            bufferedReader_hum.close();

            response_hum = stringBuffer_hum.toString();

            System.out.println(response_hum);

            JSONObject jsonObject_hum = new JSONObject(response_hum).getJSONObject("m2m:cin");
            con_hum = jsonObject_hum.optString("con");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return con_hum;
    }

    String getJsonBrightData(){
        String response_bright = "";
        String con_bright = "";

        String queryUrl_bright = "http://182.221.64.162:7579/Mobius/roro/brightness/la";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // brightness
            URL url_bright = new URL(queryUrl_bright);
            HttpURLConnection connection_bright = (HttpURLConnection) url_bright.openConnection();

            connection_bright.setRequestMethod("GET");
            connection_bright.setRequestProperty("Accept", "application/json");
            connection_bright.setRequestProperty("X-M2M-RI", "dashboard_testing");
            connection_bright.setRequestProperty("X-M2M-Origin", "dashboard_testing");


            int responseCode_bright = connection_bright.getResponseCode();

            BufferedReader bufferedReader_bright = new BufferedReader(new InputStreamReader(connection_bright.getInputStream()));
            StringBuffer stringBuffer_bright = new StringBuffer();
            String inputLine_bright;

            while ((inputLine_bright = bufferedReader_bright.readLine()) != null) {
                stringBuffer_bright.append(inputLine_bright);
            }
            bufferedReader_bright.close();

            response_bright = stringBuffer_bright.toString();

            System.out.println(response_bright);

            JSONObject jsonObject_bright = new JSONObject(response_bright).getJSONObject("m2m:cin");
            con_bright = jsonObject_bright.optString("con");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con_bright;
    }


    /* 리스트뷰 어댑터 */
    public class ListViewAdapter extends BaseAdapter {
        ArrayList<NotiLVItem> items = new ArrayList<NotiLVItem>();


        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(NotiLVItem item) {
            items.add(item);
        }

        public void clearItem() {
            items.clear();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final NotiLVItem itemList = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.noti_lv_notilist, viewGroup, false);

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            TextView tv_cage = (TextView) convertView.findViewById(R.id.tv_noti);


            tv_cage.setText(itemList.getNoti());

            Log.d(TAG, "getView() - [ "+position+" ] "+itemList.getNoti());

            return convertView;  //뷰 객체 반환
        }
    }

    //채널을 만드는 메소드
    public void createNotificationChannel()
    {
        //notification manager 생성
        mNotificationManager = (NotificationManager)
                activity.getSystemService(NOTIFICATION_SERVICE);
        // 기기(device)의 SDK 버전 확인 ( SDK 26 버전 이상인지 - VERSION_CODES.O = 26)
        if(android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.O){
            //Channel 정의 생성자( construct 이용 )
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID
                    ,"Test Notification",mNotificationManager.IMPORTANCE_HIGH);
            //Channel에 대한 기본 설정
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            // Manager을 이용하여 Channel 생성
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

    }

    // Notification Builder를 만드는 메소드
    private NotificationCompat.Builder getNotificationBuilder(String str) {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(activity.getApplicationContext(), PRIMARY_CHANNEL_ID)
                .setContentTitle("알림")
                .setContentText(str)
                .setSmallIcon(R.drawable.btnlizard);
        return notifyBuilder;
    }

    // Notification을 보내는 메소드
    public void sendNotification(String str){
        // Builder 생성
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(str);
        // Manager를 통해 notification 디바이스로 전달
        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }



}