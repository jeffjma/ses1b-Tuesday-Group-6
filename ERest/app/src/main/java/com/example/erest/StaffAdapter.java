package com.example.erest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Staff> staffList = new ArrayList<>();


    public StaffAdapter(Context mContext, ArrayList<Staff> staffList) {
        this.mContext = mContext;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Textview
        holder.name.setText(staffList.get(position).getName());
        holder.position.setText(staffList.get(position).getPosition());
        holder.email.setText(staffList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView name;
        TextView email;
        TextView position;

        public ViewHolder(@NonNull View staffView) {
            super(staffView);

            name = staffView.findViewById(R.id.tv_name);
            email = staffView.findViewById(R.id.tv_email);
            position = staffView.findViewById(R.id.tv_position);

        }
    }

}
