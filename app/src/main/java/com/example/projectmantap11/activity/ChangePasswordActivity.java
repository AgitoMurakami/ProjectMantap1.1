package com.example.projectmantap11.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.example.projectmantap11.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ChangePasswordActivity extends AppCompatActivity {

    TextInputLayout oldPassInputLayout, newPassInputLayout, confirmPassInputLayout;
    TextInputEditText oldPassEditText, newPassEditText, confirmPassEditText;

    Button changePassHitAPIButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //hideactionbar
        getSupportActionBar().hide();

        //findview
        oldPassInputLayout = findViewById(R.id.change_password_old_password_input_layout);
        newPassInputLayout = findViewById(R.id.change_password_new_password_input_layout);
        confirmPassInputLayout = findViewById(R.id.change_password_confirm_password_input_layout);

        oldPassEditText = findViewById(R.id.change_password_old_password_input);
        newPassEditText = findViewById(R.id.change_password_new_password_input);
        confirmPassEditText = findViewById(R.id.change_password_confirm_password_input);

        oldPassEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!oldPassEditText.getText().toString().equals("teststaff")) {
                    oldPassInputLayout.setError("Old password is wrong");
                    if (oldPassEditText.getText().toString()
                            .equals(newPassEditText.getText().toString())) {
                        newPassInputLayout.setError("New password is same as old password");
                    } else {
                        newPassInputLayout.setError(null);
                    }
                } else {
                    oldPassInputLayout.setError(null);
                }
            }
        });

        newPassEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String comparison = oldPassEditText.getText().toString();
                if (newPassEditText.getText().toString().equals(comparison)) {
                    newPassInputLayout.setError("New password is same as old password");
                } else {
                    newPassInputLayout.setError(null);
                }
            }
        });

        confirmPassEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String comparison = newPassEditText.getText().toString();
                if (!confirmPassEditText.getText().toString().equals(comparison)) {
                    confirmPassEditText.setError("Password not match!");
                } else {
                    confirmPassInputLayout.setError(null);
                }
            }
        });
    }
}