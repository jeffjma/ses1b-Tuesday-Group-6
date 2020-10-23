package com.example.erest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Discount> discountList = new ArrayList<>();


    public DiscountAdapter(Context mContext, ArrayList<Discount> discountList) {
        this.mContext = mContext;
        this.discountList = discountList;
    }

    @NonNull
    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Textview
        holder.name.setText(discountList.get(position).getName());
        holder.amount.setText(discountList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        TextView name;
        TextView amount;

        public ViewHolder(@NonNull View discountView) {
            super(discountView);

            name = discountView.findViewById(R.id.tv_name);
            amount = discountView.findViewById(R.id.tv_amount);

        }
    }

}
