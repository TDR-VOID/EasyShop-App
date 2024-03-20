package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    TextView supportTextView;
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

            if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);
        supportTextView = findViewById(R.id.supportTextView);
        supportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSupportDialog(); // Function to display the support dialog
            }
        });




        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String  email, password;
                email = editTextEmail.getText().toString().trim(); // Trim the email
                password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                }

                if (!isValidEmail(email)) { // Validate the email format
                    Toast.makeText(Login.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Error handling
                                    Exception exception = task.getException();
                                    if (exception != null && exception.getMessage() != null && exception.getMessage().contains("network error")) {
                                        // Show a simplified message for network errors
                                        Toast.makeText(Login.this, "PLease check the internet connection", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Show the original error message
                                        Toast.makeText(Login.this, "Authentication failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });


            }
        });
    }



    private void showSupportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Support");
        builder.setMessage("Smart Shop\nCall: 0769054603");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss(); // Dismiss the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
