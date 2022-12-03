package com.example.reptile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerTypeRVAdapter extends RecyclerView.Adapter<VerTypeRVAdapter.ViewHolder>{
    private Context context;
    private ArrayList<TypeData> typeList;

    public VerTypeRVAdapter(Context context, ArrayList<TypeData> list){
        this.typeList = list;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        protected RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.text_type_name);
            this.recyclerView = itemView.findViewById(R.id.hor_list_rv);
        }
    }

    @NonNull
    @Override
    public VerTypeRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_list_type, parent, false);
        return new VerTypeRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VerTypeRVAdapter.ViewHolder holder, int position) {
        HorCageRVAdapter hRVAdapter = new HorCageRVAdapter(typeList.get(position).getReptileList());

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setAdapter(hRVAdapter);
        holder.textView.setText(typeList.get(position).getTypeName());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}
