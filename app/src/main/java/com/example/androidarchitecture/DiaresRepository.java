package com.example.androidarchitecture;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DiaresRepository implements DataSource<DiaryModel> {

    private DariesLocalDataSource mDariesLocalDataSource;

    private LinkedHashMap<String, DiaryModel> mMemoryCache;

    private DiaresRepository() {
        this.mMemoryCache = new LinkedHashMap<>();
        this.mDariesLocalDataSource = DariesLocalDataSource.getInstance();
    }

    private static volatile DiaresRepository mInstance;

    public static DiaresRepository getInstance() {
        if (mInstance == null) {
            synchronized (DiaresRepository.class) {
                if (mInstance == null) {
                    mInstance = new DiaresRepository();
                }
            }
        }
        return mInstance;
    }

    private void updateMemeorycache(List<DiaryModel> data) {
        if (data == null || data.isEmpty()) {
            return;
        }

        for (DiaryModel diaryModel : data) {
            mMemoryCache.put(diaryModel.getId(), diaryModel);
        }
    }

    private DiaryModel getDiaryById(String id) {
        if (mMemoryCache.isEmpty()) {
            return null;
        }

        return mMemoryCache.get(id);
    }

    @Override
    public void getAll(final DataCallback<List<DiaryModel>> callback) {
        if (callback == null) {
            return;
        }

        if (!mMemoryCache.isEmpty()) {
            callback.onSuccess(new ArrayList<DiaryModel>(mMemoryCache.values()));
            return;
        }

        mDariesLocalDataSource.getAll(new DataCallback<List<DiaryModel>>() {
            @Override
            public void onSuccess(List<DiaryModel> data) {
                updateMemeorycache(data);
                callback.onSuccess(new ArrayList<DiaryModel>(mMemoryCache.values()));
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void get(String id, final DataCallback<DiaryModel> callback) {
        final DiaryModel diaryModel = getDiaryById(id);
        if (diaryModel == null) {
            mDariesLocalDataSource.get(id, new DataCallback<DiaryModel>() {
                @Override
                public void onSuccess(DiaryModel data) {
                    mMemoryCache.put(data.getId(), data);
                    callback.onSuccess(data);
                }

                @Override
                public void onError() {
                    callback.onError();
                }
            });
        } else {
            callback.onSuccess(diaryModel);
        }
    }

    @Override
    public void update(DiaryModel diary) {
        mDariesLocalDataSource.update(diary);
        mMemoryCache.put(diary.getId(), diary);
    }

    @Override
    public void clear() {
        mMemoryCache.clear();
        mDariesLocalDataSource.clear();
    }

    @Override
    public void delete(String id) {
        mMemoryCache.remove(id);
        mDariesLocalDataSource.delete(id);
    }
}
