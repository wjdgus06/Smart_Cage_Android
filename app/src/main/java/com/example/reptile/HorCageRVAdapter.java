package com.example.reptile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorCageRVAdapter extends RecyclerView.Adapter<HorCageRVAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ReptileData> cageList;

    public HorCageRVAdapter(Context context, ArrayList<ReptileData> list){
        this.context = context;
        this.cageList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView cageName;
        protected ImageButton imgBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cageName = itemView.findViewById(R.id.text_reptile_name);
            this.imgBtn = itemView.findViewById(R.id.btn_move_cage);
        }
    }

    @NonNull
    @Override
    public HorCageRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_list_cage, null);
        return new HorCageRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HorCageRVAdapter.ViewHolder holder, int position) {
        holder.imgBtn.setImageResource(cageList.get(position).getImgPath());
        holder.cageName.setText(cageList.get(position).getReptileName());
        holder.imgBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, CageInfoActivity.class);
            intent.putExtra("cageName", cageList.get(position).getReptileName());
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }

    @Override
    public int getItemCount() {
        return cageList.size();
    }
}
