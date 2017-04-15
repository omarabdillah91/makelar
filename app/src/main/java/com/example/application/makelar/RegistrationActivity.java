package com.example.application.makelar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Locale;

import model.User;
import service.UserService;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText username_box, email_box, password_box, confirm_password_box, phone_box, dob_box;
    Button create_button;
    Spinner sex_spinner;
    ArrayAdapter<CharSequence> sex_adapter;
    String username, email, password, confirm_password, phone_number, dob, sex ="";
    private ProgressDialog progressBar;
    private FirebaseAuth auth;
    private DatePickerDialog dob_picker;
    private java.text.SimpleDateFormat date_format;
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.create_account) {
                progressBar = new ProgressDialog(RegistrationActivity.this);
                progressBar.setCancelable(false);
                progressBar.setMessage("Registering your account .....");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                username = username_box.getText().toString();
                email = email_box.getText().toString();
                password = password_box.getText().toString();
                confirm_password = confirm_password_box.getText().toString();
                phone_number = phone_box.getText().toString();
                dob = dob_box.getText().toString();
                Toast.makeText(RegistrationActivity.this, username+" "+password,
                        Toast.LENGTH_SHORT).show();
                // do create account
                if(checkValidity()) {
                    User user= new User(username, email, password, dob,phone_number, sex);
                    Log.d("USER", username);
                    UserService.createNewUser(user,
                            new UserService.CreateNewUserListener() {
                                @Override
                                public void onInsertSessionCompleted(boolean isSuccess) {
                                    Log.d("CREATE NEW USER", "success");
                                    createUser(email, password);
                                }
                            });
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please fill every information needed!!",
                            Toast.LENGTH_SHORT).show();
                }
            } else if(v.getId() == R.id.date_of_birth) {
                dob_picker.show();
            }
        }
    };

    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Toast.makeText(RegistrationActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    private boolean checkValidity() {
        if(username == "" || email == "" || password == "" || confirm_password == "" || phone_number == "" || dob == "" || sex == "")  {
            return false;
        } else {
            if(!password.equalsIgnoreCase(confirm_password)) {
                return false;
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username_box = (EditText) findViewById(R.id.username);
        email_box = (EditText) findViewById(R.id.email);
        password_box = (EditText) findViewById(R.id.password);
        confirm_password_box = (EditText) findViewById(R.id.confirm_password);
        phone_box = (EditText) findViewById(R.id.phone_number);
        dob_box = (EditText) findViewById(R.id.date_of_birth);
        create_button = (Button) findViewById(R.id.create_account);
        sex_spinner = (Spinner) findViewById(R.id.sex);
        date_format = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dob_box.setInputType(InputType.TYPE_NULL);
        dob_box.requestFocus();
        sex_adapter =ArrayAdapter.createFromResource(this,R.array.sex_option, android.R.layout.simple_spinner_item);
        sex_spinner.setAdapter(sex_adapter);
        sex_spinner.setOnItemSelectedListener(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        create_button.setOnClickListener(onClick);
        dob_box.setOnClickListener(onClick);
        Calendar newCalendar = Calendar.getInstance();
        dob_picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob_box.setText(date_format.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int choice = position;
        this.sex = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
