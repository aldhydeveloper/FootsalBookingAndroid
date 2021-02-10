package com.ns.queenfutsal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ns.queenfutsal.Model.TimeSlot;
import com.ns.queenfutsal.R;

import java.util.ArrayList;
import java.util.List;

public class MyTimeSlotAdapter  extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {

    Context context;
    String[] timeSlot;

    public MyTimeSlotAdapter(Context context, String[] timeSlot) {
        this.context = context;
        this.timeSlot = timeSlot;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_time_slot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_time_slot.setText(timeSlot[position]);
        holder.txt_time_slot_description.setText("Tersedia");
        holder.itemView.setOnClickListener(view -> {
            holder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
            holder.txt_time_slot_description.setText("FULL");
            holder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.white));
        });
    }

    @Override
    public int getItemCount() {
        return timeSlot.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_time_slot, txt_time_slot_description;
        CardView card_time_slot;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_time_slot = itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description = itemView.findViewById(R.id.txt_time_slot_description);
            card_time_slot = itemView.findViewById(R.id.card_time_slot);
        }
    }
}