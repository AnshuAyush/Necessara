package com.example.prombuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class nointernet extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nointernet);
        LottieAnimationView no_internet = findViewById(R.id.no_internet);
        no_internet.setScale(0.5f);
        Button no_internet_try_again = findViewById(R.id.no_internet_try_again);
        no_internet_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent go_back = new Intent(nointernet.this , MainActivity2.class);
                    startActivity(go_back);
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(nointernet.this, "Make your you have access to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}