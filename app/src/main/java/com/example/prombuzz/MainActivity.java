package com.example.prombuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LottieAnimationView splash_screen = findViewById(R.id.splash_screen);
        splash_screen.setScale(6.2f);
        Button get_started  = findViewById(R.id.get_started);
        Animation get_started_animation = AnimationUtils.loadAnimation(MainActivity.this , R.anim.get_started);
        get_started.setAnimation(get_started_animation);

        Intent intent = new Intent(MainActivity.this , MainActivity2.class);
        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}