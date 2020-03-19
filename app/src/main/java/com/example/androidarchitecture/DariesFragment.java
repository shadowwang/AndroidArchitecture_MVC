package com.example.androidarchitecture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class DariesFragment extends Fragment {

    private DariesController mDariesController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDariesController = new DariesController(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_daries, container, false);
        if (this.mDariesController != null) {
            this.mDariesController.setDiariesList((RecyclerView) view.findViewById(R.id.rv_daries_list));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mDariesController != null) {
            this.mDariesController.loadDiaries();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_write, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                if (this.mDariesController != null) {
                    this.mDariesController.gotoWriteDiaries();
                }
                return true;
        }
        return false;
    }
}
