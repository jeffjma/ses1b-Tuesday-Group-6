package com.example.erest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class AdminViewOrderAdapter  extends RecyclerView.Adapter<AdminViewOrderAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<AdminViewOrderItem> aitemList = new ArrayList<>();


    public AdminViewOrderAdapter(Context mContext, ArrayList<AdminViewOrderItem> aitemList) {
        this.mContext = mContext;
        this.aitemList = aitemList;
    }

    @NonNull
    @Override
    public AdminViewOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_vieworder_items, parent, false);

        return new AdminViewOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewOrderAdapter.ViewHolder holder, int position) {

        holder.user.setText(aitemList.get(position).getUser());
        holder.food.setText(aitemList.get(position).getFood());
        holder.price.setText(aitemList.get(position).getPrice());
        //if(aitemList.get(position).isStatus())
        //{
        holder.btn_ok.setVisibility(View.VISIBLE);
        holder.onClick(position);
        //}

    }

    @Override
    public int getItemCount()
    {
        return aitemList.size();
    }

    //delete
    private void removeAt(int position) {
        aitemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, aitemList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        TextView food;
        TextView price;
        boolean status;
        Button btn_ok;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.tv_user);
            food = itemView.findViewById(R.id.tv_food);
            price = itemView.findViewById(R.id.tv_price);
            btn_ok = itemView.findViewById(R.id.btn_ok);

        }
        public void onClick(final int position)
        {
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(position);

                }
            });
        }
    }

}
