package com.ns.queenfutsal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ns.queenfutsal.Common.Common;
import com.ns.queenfutsal.Model.Users;
import com.ns.queenfutsal.R;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    ImageView imgUsers, btnLogout;
    TextView tvFullName, tvNoHp;

    CardView btnBooking, btnCart, btnHistory, btnMore;

    SweetAlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        Common.userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        imgUsers = findViewById(R.id.imgUsers);
        btnLogout = findViewById(R.id.btnLogout);
        tvFullName = findViewById(R.id.tvFullName);
        tvNoHp = findViewById(R.id.tvNoHp);
        btnBooking = findViewById(R.id.btnBooking);
        btnCart = findViewById(R.id.btnCart);
        btnHistory = findViewById(R.id.btnHistory);
        btnMore = findViewById(R.id.btnMore);

        iniEvent();

    }

    private void iniEvent() {
        btnLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnBooking.setOnClickListener(view ->
                startActivity(new Intent(this, BookingActivity.class)));
    }

    private void initView() {
        tvFullName.setText(Common.Fullname);
        tvNoHp.setText(Common.NoHp);
        loading.dismissWithAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loading.setTitle("Loading");
        loading.show();
        myRef.child(Common.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Users users = snapshot.getValue(Users.class);
                    assert users != null;
                    Common.Fullname = users.getFullName();
                    Common.Password = users.getPassword();
                    Common.Email    = users.getEmail();
                    Common.NoHp     = users.getNoHp();
                    Log.d("cek data", Common.userId + "," + Common.Email + "," + Common.Fullname + "," + Common.NoHp);

                    initView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {}
}