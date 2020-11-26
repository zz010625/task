package com.example.a5_lv4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class register_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ImageButton back = findViewById(R.id.ibtn_back);
        EditText account = findViewById(R.id.et_register_account);
        EditText password = findViewById(R.id.et_register_password);
        EditText verifyPassword = findViewById(R.id.et_register_verify_password);
        Button register = findViewById(R.id.btn_register);
        register.setEnabled(false);
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
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
                if (account.length() < 2) {
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
                if (password.getEditableText().toString().equals(verifyPassword.getEditableText().toString()) && password.length() >= 6) {
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
        verifyPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getEditableText().toString().equals(verifyPassword.getEditableText().toString()) &&account.length()>=2&& password.length() >= 6) {
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
                editor.putString("account", account.getEditableText().toString());
                editor.putString("password", password.getEditableText().toString());
                editor.apply();
                Toast.makeText(register_activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}