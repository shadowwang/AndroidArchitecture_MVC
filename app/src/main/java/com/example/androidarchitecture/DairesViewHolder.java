package com.example.androidarchitecture;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DairesViewHolder extends RecyclerViewHolder<DiaryModel> {

    public View.OnLongClickListener mOnLongClickListener;


    public DairesViewHolder(ViewGroup parent) {
        super(parent, R.layout.list_diary_item);
    }

    @Override
    protected void onBindView(DiaryModel data) {
        super.onBindView(data);
        TextView title = (TextView) itemView.findViewById(R.id.tv_title);
        title.setText(data.getTitle());

        TextView content = (TextView) itemView.findViewById(R.id.tv_content);
        content.setText(data.getContent());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mOnLongClickListener != null && mOnLongClickListener.onLongClick(view);
            }
        });
    }
}
