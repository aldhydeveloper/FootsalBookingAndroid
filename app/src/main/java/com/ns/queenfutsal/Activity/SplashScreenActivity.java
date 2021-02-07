package com.ns.queenfutsal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ns.queenfutsal.R;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;

    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        handler = new Handler();
        handler.postDelayed(() -> {

            if (fUser != null){
                Intent SendToDashboard = new Intent(this, MainActivity.class);
                startActivity(SendToDashboard);
                finish();

            }else {
                Intent dsp = new Intent(this, LoginActivity.class);
                startActivity(dsp);
                finish();
            }
        }, 4000);
    }
}