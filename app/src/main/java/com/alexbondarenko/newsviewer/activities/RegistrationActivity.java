package com.alexbondarenko.newsviewer.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.data.models.User;
import com.alexbondarenko.newsviewer.data.storage.UsersStorage;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    TextView tvLogin, tvPassword, tvError;
    CheckBox ckbIsAdmin;
    Button btnRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tvLogin = findViewById(R.id.ra_login);
        tvPassword = findViewById(R.id.ra_password);
        tvError = findViewById(R.id.ra_error);
        ckbIsAdmin = findViewById(R.id.ra_checkbox_admin);
        btnRegistration = findViewById(R.id.ra_button_registration);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = tvLogin.getText().toString();
                String password = tvPassword.getText().toString();
                boolean isAdmin = ckbIsAdmin.isChecked();

                if (login.length() == 0 || password.length() == 0) {
                    tvError.setText(R.string.data_not_correct);
                    tvError.setVisibility(View.VISIBLE);
                }
                else {
                    List<User> users = UsersStorage.getInstance(getApplicationContext()).getUsers();

                    User newUser = new User(login, password, isAdmin);
                    if (users != null) {
                        if (!UsersStorage.getInstance(getApplicationContext()).hasIn(newUser)) {
                            UsersStorage.getInstance(getApplicationContext()).addUser(newUser);
                            tvError.setText("");
                            tvLogin.setText("");
                            tvPassword.setText("");
                            ckbIsAdmin.setChecked(false);
                            Toast.makeText(getBaseContext(), R.string.user_added, Toast.LENGTH_SHORT).show();
                        }else
                            tvError.setText(R.string.user_already_exist);

                    }
                }
            }
        });
    }
}