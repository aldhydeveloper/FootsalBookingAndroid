package com.ns.queenfutsal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ns.queenfutsal.Model.Users;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    Button btnRegister;
    EditText edtFullname, edtUsername, edtPassword, edtEmail, edtNoHp;

    FirebaseDatabase database;
    DatabaseReference myRef;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnRegister = findViewById(R.id.btnRegister);

        edtFullname = findViewById(R.id.edtFullname);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtNoHp = findViewById(R.id.edtNoHp);


        initEvent();
    }

    private void initEvent() {
        btnRegister.setOnClickListener(view -> {
            String idUsers = String.valueOf(id + 1);
            String fullName = edtFullname.getText().toString();
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String email = edtEmail.getText().toString();
            String noHp = edtNoHp.getText().toString();

            SweetAlertDialog loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            loading.getProgressHelper().setBarColor(Color.parseColor("#222121"));
            loading.setTitle("Loading");
            loading.show();

            if ((fullName.isEmpty() || fullName.equals(" ")) && (username.isEmpty() || username.equals(" ")) &&
                    (password.isEmpty() || password.equals(" ")) && (email.isEmpty() || email.equals(" ")) &&
                    (noHp.isEmpty() || noHp.equals(" "))) {
                loading.dismissWithAnimation();
                SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
                progress.setTitle("Gagal Daftarkan Akun");
                progress.setContentText("Semua field tidak boleh kosong !!!");
                progress.show();

            } else {
                SweetAlertDialog progress = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                progress.setTitle("Berhasil Daftarkan Akun");
                progress.setConfirmText("Login");
                progress.setConfirmClickListener(sweetAlertDialog -> {
                    loading.dismissWithAnimation();

                    Users user = new Users(idUsers, fullName, username, password, email, noHp);
                    myRef.child(idUsers).setValue(user);
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                });
                progress.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
    }
}