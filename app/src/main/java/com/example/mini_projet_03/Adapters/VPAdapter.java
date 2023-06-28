package com.example.mini_projet_03.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.R;

import java.util.ArrayList;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.ViewHolder> {

    ArrayList<String> messages;

    public VPAdapter(ArrayList<String> messages){
        this.messages = messages;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_itemViewPager2Message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemViewPager2Message = itemView.findViewById(R.id.tv_itemViewPager2Message);
        }
    }

    @NonNull
    @Override
    public VPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager2 , parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VPAdapter.ViewHolder holder, int position) {
        holder.tv_itemViewPager2Message.setText(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
