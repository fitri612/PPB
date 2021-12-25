package com.udinus.login_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    // deklarasi variabel dan connect firebase
    EditText editUsername;
    EditText editEmail;
    EditText editPassword;
    EditText editConfPassword;
    TextView signIp;
    Button btnRegister;
    ImageView back;

    //connect firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = findViewById(R.id.edit_txt_username);
        editEmail = findViewById(R.id.edit_txt_email);
        editPassword = findViewById(R.id.edit_txt_password);
        editConfPassword = findViewById(R.id.edit_txt_conf_pass);
        signIp = findViewById(R.id.btnsignin);
        btnRegister = findViewById(R.id.btnlogin);
        back = findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            CreateNewUser();
        });

        back.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signIp.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    //create new fungsi handle new user
    private void CreateNewUser(){
        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPass = editConfPassword.getText().toString();

        // handle beberapa kondisi
        if(TextUtils.isEmpty(username)){
            editUsername.setError("Username cannot be empty");
            editUsername.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            editEmail.setError("Email cannot be empty");
            editEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            editPassword.setError("Password cannot be empty");
            editPassword.requestFocus();
        }
        else if(TextUtils.isEmpty(confirmPass)){
            editConfPassword.setError("Confirm password cannot be empty");
            editConfPassword.requestFocus();
        }
        else if(!confirmPass.equals(password)){
            editConfPassword.setError("Confirm password and password not equals");
            editConfPassword.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}