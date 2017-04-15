package com.example.application.makelar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;

import model.User;
import service.UserService;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText email_box;
    Button reset_button;
    private FirebaseAuth auth;
    private ProgressDialog progressBar;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.reset_password) {
                progressBar = new ProgressDialog(ResetPasswordActivity.this);
                progressBar.setCancelable(false);
                progressBar.setMessage("Sending  email .....");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                resetPassword(email_box.getText().toString());
            }
        }
    };

    private void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.dismiss();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email_box = (EditText) findViewById(R.id.email);
        reset_button = (Button) findViewById(R.id.reset_password);
        auth = FirebaseAuth.getInstance();
        reset_button.setOnClickListener(onClick);
    }
}
