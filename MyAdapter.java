package com.example.tripti;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {

    Context context;
    ArrayList<historymodel> arrayList;

    public MyAdapter(Context context, ArrayList<historymodel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.singlerow,parent,false);

        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyviewHolder holder, int position) {
              holder.name.setText(arrayList.get(position).getName());
              holder.number.setText(arrayList.get(position).getMobile());
              holder.add.setText(arrayList.get(position).getAddress());
              holder.qty.setText(arrayList.get(position).getQty()+" KG");
              holder.time.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView name,number,add,qty,time;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.singlename);
            number=itemView.findViewById(R.id.singlephone);
            add=itemView.findViewById(R.id.singleadd);
            qty=itemView.findViewById(R.id.singleqty);
            time=itemView.findViewById(R.id.singletime);

        }
    }
}
