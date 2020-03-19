package com.example.androidarchitecture;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public T data;

    public RecyclerViewHolder(ViewGroup parent, int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    @CallSuper
    protected void onBindView(T data) {
        this.data = data;
    }
}
