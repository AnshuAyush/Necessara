package com.example.prombuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

// PRE EXISTING USER CLASS AND SIGN UP USING EMAIL
public class MainActivity2 extends AppCompatActivity {
    private static final int RC_SIGN_IN = 101;
    private static final String TAG = " ";
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;


    // Check if Internet Connection or not
    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork == null){
            Intent no_internet = new Intent(MainActivity2.this , nointernet.class);
            startActivity(no_internet);
        }
    }

    @Override
    public void onStart() {
        setRequestedOrientation(1);
        super.onStart();
        checkConnection();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity2.this , MainActivity4.class);
            startActivity(intent);
            finish();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Animation connect_with_us_Animation = AnimationUtils.loadAnimation(MainActivity2.this , R.anim.connect_with_us_anima);
        // Always in Portrait mode

        TextView connect_with_us_text = findViewById(R.id.Connect_with_us);
        connect_with_us_text.setAnimation(connect_with_us_Animation);
        Button sign_up_btn = findViewById(R.id.SIGN_UP_BTN);
        TextView forgot_password = findViewById(R.id.forgot_password);
        GoogleSignInClient mGoogleSignInClient;
        LottieAnimationView spinner = findViewById(R.id.spinner);
        LottieAnimationView login_upper_animation = findViewById(R.id.login_animation);
        spinner.setScale(0.3f);
        LottieAnimationView instagram = findViewById(R.id.insta);
        LottieAnimationView gmail_us = findViewById(R.id.gmail);


        // Firebase object
        mAuth = FirebaseAuth.getInstance();


        // Sign up Using Create New Account
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this , MainActivity3.class);
                startActivity(intent);
            }
        });



        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login_btn = findViewById(R.id.LOGIN_BTN);
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        // Login onclick Pre-Existing Account
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str = email.getText().toString().trim();
                String password_str = password.getText().toString().trim();

                // checking if input is valid
                if(TextUtils.isEmpty(email_str)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password_str)){
                    password.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    password.setError("Password must have 6 character or long");
                }
                spinner.setVisibility(View.VISIBLE);
                login_upper_animation.setVisibility(View.INVISIBLE);
                // authenticate the user
                fAuth.signInWithEmailAndPassword(email_str , password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity2.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity2.this , MainActivity4.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity2.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            spinner.setVisibility(View.INVISIBLE);
                            login_upper_animation.setVisibility(View.VISIBLE);


                        }
                    }
                });
            }
        });


        // Forgot Password Method
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str_forgot_password = email.getText().toString().trim();
                if (TextUtils.isEmpty(email_str_forgot_password)) {
                    Toast.makeText(MainActivity2.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Check your mail", Toast.LENGTH_SHORT).show();
                    fAuth.sendPasswordResetEmail(email_str_forgot_password);
                }
            }
        });


        // Visit to our Instagram
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/aster_bluem";
                Uri uri = Uri.parse(url);
                try {
                    Intent visit_to_our_insta = new Intent(Intent.ACTION_VIEW , uri);
                    startActivity(visit_to_our_insta);
                }
                catch (Exception e){
                    Toast.makeText(MainActivity2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Mail us your query
        gmail_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:?subject=" + "subject text"+ "&body=" + "Send mail.. " + "&to=" + "destination@mail.com");
                Intent mail_to_us = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(Intent.createChooser(mail_to_us , "Send mail.."));

            }
        });

        // SIGN IN USING GOOGLE:
        // OnClick Listener Using Email SignUp
        LottieAnimationView google_sign_in = findViewById(R.id.google);

        google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessRequest();
                ProcessLogin();
            }
        });
    }



    private void ProcessRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // Entering email and password for Google sign in
    private void ProcessLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent , RC_SIGN_IN);
    }

    // Email connect to fireBase and authentication
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Error Getting Google User Information", Toast.LENGTH_SHORT).show();

            }
        }
    }
    // Going to main app after login
    private void firebaseAuthWithGoogle(String idToken) {
        LottieAnimationView spinner = findViewById(R.id.spinner);
        LottieAnimationView login_upper_animation = findViewById(R.id.login_animation);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        spinner.setVisibility(View.VISIBLE);
        login_upper_animation.setVisibility(View.INVISIBLE);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity2.this , MainActivity4.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            spinner.setVisibility(View.INVISIBLE);
                            login_upper_animation.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity2.this, "Error !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}