package com.example.reptile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reptile.databinding.ActivityManCageUpdateBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManCageUpdateActivity extends AppCompatActivity {

    ActivityManCageUpdateBinding viewBinding;
    Button Registerbutton;
    EditText editname;
    String aedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_man_cage_update);


        viewBinding = ActivityManCageUpdateBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        String[] items = getResources().getStringArray(R.array.spinner_sort);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ManCageUpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        viewBinding.spinnerSort.setAdapter(dataAdapter);

        viewBinding.spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //viewBinding.testSpinner.setText(items[position]);
                //Toast.makeText(CageUpdateActivity.this, "Item is selected.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //viewBinding.testSpinner.setText(items[0]);
            }

        });

        Intent intent = getIntent();
        String myData = intent.getStringExtra("group_name");


        SharedPreferences preferences = getSharedPreferences("group", MODE_PRIVATE);
        String grp_name = preferences.getString("grp_name","NO_GROUP");
        // 그룹 새로 등록하기 전까지 처음 등록한 group이름 유지

        System.out.println(grp_name);


        // aename = "test-ae-1";  테스트용

        Registerbutton = (Button) findViewById(R.id.button4);
        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editname = findViewById(R.id.editText);
                String aename = editname.getText().toString(); // ae이름
                System.out.println("aename_please = " + aename);

                aedata = getAEname(grp_name); //ae 이름 조회
                System.out.println("aedata = " + aedata);

                // aedata = getAEname("test-grp1");
                if(aedata.contains(aename))
                { System.out.println("Find AeName");
                    System.out.println("ae name = " + aename);
                    //입력한 ae와 동일한 이름을 가진 디바이스 존재
                    // con 등록
                    String JsonMsg = makeJsonMsg();
                    SendJsonToServer(JsonMsg, aename, "temperature");
                    SendJsonToServer(JsonMsg, aename, "humidity");
                    SendJsonToServer(JsonMsg, aename, "brightness");
                }
                else
                {
                    Toast myToast = Toast.makeText(getApplicationContext(),
                            "ae 이름을 확인하세요!", Toast.LENGTH_SHORT);
                    myToast.show();
                }



            }
        });

        getAEname(grp_name); //ae 이름 조회


    }

    String getAEname(String grp_name) {

        String response = "";
        String mid = "";

        String queryUrl = "http://182.221.64.162:7579/Mobius/"+grp_name;
        // 그룹 조회

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // 그룹 조회하여 ae가 존재하는지 확인.
            // ae 없으면 디바이스 등록하라는 toast message

            URL url = new URL(queryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("X-M2M-RI", "dks");
            connection.setRequestProperty("X-M2M-Origin", "dks");
            connection.setRequestProperty("Accept-Encoding","gzip, deflate, br");

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

            JSONObject jsonObject = new JSONObject(response).getJSONObject("m2m:grp");
            mid = jsonObject.optString("mid");
            System.out.println("mid: " +  mid);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mid;
    }

    private static String makeJsonMsg(){
        String retMsg = "";

        JSONStringer jsonStringer = new JSONStringer();

        try{
            retMsg = jsonStringer.object()
                    .key("m2m:cnt").object()
                    .key("rn").value("control")
                    .key("lbl").array()
                    .endArray()
                    .key("mbs").value(16384)
                    .endObject()
                    .endObject().toString();
            System.out.println(retMsg);

            Log.d(TAG,"send string = " + retMsg);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return retMsg;
    }

    static public String SendJsonToServer(String JsonMsg, String ae_name, String sensor){
        OutputStream os = null;
        InputStream is = null;

        int responseCode;

        HttpURLConnection connection = null;

        String response="";
        String queryUrl = "http://182.221.64.162:7579/Mobius/"+ae_name+"/"+sensor;
        System.out.println("con생성" + queryUrl);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            URL url = new URL(queryUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("X-M2M-RI", "dks");
            connection.setRequestProperty("X-M2M-Origin", "dks");
            connection.setRequestProperty("Content-Type", "application/vnd.onem2m-res+json; ty=9");

            // OutputStream으로 Post데이터 넘겨주는 옵션
            connection.setDoOutput(true);

            // InputStream으로 서버 응답 받는 옵션
            connection.setDoInput(true);

            os = connection.getOutputStream();
            os.write(JsonMsg.getBytes()); //json넘겨주는 부분
            os.flush();

            responseCode = connection.getResponseCode();

            System.out.println(responseCode);


            // inputStream으로 응답
            // 요 부분 잘 안됨....
            if(responseCode==201){
                System.out.println("HTTP_OK");
                is = connection.getInputStream();
                // inputStream to String
                if(is != null)
                {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                    StringBuffer stringBuffer = new StringBuffer();
                    String inputLine;

                    while ((inputLine = bufferedReader.readLine()) != null)  {
                        stringBuffer.append(inputLine);
                    }
                    bufferedReader.close();

                    response = stringBuffer.toString();
                }
                else
                    response = "Error";
                System.out.println(response);
            }
            else{
                response = "Connection_Error";
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}