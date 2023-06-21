package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private TextView login;
    private EditText email, password, confirm_password;
    private Button rl_signUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.et_email_address);
        password = findViewById(R.id.et_password);
        confirm_password = findViewById(R.id.et_confirm_password);
        rl_signUp = findViewById(R.id.rl_signup_button);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        rl_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("") ||
                        password.getText().toString().equals("") ||
                        confirm_password.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please fill all the input fields",
                            Toast.LENGTH_LONG).show();
                } else if (!password.getText().toString().equals(confirm_password.getText().toString())) {
                    Toast.makeText(Register.this, "Passwords do not match",
                            Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Register.this, inputCredentials.class);
                                        intent.putExtra("email", email.getText().toString());
                                        intent.putExtra("password", password.getText().toString());
                                        intent.putExtra("id", mAuth.getCurrentUser().getUid());
                                        startActivity(intent);

                                        finish();
                                    } else {
                                        Toast.makeText(Register.this, "Failed to Create User", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Failed to Create User", Toast.LENGTH_LONG).show();
                                }
                            })
                    ;
                }
            }
        });
    }
}