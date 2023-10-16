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

public class Myadapter1 extends RecyclerView.Adapter<Myadapter1.MyviewHolder> {

    Context context;
    ArrayList<historymodel1> arrayList;

    public Myadapter1(Context context, ArrayList<historymodel1> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Myadapter1.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.singlerow1,parent,false);

        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter1.MyviewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getRecname());
        holder.number.setText(arrayList.get(position).getRecmob());
        holder.add.setText(arrayList.get(position).getRecaddress());
        holder.time.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView name,number,add,time;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.single1name);
            number=itemView.findViewById(R.id.single1phone);
            add=itemView.findViewById(R.id.single1add);
            time=itemView.findViewById(R.id.single1time);

        }
    }
}
