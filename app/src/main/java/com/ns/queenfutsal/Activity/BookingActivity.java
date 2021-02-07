package com.ns.queenfutsal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ns.queenfutsal.Adapter.BookingAdapter;
import com.ns.queenfutsal.Model.Lapangan;
import com.ns.queenfutsal.R;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookingActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;

    DatabaseReference reference;

    FirebaseRecyclerAdapter<Lapangan, BookingAdapter> adapter;
    FirebaseRecyclerOptions<Lapangan> options;

    SweetAlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        btnBack = findViewById(R.id.btnBack);

        reference = FirebaseDatabase.getInstance().getReference("Lapangan");

        btnBack.setOnClickListener(view -> super.onBackPressed());
        initEvent();
    }

    private void initEvent() {

        recyclerView = findViewById(R.id.rvBooking);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

         options = new FirebaseRecyclerOptions.Builder<Lapangan>()
                 .setQuery(reference,Lapangan.class).build();

        adapter = new FirebaseRecyclerAdapter<Lapangan, BookingAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookingAdapter holder, int position, @NonNull Lapangan model) {
//                if (model.getGambar().isEmpty() && model.getGambar().equals("")){
//                    holder.IVLapangan.setImageResource(R.drawable.ic_event);
//                }else{
//                    Picasso.get().load(model.getGambar()).into(holder.IVLapangan);
//                }
                Log.d("CHECK BOOKING", model.getGambar() + ",  " + model.getHarga() + ",  " + model.getNama() + ",  " + model.getId());
                holder.tvName.setText(model.getNama());
                holder.tvHarga.setText(String.format("Rp%s", model.getHarga()));
                holder.itemView.setOnClickListener(view -> {
                    Toast.makeText(BookingActivity.this, "OKEY  " + adapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                });
            }

            @NonNull
            @Override
            public BookingAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_booking,parent,false);
                return new BookingAdapter(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}