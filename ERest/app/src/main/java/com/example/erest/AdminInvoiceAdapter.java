package com.example.erest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminInvoiceAdapter extends RecyclerView.Adapter<AdminInvoiceAdapter.ViewHolder>{


        private Context mContext;
        private ArrayList<AdminInvoiceItem> aitemList = new ArrayList<>();


        public AdminInvoiceAdapter(Context mContext, ArrayList<AdminInvoiceItem> aitemList) {
            this.mContext = mContext;
            this.aitemList = aitemList;
        }

        @NonNull
        @Override
        public AdminInvoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.invoice_item, parent, false);

            return new AdminInvoiceAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdminInvoiceAdapter.ViewHolder holder, int position) {

            holder.id.setText(aitemList.get(position).getId());
            holder.name.setText(aitemList.get(position).getName());
            holder.onClick(position);
        }

       private void removeAt(int position) {
        aitemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, aitemList.size());
       }

        @Override
        public int getItemCount()
        {
            return aitemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView id;
            TextView name;
            Button mbtn_detail;
            Button mbtn_delete;



            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.tv_invoiceid);
                name = itemView.findViewById(R.id.tv_invoicename);
                mbtn_detail = itemView.findViewById(R.id.btn_invoice);
                mbtn_delete=itemView.findViewById(R.id.btn_back);


            }


            public void onClick(final int position)
            {
                mbtn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            removeAt(position);
                    }
                });


                mbtn_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context=view.getContext();
                        Intent intent=new Intent(context,AdminInvoiceDetail.class);
                        intent.putExtra("ID",aitemList.get(position).getId());
                        intent.putExtra("Name",aitemList.get(position).getName());
                        intent.putExtra("Price",aitemList.get(position).getPrice());
                        intent.putExtra("Food",aitemList.get(position).getFood());
                        intent.putExtra("Discount",aitemList.get(position).getDiscount());
                        context.startActivity(intent);

                    }
                });
            }

        }

    }

