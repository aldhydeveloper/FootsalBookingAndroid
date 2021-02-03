package com.ns.queenfutsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView tvSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        tvSingUp = findViewById(R.id.tvSingUp);


        tvSingUp.setOnClickListener(view ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }

    @Override
    public void onBackPressed() { }
}