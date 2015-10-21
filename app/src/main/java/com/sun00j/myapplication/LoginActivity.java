package com.sun00j.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lewa on 10/21/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText name;
    private EditText password;
    private Button login;
    private Button forgetPwd;
    private TextView register;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;
        name = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        forgetPwd = (Button) findViewById(R.id.forget);
        forgetPwd.setOnClickListener(this);
        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String nameStr = name.getText().toString();
        String pwd = password.getText().toString();
        int id = view.getId();
        switch (id) {
            case R.id.login:
                if(nameStr.length()<1) {
                    Toast.makeText(this,R.string.name_not_null,Toast.LENGTH_SHORT).show();
                } else if(pwd.length()<1) {
                    Toast.makeText(this,R.string.password_not_null,Toast.LENGTH_SHORT).show();
                } else {

                }

        }

    }
    private void doLogin(String name, String password) {
        String url = "http://localhost:8080/DogLegWeb/Login.do";
        password = getMD5(password);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("userName",name);
        builder.add("password", password);
        Request request = new Request.Builder()
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

            }
        });

    }
    private String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
