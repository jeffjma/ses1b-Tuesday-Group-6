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


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<MenuItem> itemList = new ArrayList<>();

    private ViewMenuInterface viewMenuInterface;


    public RecyclerAdapter(Context mContext, ArrayList<MenuItem> itemList, ViewMenuInterface viewMenuInterface) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.viewMenuInterface = viewMenuInterface;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Textview
        holder.name.setText(itemList.get(position).getName());
        holder.price.setText(itemList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView name;
        TextView price;
        Button order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            order = itemView.findViewById(R.id.btn_order);
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewMenuInterface.onItemClick(getAdapterPosition(), name.getText().toString(), price.getText().toString());
                    //Order newOrder = new Order();
                    //newOrder.addToCart(name.getText().toString(), price.getText().toString());
                }
            });
        }
    }

}
