package com.example.android.mobilecourse;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignupActivity extends Activity {
    private static final int passwordValue = 8;
    private FirebaseAuth mAuth;
    DatabaseReference databaseUsers;

    @BindView(R.id.input_name) EditText nameField;
    @BindView(R.id.input_email) EditText emailField;
    @BindView(R.id.input_phone) EditText phoneField;
    @BindView(R.id.input_password) EditText passwordField;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.link_login) TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        signupButton.setOnClickListener(v -> signUp());
        loginLink.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void signUp() {
        if (!fieldsValidation()) {
            signupFailure();
            return;
        }

        signupButton.setEnabled(false);

        final String name = nameField.getText().toString();
        final String email = emailField.getText().toString();
        final String phone = phoneField.getText().toString();
        String password = passwordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        InfoFields InfoFields = new InfoFields(name, email, phone);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(InfoFields).addOnCompleteListener(task_users -> {
                            signupButton.setEnabled(true);
                            if (task_users.isSuccessful()) {
                                Toast.makeText(getBaseContext(), getString(R.string.successful_registration), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();
                            }
                        });
                    } else {
                        signupFailure();
                    }
                });
    }

    private void signupFailure() {
        Toast.makeText(getBaseContext(), getString(R.string.registration_failure), Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    private boolean fieldsValidation() {
        boolean input = true;

        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String phone = phoneField.getText().toString();
        String password = passwordField.getText().toString();

            if (email.isEmpty() && name.isEmpty() && phone.isEmpty() && password.isEmpty()) {
                emptyFieldsValidation();
                input = false;
            }
            else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.setError(getText(R.string.invalid_email_input));
                input = false;
                emailField.setError(null);
            }
            else if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
                phoneField.setError(getText(R.string.invalid_phone_input));
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
        nameField.setError(getString(R.string.name_is_required));
        emailField.setError(getString(R.string.email_is_required));
        phoneField.setError(getString(R.string.phone_number_is_required));
        passwordField.setError(getString(R.string.password_is_required));
        nameField.requestFocus();
        emailField.requestFocus();
        phoneField.requestFocus();
        passwordField.requestFocus();
    }
}
