package com.sun00j.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by sun00j on 2015/10/22.
 */
public class RegisterActivity extends Activity {
    private EditText userName;
    private EditText password;
    private EditText repeat_pwd;
    private Button submit;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mContext = this;
        userName = (EditText) findViewById(R.id.reg_name);
        password = (EditText) findViewById(R.id.reg_pwd);
        repeat_pwd = (EditText) findViewById(R.id.repeat_pwd);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = userName.getText().toString();
                String pwdStr = password.getText().toString();
                String repeatStr = repeat_pwd.getText().toString();
                if (nameStr.length() < 1) {
                    Toast.makeText(mContext, R.string.name_not_null, Toast.LENGTH_SHORT).show();
                } else if (pwdStr.length() < 6) {
                    Toast.makeText(mContext, R.string.pwd_too_short, Toast.LENGTH_SHORT).show();
                } else if (!pwdStr.equals(repeatStr)) {
                    Toast.makeText(mContext, R.string.pwd_not_same, Toast.LENGTH_SHORT).show();
                } else {
                    String url = "http://10.0.4.170:8080/DogLegWeb/addUser.do";
                    pwdStr = CommonUtil.getMD5(pwdStr);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    builder.add("userName", nameStr);
                    builder.add("password", pwdStr);
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(builder.build())
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            final String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("fail")) {
                                        Toast.makeText(mContext, R.string.name_exist, Toast.LENGTH_SHORT).show();
                                    } else if (result.equals("sucess")) {
                                        Toast.makeText(mContext, R.string.register_sucess, Toast.LENGTH_SHORT).show();
                                        CommonUtil.online = true;
                                        RegisterActivity.this.finish();

                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
