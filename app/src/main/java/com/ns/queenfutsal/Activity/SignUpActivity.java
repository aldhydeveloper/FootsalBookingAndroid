package com.ns.queenfutsal.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ns.queenfutsal.Model.Users;
import com.ns.queenfutsal.R;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    Button btnRegister;
    EditText edtFullname, edtPassword, edtEmail, edtNoHp;
    TextView tvLogin;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        btnRegister = findViewById(R.id.btnRegister);

        edtFullname = findViewById(R.id.edtFullname);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtNoHp = findViewById(R.id.edtNoHp);
        tvLogin = findViewById(R.id.tvLogin);


        initEvent();
    }

    private void initEvent() {
        tvLogin.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        btnRegister.setOnClickListener(view -> {
            String fullName = edtFullname.getText().toString();
            String password = edtPassword.getText().toString();
            String email = edtEmail.getText().toString();
            String noHp = edtNoHp.getText().toString();

            SweetAlertDialog loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            loading.setTitle("Loading");
            loading.show();

            if ((fullName.isEmpty() || fullName.equals(" ")) && (password.isEmpty() || password.equals(" ")) &&
                    (email.isEmpty() || email.equals(" ")) && (noHp.isEmpty() || noHp.equals(" "))) {
                loading.dismissWithAnimation();
                SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                progress.setTitle("Gagal Daftarkan Akun");
                progress.setContentText("Semua field tidak boleh kosong !!!");
                progress.show();

            } else {
                if (password.length() < 8){
                    SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
                    progress.setTitle("Password harus 8 digit!");
                    progress.show();
                    loading.dismissWithAnimation();
                }else{
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                            progress.setTitle("Berhasil Daftarkan Akun");
                            progress.setConfirmText("Ok");
                            progress.setConfirmClickListener(sweetAlertDialog -> {
                                loading.dismissWithAnimation();

                                UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                Users user = new Users(UserId, fullName, password, email, noHp);
                                myRef.child(UserId).setValue(user);
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            });
                            progress.show();
                        } else {
                            loading.dismissWithAnimation();
                            SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
                            progress.setTitle("Gagal Daftarkan Akun");
                            progress.show();
                            Log.e("Register","ERROR REGISTER", task.getException());
                        }
                    });
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
    }
}