package com.example.sparringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox showPasswordCheckBox;
    private Button loginButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        showPasswordCheckBox = findViewById(R.id.show_password);
        loginButton = findViewById(R.id.login_button);
        resetButton = findViewById(R.id.reset_button);

        // 显示密码功能
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // 显示密码
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // 隐藏密码
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        // 重置功能
        resetButton.setOnClickListener(v -> {
            usernameEditText.setText("");
            passwordEditText.setText("");
        });

        // 登录按钮功能
        loginButton.setOnClickListener(v -> {
            // 执行登录操作 (此处可以添加验证用户名和密码的逻辑)

            // 登录成功，启动MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // 关闭当前登录活动，以防用户按返回键返回登录界面
        });
    }
}
