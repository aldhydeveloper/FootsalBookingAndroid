package com.ns.queenfutsal.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ns.queenfutsal.R;

public class BookingAdapter extends RecyclerView.ViewHolder {

    public ImageView IVLapangan;
    public TextView tvName, tvHarga ,tvDesc;

    public BookingAdapter(@NonNull View itemView) {
        super(itemView);
        IVLapangan = itemView.findViewById(R.id.IVLapangan);
        tvName = itemView.findViewById(R.id.tvName);
        tvHarga = itemView.findViewById(R.id.tvHarga);
        tvDesc = itemView.findViewById(R.id.tvDesc);
    }
}
