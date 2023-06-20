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

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginButton;
    private String emailOfUser;
    private String passwordOfUser;
    private SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            onClick();
        });
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
    }

    private void onClick() {
        emailOfUser = email.getText().toString();
        passwordOfUser = password.getText().toString();

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

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(emailOfUser, passwordOfUser);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getError().equals("200")) {
                    sharedPrefManager.saveUser(response.body().getUser());
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPrefManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}