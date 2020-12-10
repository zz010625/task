package com.example.a7_lv4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    String errorCode = "";
    String errorMsg = "";
    TextView sina;
    TextView tencent;
    TextView userAgreement;
    TextView register;
    EditText account;
    EditText password;
    Button login;
    CheckBox agreeUserAgreement;
    CheckBox rememberPassword;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    boolean isRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        sina = findViewById(R.id.tv_sina);
        tencent = findViewById(R.id.tv_tencent);
        userAgreement = findViewById(R.id.tv_user_agreement);
        register = findViewById(R.id.tv_register);
        account = findViewById(R.id.et_account);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        login.setEnabled(false);
        agreeUserAgreement = findViewById(R.id.cb_user_agreement);
        rememberPassword = findViewById(R.id.cb_remember_password);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        //点击新浪微博登录
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "暂不支持此登陆方式", Toast.LENGTH_SHORT).show();
            }
        });
        //点击腾讯微博登录
        tencent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "暂不支持此登陆方式", Toast.LENGTH_SHORT).show();
            }
        });
        //监听用户协议勾选框
        agreeUserAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (agreeUserAgreement.isChecked()) {
                    login.setEnabled(true);

                } else {
                    login.setEnabled(false);
                }
            }
        });

        //监听记住密码勾选框
        rememberPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rememberPassword.isChecked()){
                    editor.putBoolean("rememberPasswordAndAgreeUserAgreement",true);
                }else {
                    editor.putBoolean("rememberPasswordAndAgreeUserAgreement",false);
                }
                editor.apply();
            }
        });

        //点击用户协议
        userAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_user_agreement:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                        dialog.setTitle("用户协议");
                        dialog.setMessage("红岩移动开发部第七次作业Lv4");
                        dialog.setCancelable(false);
                        //点击确认
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                agreeUserAgreement.setChecked(true);
                                login.setEnabled(true);
                            }
                        });
                        //点击取消
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                agreeUserAgreement.setChecked(false);
                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }
            }
        });

        //记住密码和同意用户协议
        isRemember = preferences.getBoolean("rememberPasswordAndAgreeUserAgreement", false);
        if (isRemember) {
            account.setText(preferences.getString("username", " "));
            password.setText(preferences.getString("password", " "));
            rememberPassword.setChecked(true);
            agreeUserAgreement.setChecked(true);
        }
        //点击立即注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //点击登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> loginData = new HashMap<>();
                loginData.put("username", account.getEditableText().toString());
                loginData.put("password", password.getEditableText().toString());
                Tools tools = new Tools();
                tools.sendPostNetRequest("https://www.wanandroid.com/user/login", loginData, handler);
            }
        });
    }

    //接收网络请求返回的数据判断是否成功登录 若错误则Toast出错误信息
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            JSONObject jsonObject = (JSONObject) msg.obj;
            try {
                errorCode = jsonObject.getString("errorCode");
                errorMsg = jsonObject.getString("errorMsg");
                if (errorCode.equals("0")) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //如果勾选记住密码框 则将账号密码保存至本地
                    if (rememberPassword.isChecked()) {
                        editor.putBoolean("rememberPasswordAndAgreeUserAgreement", true);
                        editor.putString("username", account.getEditableText().toString());
                        editor.putString("password", password.getEditableText().toString());
                        editor.apply();
                    }
                    Intent intent = new Intent(LoginActivity.this, RecyclerViewActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}