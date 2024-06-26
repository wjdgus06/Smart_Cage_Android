package com.example.reptile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.reptile.databinding.ActivityCageInfoBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CageInfoActivity extends AppCompatActivity {

    ActivityCageInfoBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCageInfoBinding.inflate(getLayoutInflater());

        String repName = getIntent().getStringExtra("cageName");
        String grpName = getIntent().getStringExtra("typeName");
        viewBinding.cageName.setText(repName);

        if(grpName.equals("snake"))
            viewBinding.photoProfile.setImageResource(R.drawable.grp_snake_img_xml);
        else if (grpName.equals("turtle"))
            viewBinding.photoProfile.setImageResource(R.drawable.grp_turtle_img_xml);
        else if (grpName.equals("crocodile"))
            viewBinding.photoProfile.setImageResource(R.drawable.grp_crocodile_img_xml);
        else if (grpName.equals("dinosaur"))
            viewBinding.photoProfile.setImageResource(R.drawable.grp_dinosaur_img_xml);
        else if (grpName.equals("frog"))
            viewBinding.photoProfile.setImageResource(R.drawable.grp_frog_img_xml);

        viewBinding.btnBack.setOnClickListener(view -> {
            finish();
        });

        viewBinding.btnMoveSensor.setOnClickListener(view -> {
            Intent intent = new Intent(this, SensorActivity.class);
            intent.putExtra("cageName", repName);
            startActivity(intent);
        });

        viewBinding.btnPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecentPhotoActivity.class);
            intent.putExtra("cageName", repName);
            startActivity(intent);
        });

        viewBinding.btnUpdateVal.setOnClickListener(view -> new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String data = getJsonTempData(repName);
                    String data_hum = getJsonHumData(repName);
                    String data_bright = getJsonBrightData(repName);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewBinding.resultTemp.setText(data);
                            viewBinding.resultHum.setText(data_hum);
                            viewBinding.resultLig.setText(data_bright);
                        }
                    });
                    try {
                        Thread.sleep(300000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start());
        setContentView(viewBinding.getRoot());
    }


    String getJsonTempData(String cageName) {

        String response = "";
        String con = "";

        String queryUrl = "http://182.221.64.162:7579/Mobius/"+cageName+"/temperature/la";

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
        return con + " ℃";
    }


    String getJsonHumData(String cageName) {

        String response_hum = "";
        String con_hum = "";

        String queryUrl_hum = "http://182.221.64.162:7579/Mobius/"+cageName+"/humidity/la";

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
        return con_hum + "%";
    }

    String getJsonBrightData(String cageName){
        String response_bright = "";
        String con_bright = "";

        String queryUrl_bright = "http://182.221.64.162:7579/Mobius/"+cageName+"/brightness/la";

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
        return con_bright + "lx";
    }
}
