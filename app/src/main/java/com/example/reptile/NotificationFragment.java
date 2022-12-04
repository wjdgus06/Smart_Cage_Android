package com.example.reptile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
    String string;

    Activity activity;

    //리스트뷰 변수
    ListViewAdapter adapter;

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

                while (true) {
                    data_temp = getJsonTempData();
                    data_hum = getJsonHumData();
                    data_bright = getJsonBrightData();

                    if (Integer.parseInt(data_temp) > 60) {
                        System.out.println("why?");
                        adapter.addItem(new NotiLVItem("RoRo 케이지의 온도를 확인하세요!"));
                    }
                    /*
                    if (Integer.parseInt(data_hum) > 60)
                        adapter.addItem(new NotiLVItem("RoRo 케이지의 습도를 확인하세요!"));
                    if (Integer.parseInt(data_bright) > 60)
                        adapter.addItem(new NotiLVItem("RoRo 케이지의 조도를 확인하세요!"));
                     */

                    //adapter.addItem(new ManLVItem(mid_list[i]));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewBinding.listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    try {
                        Thread.sleep(60000);
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

        String queryUrl = "http://182.221.64.162:7579/Mobius/test-ae-1/temperature/la";

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

        String queryUrl_hum = "http://182.221.64.162:7579/Mobius/test-ae-1/humidity/la";

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

        String queryUrl_bright = "http://182.221.64.162:7579/Mobius/test-ae-1/brightness/la";

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

}