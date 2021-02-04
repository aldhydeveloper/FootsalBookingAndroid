package com.ns.queenfutsal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    TextView tvSingUp;
    EditText edtEmail, edtPassword;
    Button btnLogin;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        tvSingUp = findViewById(R.id.tvSingUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        initEvent();

    }

    private void initEvent() {
        tvSingUp.setOnClickListener(view ->
                startActivity(new Intent(this, SignUpActivity.class)));
        btnLogin.setOnClickListener(view -> {
            SweetAlertDialog loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            loading.setTitle("Loading");
            loading.show();

            String password = edtPassword.getText().toString();
            String email = edtEmail.getText().toString();

            if (password.isEmpty() || email.isEmpty()){
                loading.dismissWithAnimation();
                SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                progress.setTitle("Gagal Login");
                progress.setContentText("Semua field tidak boleh kosong !!!");
                progress.show();
            }else{
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        loading.dismissWithAnimation();
                        Common.userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        myRef.child(Common.userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users users = snapshot.getValue(Users.class);
                                assert users != null;
                                Common.Fullname = users.getFullName();
                                Common.Password = users.getPassword();
                                Common.Email    = users.getEmail();
                                Common.NoHp     = users.getNoHp();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        startActivity(new Intent(this, MainActivity.class));
                    }else{
                        loading.dismissWithAnimation();
                        SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
                        progress.setTitle("Email atau Password salah!");
                        progress.show();
                        Log.e("LOGIN","ERROR LOGIN", task.getException());
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() { }
}