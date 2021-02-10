package com.ns.queenfutsal.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ns.queenfutsal.Adapter.MyTimeSlotAdapter;
import com.ns.queenfutsal.Common.Common;
import com.ns.queenfutsal.Model.Lapangan;
import com.ns.queenfutsal.R;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FormBookingActivity extends AppCompatActivity {

    TextView tvName, tvHarga, tvDesc, tvTanggal;
    Button btnSaveBooking, btnTanggal;
    ImageView btnBack;
    RecyclerView rvTimeBooking;

    SweetAlertDialog loading;

    DatabaseReference mRef;

    String[] timeSlot;


    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_booking);

        tvName = findViewById(R.id.tvName);
        tvHarga = findViewById(R.id.tvHarga);
        tvDesc = findViewById(R.id.tvDesc);
        tvTanggal = findViewById(R.id.tvTanggal);
        btnSaveBooking = findViewById(R.id.btnSaveBooking);
        btnTanggal = findViewById(R.id.btnTanggal);
        btnBack = findViewById(R.id.btnBack);
        rvTimeBooking = findViewById(R.id.rvTimeBooking);

        timeSlot = getResources().getStringArray(R.array.Time_Slot);

        mRef = FirebaseDatabase.getInstance().getReference("Lapangan");

        initView();
        initEvent();
    }

    private void initView() {
        loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loading.setTitle("Loading");
        loading.show();
        mRef.child(Common.KEY_LAPANGAN).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Lapangan lapangan = snapshot.getValue(Lapangan.class);
                    tvName.setText(lapangan.getNama());
                    tvHarga.setText(String.format("Rp. %s", lapangan.getHarga()));
                    tvDesc.setText(lapangan.getGambar());
                    loading.dismissWithAnimation();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GridLayoutManager gridLayout = new GridLayoutManager(this, 3);
        rvTimeBooking.setLayoutManager(gridLayout);

        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(this, timeSlot);
        rvTimeBooking.setAdapter(adapter);
    }

    private void initEvent() {

        btnTanggal.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            @SuppressLint("DefaultLocale")
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        tvTanggal.setText(String.format("%d / %d / %d", dayOfMonth, monthOfYear + 1, year));
                        view1.setMinDate(System.currentTimeMillis() - 1000);
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        });
    }
}