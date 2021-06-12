package com.example.prombuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity3 extends AppCompatActivity {
    // SIGN UP CLASS FOR THE USER
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Animation btn_animation = AnimationUtils.loadAnimation(MainActivity3.this , R.anim.connect_with_us_anima);
        Button create_account = findViewById(R.id.CREATE_YOUR_ACCOUNT_SIGN_UP);
        create_account.setAnimation(btn_animation);


        // Variables
        EditText sigup_name = findViewById(R.id.name_signup);
        EditText signup_email = findViewById(R.id.Email_signup);
        EditText signup_password = findViewById(R.id.PASSWORD_SIGN_UP);
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity3.this , MainActivity4.class);
            startActivity(intent);
            finish();
        }

        // FireBase Things
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sigup_name.getText().toString().trim();
                String email = signup_email.getText().toString().trim();
                String password = signup_password.getText().toString().trim();

                // checking if input is valid
                if(TextUtils.isEmpty(email)){
                    signup_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    signup_password.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    sigup_name.setError("Name is Required");
                    return;
                }
                if(password.length() < 6){
                    signup_password.setError("Password must have 6 character or long");
                }

                // Registration Process
                fAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity3.this, "User Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity3.this , MainActivity4.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(MainActivity3.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}