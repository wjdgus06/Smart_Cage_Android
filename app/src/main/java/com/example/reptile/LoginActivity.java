package com.example.reptile;

import androidx.appcompat.app.AppCompatActivity;
import com.example.reptile.databinding.ActivityLoginBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        viewBinding.btnLogin.setOnClickListener(view -> {
            Response.Listener<String> response_listener = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userID = jsonObject.getString("userID");
                        String userPW = jsonObject.getString("userPassword");
                        Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        //출발 -> 도착
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("userPassword", userPW);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return; // 실패 -> MainActivity 로 넘어가면 X
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            LoginRequest loginRequest = new LoginRequest(viewBinding.etId.getText().toString(), viewBinding.etPw.getText().toString(), response_listener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        });

        viewBinding.btnRegister.setOnClickListener(view -> {
            // 회원가입 액티비티 생성 후 연결
            Toast.makeText(getApplicationContext(), "Test btn_register", Toast.LENGTH_SHORT).show();
        });

        setContentView(viewBinding.getRoot());
    }
}