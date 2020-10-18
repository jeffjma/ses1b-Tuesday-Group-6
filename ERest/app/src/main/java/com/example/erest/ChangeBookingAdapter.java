package com.example.erest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChangeBookingAdapter extends RecyclerView.Adapter<ChangeBookingAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ChangeBookingItem> itemList = new ArrayList<>();
    private ChangeBookingInterface changeBookingInterface;


    public ChangeBookingAdapter(Context mContext, ArrayList<ChangeBookingItem> itemList, ChangeBookingInterface changeBookingInterface) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.changeBookingInterface = changeBookingInterface;
    }

    @NonNull
    @Override
    public ChangeBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_item, parent, false);

        return new ChangeBookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeBookingAdapter.ViewHolder holder, int position) {
        //Textview
        holder.date.setText(itemList.get(position).getDate());
        holder.time.setText(itemList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView date;
        TextView time;
        Button changeBooking;
        Button changeOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);
            changeBooking = itemView.findViewById(R.id.btn_edit_booking);
            changeOrder = itemView.findViewById(R.id.btn_edit_order);
            changeBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeBookingInterface.onBookingButtonClick(getAdapterPosition(), date.getText().toString(), time.getText().toString(), itemList.get(getAdapterPosition()).getPax());
                }
            });
            changeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeBookingInterface.onOrderButtonClick(getAdapterPosition(), date.getText().toString(), time.getText().toString());
                }
            });
        }
    }
}
