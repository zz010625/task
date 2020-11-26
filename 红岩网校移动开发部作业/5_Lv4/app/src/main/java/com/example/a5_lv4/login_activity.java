package com.example.a5_lv4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class login_activity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        TextView sina = findViewById(R.id.tv_sina);
        TextView tencent = findViewById(R.id.tv_tencent);
        TextView userAgreement = findViewById(R.id.tv_user_agreement);
        TextView register = findViewById(R.id.tv_register);
        EditText account=findViewById(R.id.et_account);
        EditText password=findViewById(R.id.et_password);
        Button login = findViewById(R.id.btn_login);
        login.setEnabled(false);
        CheckBox agreeUserAgreement = findViewById(R.id.cb_user_agreement);
        CheckBox rememberPassword=findViewById(R.id.cb_remember_password);
        //点击新浪微博登录
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(login_activity.this, "暂不支持此登陆方式", Toast.LENGTH_SHORT).show();
            }
        });
        //点击腾讯微博登录
        tencent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(login_activity.this, "暂不支持此登陆方式", Toast.LENGTH_SHORT).show();
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
        //点击用户协议
        userAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_user_agreement:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(login_activity.this);
                        dialog.setTitle("用户协议");
                        dialog.setMessage("红岩移动开发部第五次作业Lv4");
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
        //点击注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_activity.this, register_activity.class);
                startActivity(intent);
            }
        });
        //记住密码和同意用户协议
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        SharedPreferences preferences= getSharedPreferences("data",MODE_PRIVATE);
        boolean isRemember=preferences.getBoolean("remember_password_agree_user_agreement",false);
        if (isRemember){
            account.setText(preferences.getString("account"," "));
            password.setText(preferences.getString("password"," "));
            rememberPassword.setChecked(true);
            agreeUserAgreement.setChecked(true);
        }
        //点击登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (account.getEditableText().toString().equals(preferences.getString("account"," "))&&password.getEditableText().toString().equals(preferences.getString("password"," "))){
                  if (rememberPassword.isChecked()){
                      editor.putBoolean("remember_password_agree_user_agreement",true);
                      editor.apply();
                  }
                  Intent intent=new Intent(login_activity.this,first_activity.class);
                  startActivity(intent);
                  finish();
              }else {
                  Toast.makeText(login_activity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
              }

            }
        });
    }
}
