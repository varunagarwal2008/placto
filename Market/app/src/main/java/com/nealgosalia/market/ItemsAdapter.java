package com.nealgosalia.market;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private List<Item> listItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemPrice;
        public TextView itemQuantity;

        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.itemName);
            itemPrice = (TextView) view.findViewById(R.id.itemPrice);
            itemQuantity = (TextView) view.findViewById(R.id.itemQuantity);
        }
    }

    public ItemsAdapter(List<Item> lectureList) {
        this.listItems = lectureList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = listItems.get(position);
        holder.itemName.setText(item.getProduct());
        holder.itemPrice.setText(item.getPrice());
        holder.itemQuantity.setText(item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
