package com.mbs.favquotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    Context cx;
    ArrayList<Quotes> list;

    public RecyclerAdapter(Context cx, ArrayList<Quotes> list) {
        this.cx = cx;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        holder.author.setText(list.get(position).getAuthor());
        holder.description.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public static class MyHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView description;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
//            author = itemView.findViewById(R.id.txt_author);
            description = itemView.findViewById(R.id.tv_quote);
        }
    }
}
