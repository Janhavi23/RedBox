package com.example.red_box;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapterEmail extends RecyclerView.Adapter<adapterEmail.MyViewHolder> {

    Context context;
    ArrayList<Email> candidateArrayList;
    ItemClickListener mItemClickListener;

    public adapterEmail(Context context, ArrayList<Email> candidateArrayList,ItemClickListener itemClickListener) {
        this.context = context;
        this.candidateArrayList = candidateArrayList;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public adapterEmail.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapterEmail.MyViewHolder holder, int position) {
        Email email = candidateArrayList.get(position);
        holder.email.setText(email.decrypted_Email);
        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(candidateArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        Log.i("List size:",String.valueOf(candidateArrayList.size()));
        return candidateArrayList.size();
    }

    public interface ItemClickListener{
        void onItemClick(Email candidates);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.Email);
        }
    }
}
