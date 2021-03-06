package com.sun00j.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by lewa on 10/21/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText name;
    private EditText password;
    private Button login;
    private Button forgetPwd;
    private TextView register;
    private Context mContext;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;
        name = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        forgetPwd = (Button) findViewById(R.id.forget);
        forgetPwd.setOnClickListener(this);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String nameStr = name.getText().toString();
        String pwd = password.getText().toString();
        int id = view.getId();
        switch (id) {
            case R.id.login:
                if (nameStr.length() < 1) {
                    Toast.makeText(this, R.string.name_not_null, Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 1) {
                    Toast.makeText(this, R.string.password_not_null, Toast.LENGTH_SHORT).show();
                } else {
                    doLogin(nameStr, pwd);
                }
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
        }

    }

    private void doLogin(String name, String password) {
        String url = "http://10.0.4.170:8080/DogLegWeb/login.do";
        password = CommonUtil.getMD5(password);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("userName", name);
        builder.add("password", password);
        final Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "IOException :" + e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("sucess")) {
                            Intent intent = new Intent(LoginActivity.this,GameActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else if (result.equals("fail")) {
                            Toast.makeText(mContext, R.string.name_pwd_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
