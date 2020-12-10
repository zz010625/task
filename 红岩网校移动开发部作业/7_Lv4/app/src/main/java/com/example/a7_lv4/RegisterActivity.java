package com.example.a7_lv4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {
    String errorCode = "";
    String errorMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ImageButton back = findViewById(R.id.ibtn_back);
        EditText account = findViewById(R.id.et_register_account);
        EditText password = findViewById(R.id.et_register_password);
        EditText rePassword = findViewById(R.id.et_register_verify_password);
        Button register = findViewById(R.id.btn_register);
        register.setEnabled(false);
        //返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //对账号栏输入长度进行验证
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (account.length() >= 3 && password.length() == rePassword.length()) {
                    if (TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(rePassword.getText())) {
                        register.setEnabled(false);
                    } else {
                        register.setEnabled(true);
                    }
                }else {
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //对密码栏输入长度进行验证
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.length() >= 6 && password.length() == rePassword.length()) {
                    register.setEnabled(true);
                } else {
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //对确认密码栏进行核实
        rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (account.length() >= 3 && password.length() >= 6 && password.length() == rePassword.length()) {
                    register.setEnabled(true);
                } else {
                    register.setEnabled(false);
                }
            }
        });
        //点击注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> registerData = new HashMap<>();
                registerData.put("username", account.getEditableText().toString());
                registerData.put("password", password.getEditableText().toString());
                registerData.put("repassword", rePassword.getEditableText().toString());
                Tools tools = new Tools();
                tools.sendPostNetRequest("https://www.wanandroid.com/user/register", registerData, handler);


            }
        });
    }

    //接收网络请求返回的数据判断是否成功注册 若错误则Toast出错误信息
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            JSONObject jsonObject = (JSONObject) msg.obj;
            try {
                errorCode = jsonObject.getString("errorCode");
                errorMsg = jsonObject.getString("errorMsg");
                if (errorCode.equals("0")) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}