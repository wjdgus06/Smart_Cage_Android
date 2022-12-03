package com.example.reptile;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TypeRVAdapter extends RecyclerView.Adapter<TypeRVAdapter.ViewHolder>{
    private ArrayList<String> typeList;

    class ViewHolder extends RecyclerView.ViewHolder {
        protected RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recyclerView = itemView.findViewById(R.id.ver_list_rv);
        }
    }

    @NonNull
    @Override
    public TypeRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TypeRVAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
