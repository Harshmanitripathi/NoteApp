package com.example.notescopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private Button registerButton;
    private Button loginButton;
    private String nameOfUser;
    private String emailOfUser;
    private String passwordOfUser;
    private int checkForRegistration = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(v -> {
            onClick(StringVariable.REGISTER);
        });
        loginButton.setOnClickListener(v-> {
            onClick(StringVariable.LOGIN);
        });
    }

    private void onClick(String registerOrLoginClick) {
        switch (registerOrLoginClick) {

            case StringVariable.REGISTER:
                nameOfUser = username.getText().toString();
                emailOfUser = email.getText().toString();
                passwordOfUser = password.getText().toString();
                if (TextUtils.isEmpty(nameOfUser)) {
                    username.requestFocus();
                    username.setError("Please enter your name");
                    return;
                }
                if (TextUtils.isEmpty(emailOfUser)) {
                    email.requestFocus();
                    email.setError("Please enter your email");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailOfUser).matches()) {
                    email.requestFocus();
                    email.setError("Please enter correct email");
                    return;
                }
                if (TextUtils.isEmpty(passwordOfUser)) {
                    password.requestFocus();
                    password.setError("Please enter your password");
                    return;
                }
                checkForRegistration=1;
                break;
            case StringVariable.LOGIN:
                startActivity(new Intent(this, LoginActivity.class));
        }
        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(nameOfUser,emailOfUser,passwordOfUser);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                if (checkForRegistration == 1) {
                    checkForRegistration=0;
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}