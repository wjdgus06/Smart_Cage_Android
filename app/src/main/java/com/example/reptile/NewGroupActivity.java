package com.example.reptile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewGroupActivity extends AppCompatActivity {

    EditText editName;
    Button btn_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        SharedPreferences grp_preferences = getSharedPreferences("group", MODE_PRIVATE);
        GrpData grpData = new GrpData();

        btn_Register = (Button) findViewById(R.id.group_register);

        btn_Register.setOnClickListener(view -> {
            editName = findViewById(R.id.group_name);
            String name = editName.getText().toString();

            if(name.length() == 0){
                Toast.makeText(getApplicationContext(), "Input your new group name!", Toast.LENGTH_SHORT).show();
            } else {
                // putString
                SharedPreferences.Editor editor = grp_preferences.edit();
                editor.putString("grp_name", name);
                grpData.addTypeList(name);

                // 저장
                editor.commit();
                editor.apply();

                Toast myToast = Toast.makeText(getApplicationContext(),
                        "그룹이 생성되었습니다. 케이지를 등록해주세요!", Toast.LENGTH_SHORT);
                myToast.show();

                SendJsonToServer( makeJsonMsg(name));


                Intent intent = new Intent(NewGroupActivity.this, ManCageUpdateActivity.class);
                intent.putExtra("group_name", name);
                startActivity(intent);
            }
        });
    }

    private static String makeJsonMsg(String name){
        String retMsg = "";

        JSONStringer jsonStringer = new JSONStringer();

        try{
            retMsg = jsonStringer.object()
                        .key("m2m:grp").object()
                            .key("rn").value(name)
                            .key("mnm").value(5)
                            .key("mid").array()
                            .endArray()
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

    static public String SendJsonToServer(String JsonMsg){
        OutputStream os = null;
        InputStream is = null;

        int responseCode;

        HttpURLConnection connection = null;

        String response="";
        String queryUrl = "http://182.221.64.162:7579/Mobius";

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