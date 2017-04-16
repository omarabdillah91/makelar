package com.example.application.makelar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import model.User;
import service.UserService;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    EditText username_box, email_box, password_box, confirm_password_box, phone_box, dob_box, description_box;
    Button create_button;
    Spinner sex_spinner;
    ArrayAdapter<CharSequence> sex_adapter;
    String username, email, password, confirm_password, phone_number, dob, sex, description = "";
    ImageButton profile_box;
    private ProgressDialog progressBar;
    private FirebaseAuth auth;
    private StorageReference storageReference;
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
                description = description_box.getText().toString();
                // do create account
                if (checkValidity()) {
                    User user = new User(username, email, password, dob, phone_number, sex, description);
                    Log.d("USER", username);
                    UserService.createNewUser(user,
                            new UserService.CreateNewUserListener() {
                                @Override
                                public void onInsertSessionCompleted(boolean isSuccess) {
                                    Log.d("CREATE NEW USER", "success");
                                    if (filePath != null) {
                                        uploadFile(username);
                                    }
                                    createUser(email, password);
                                }
                            });
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please fill every information needed!!",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (v.getId() == R.id.date_of_birth) {
                dob_picker.show();
            } else if (v.getId() == R.id.profile_picture) {
                Log.d("File chooser", "success");
                showFileChooser();
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private void uploadFile(String username) {
        StorageReference riversRef = storageReference.child("profile_picture/"+username+".jpg");
        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("Profile Picture Storing","Sucsess");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //and displaying error message
                        Log.d("FAILED",exception.getMessage());
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        //displaying percentage in progress dialog
                        progressBar.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        storageReference = FirebaseStorage.getInstance().getReference();
        username_box = (EditText) findViewById(R.id.username);
        email_box = (EditText) findViewById(R.id.email);
        password_box = (EditText) findViewById(R.id.password);
        confirm_password_box = (EditText) findViewById(R.id.confirm_password);
        phone_box = (EditText) findViewById(R.id.phone_number);
        dob_box = (EditText) findViewById(R.id.date_of_birth);
        description_box = (EditText) findViewById(R.id.description);
        create_button = (Button) findViewById(R.id.create_account);
        profile_box = (ImageButton) findViewById(R.id.profile_picture);
        sex_spinner = (Spinner) findViewById(R.id.sex);
        date_format = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dob_box.setInputType(InputType.TYPE_NULL);
        dob_box.requestFocus();
        sex_adapter = ArrayAdapter.createFromResource(this, R.array.sex_option, android.R.layout.simple_spinner_item);
        sex_spinner.setAdapter(sex_adapter);
        sex_spinner.setOnItemSelectedListener(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        create_button.setOnClickListener(onClick);
        profile_box.setOnClickListener(onClick);
        dob_box.setOnClickListener(onClick);
        Calendar newCalendar = Calendar.getInstance();
        dob_picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob_box.setText(date_format.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

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
        if (username == "" || email == "" || password == "" || confirm_password == "" || phone_number == "" || dob == "" || sex == "") {
            return false;
        } else {
            if (!password.equalsIgnoreCase(confirm_password)) {
                return false;
            }
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int choice = position;
        this.sex = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_box.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Registration Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
