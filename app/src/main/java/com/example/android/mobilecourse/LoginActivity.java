package com.example.android.mobilecourse;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {
    private static final int passwordValue = 8;
    private FirebaseAuth mAuth;

    @BindView(R.id.input_email) EditText emailField;
    @BindView(R.id.input_password) EditText passwordField;
    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.link_signup) TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(v -> logIn());
        signupLink.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    public void logIn() {
        if (!fieldsValidation()) {
            Toast.makeText(LoginActivity.this, getText(R.string.input_validation), Toast.LENGTH_LONG).show();
            loginFailure();
            return;
        }

        loginButton.setEnabled(false);

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        onSuccess();
                        finish();
                    } else {
                        loginFailure();
                    }
                });
    }

    private void onSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Toast.makeText(getBaseContext(), getText(R.string.successful_login), Toast.LENGTH_LONG).show();
        finish();
        loginButton.setEnabled(true);
    }

    private void loginFailure() {
        Toast.makeText(LoginActivity.this, getText(R.string.login_failure),
                Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);
    }

    private boolean fieldsValidation() {
        boolean input = true;

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
            emptyFieldsValidation();
            input = false;
        }
        else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getText(R.string.invalid_email_input));
            input = false;
            emailField.setError(null);
        }
        else if (password.isEmpty() || password.length() < passwordValue) {
            passwordField.setError(getText(R.string.invalid_password_input));
            input = false;
            passwordField.setError(null);
        }
        return input;
    }

    private void emptyFieldsValidation() {
        emailField.setError(getString(R.string.email_is_required));
        passwordField.setError(getString(R.string.password_is_required));
        emailField.requestFocus();
        passwordField.requestFocus();
    }
}
