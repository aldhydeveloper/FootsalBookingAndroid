package com.ns.queenfutsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION );

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view ->
                startActivity(new Intent(this,LoginActivity.class)));
    }

    @Override
    public void onBackPressed() { }
}