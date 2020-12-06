package com.example.a6_lv5.adapter.RecycleViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a6_lv5.Bill;
import com.example.a6_lv5.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private ArrayList billArrayList;

    public MyRecyclerViewAdapter(ArrayList billArrayList) {
        this.billArrayList = billArrayList;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        Bill bill = (Bill) billArrayList.get(position);
        holder.first.setText(bill.getTime());
        holder.second.setText(bill.getConsumptionCategory());
        holder.third.setText(bill.getPrice() + "元");
    }

    @Override
    public int getItemCount() {
        return billArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView first;
        private TextView second;
        private TextView third;

        public ViewHolder(View view) {
            super(view);
            first = view.findViewById(R.id.tv_first);
            second = view.findViewById(R.id.tv_second);
            third = view.findViewById(R.id.tv_third);
        }
    }
}