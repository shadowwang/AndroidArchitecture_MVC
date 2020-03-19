package com.example.androidarchitecture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DariesController implements DiaryModel.Observer<DiaryModel> {

    private DiaresRepository mDiaresRepository;
    private DariesFragment mView;
    private DairesAdapter mAdapter;

    public DariesController(DariesFragment dariesFragment) {
        this.mView = dariesFragment;
        this.mView.setHasOptionsMenu(true);
        this.mDiaresRepository = DiaresRepository.getInstance();

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new DairesAdapter(new ArrayList<DiaryModel>());
        mAdapter.onLongClickListener = new DairesAdapter.OnLongClickListener<DiaryModel>() {
            @Override
            public boolean onLongClick(View view, DiaryModel data) {
                showInputDialog(data);
                return false;
            }
        };
    }

    private void showInputDialog(final DiaryModel data) {
        final EditText editText = new EditText(mView.getContext());
        editText.setText(data.getContent());
        new AlertDialog.Builder(mView.getContext())
                .setTitle(data.getTitle())
                .setView(editText)
                .setPositiveButton(mView.getContext().getString(R.string.dialog_btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.setContent(editText.getText().toString());
                        mDiaresRepository.update(data);
                        loadDiaries();
                    }
                }).setNegativeButton(mView.getContext().getString(R.string.dialog_btn_cancel), null).show();
    }

    public void setDiariesList(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void loadDiaries() {
        mDiaresRepository.getAll(new DataCallback<List<DiaryModel>>() {
            @Override
            public void onSuccess(List<DiaryModel> data) {
                if (data == null || data.isEmpty()) {
                    return;
                }

                for (DiaryModel diaryModel : data) {
                    diaryModel.registObserver(DariesController.this);
                    mAdapter.updateDairy(data);
                }
            }

            @Override
            public void onError() {
                UIUtils.showMessage(mView.getContext(), mView.getString(R.string.dialog_error));
            }
        });
    }

    public void gotoWriteDiaries() {

    }

    @Override
    public void onUpdate(DiaryModel data) {
        mDiaresRepository.update(data);
        loadDiaries();
    }
}
