package com.example.erest;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.*;

import com.bumptech.glide.Glide;

public class OrderAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mLayourInflater;
    public OrderAdapter(Context context)
    {
        this.mContext=context;
        mLayourInflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount()
    {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder
    {
        public ImageView imageView;
        public TextView tvName,tvDescription,tvPrice;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            convertView=mLayourInflater.inflate(R.layout.layout_order_item,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView=convertView.findViewById(R.id.iv_item);
            viewHolder.tvName=convertView.findViewById(R.id.tv_itemname);
            viewHolder.tvDescription=convertView.findViewById(R.id.tv_description);
            viewHolder.tvPrice=convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        //赋值
        viewHolder.tvName.setText("Food Name");
        viewHolder.tvDescription.setText("Food Description");
        viewHolder.tvPrice.setText("Price");
        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598194659802&di=8273ff185746ff71dc4d7597ff63a57a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190519%2Fe5dc5d755d0643819c09dc40e67b5a80.jpeg").into(viewHolder.imageView);

        return convertView;

    }

}
