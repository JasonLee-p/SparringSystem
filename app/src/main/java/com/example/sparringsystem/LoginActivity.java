package com.example.sparringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private EditText verificationCodeEditText;
//    private CheckBox showPasswordCheckBox;
    private Button loginButton;
//    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumberEditText = findViewById(R.id.phone_number_edit);
        verificationCodeEditText = findViewById(R.id.verification_code_edit);
        loginButton = findViewById(R.id.login_button);

//        showPasswordCheckBox = findViewById(R.id.show_password);
//        resetButton = findViewById(R.id.reset_button);
//        // 显示密码功能
//        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                // 显示密码
//                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//                // 隐藏密码
//                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        });
//
//        // 重置功能
//        resetButton.setOnClickListener(v -> {
//            usernameEditText.setText("");
//            passwordEditText.setText("");
//        });

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
