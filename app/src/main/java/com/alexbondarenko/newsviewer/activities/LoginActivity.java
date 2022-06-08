package com.alexbondarenko.newsviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.data.models.User;
import com.alexbondarenko.newsviewer.data.storage.UsersStorage;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView tvLogin, tvPassword, tvError;
    Button loginButton, registrationButton;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvLogin = findViewById(R.id.la_login);
        tvPassword = findViewById(R.id.la_password);
        tvError = findViewById(R.id.la_error);
        loginButton = findViewById(R.id.la_button_login);
        registrationButton = findViewById(R.id.la_button_registration);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users = UsersStorage.getInstance(getApplicationContext()).getUsers();

                if (!(tvLogin.length() == 0 || tvPassword.length() == 0)) {
                    for (User u : users) {
                        if (u.getLogin().equals(tvLogin.getText().toString()) && u.getPassword().equals(tvPassword.getText().toString())) {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            SharedPreferences sharedPreferences = getSharedPreferences("hasLogin", MODE_PRIVATE);

                            SharedPreferences.Editor e = sharedPreferences.edit();
                            e.putBoolean("hasLogin", true);
                            e.apply();
                            break;
                        }
                        tvError.setText(R.string.user_not_found);
                        tvError.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvError.setText(R.string.data_not_correct);
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLogin.setText("");
        tvPassword.setText("");
        tvError.setText("");
    }
}