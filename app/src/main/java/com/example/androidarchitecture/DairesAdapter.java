package com.example.androidarchitecture;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DairesAdapter extends RecyclerView.Adapter<DairesViewHolder> {

    private List<DiaryModel> mDairies;

    public OnLongClickListener<DiaryModel> onLongClickListener;


    public DairesAdapter(List<DiaryModel> dairies) {
        this.mDairies = dairies;
        notifyDataSetChanged();
    }

    public void updateDairy(List<DiaryModel> dairies) {
        if (this.mDairies == null) {
            this.mDairies = new ArrayList<>(dairies.size());
        }

        if (!this.mDairies.isEmpty()) {
            this.mDairies.clear();
        }

        this.mDairies.addAll(dairies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DairesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DairesViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DairesViewHolder holder, int position) {
        if (mDairies == null || mDairies.isEmpty()) {
            return;
        }

        final DiaryModel data = mDairies.get(position);
        holder.onBindView(data);
        holder.mOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onLongClickListener != null && onLongClickListener.onLongClick(view, data);
            }
        };
    }

    @Override
    public int getItemCount() {
        return mDairies == null || mDairies.isEmpty() ? 0 : mDairies.size();
    }

    interface OnLongClickListener<T> {
        boolean onLongClick(View view, T data);
    }
}