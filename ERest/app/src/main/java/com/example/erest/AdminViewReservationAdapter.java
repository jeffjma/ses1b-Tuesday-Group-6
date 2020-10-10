package com.example.erest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminViewReservationAdapter extends RecyclerView.Adapter<AdminViewReservationAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<AdminViewReservationItem> itemList = new ArrayList<>();


    public AdminViewReservationAdapter(Context mContext, ArrayList<AdminViewReservationItem> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AdminViewReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_viewreservation_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Textview
        holder.name.setText(itemList.get(position).getName());
        holder.pax.setText(itemList.get(position).getPax());
        holder.time.setText(itemList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView name;
        TextView pax;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            pax = itemView.findViewById(R.id.tv_pax);
            time = itemView.findViewById(R.id.tv_time);

        }
    }

}