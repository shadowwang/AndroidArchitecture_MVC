package com.example.androidarchitecture;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class DariesLocalDataSource implements DataSource<DiaryModel> {

    private static final String SP_DIARIY_DATA = "diariy_data";
    private static final String SP_KEY_ALL_DATA = "all_data";

    private static volatile DariesLocalDataSource mInstance;
    //本地数据内存缓存
    private static LinkedHashMap<String, DiaryModel> LOCAL_DATA = new LinkedHashMap<>();

    private SpUtils mSpUtils;

    public static DariesLocalDataSource getInstance() {
        if (mInstance == null) {
            synchronized (DariesLocalDataSource.class) {
                if (mInstance == null) {
                    mInstance = new DariesLocalDataSource();
                }
            }
        }

        return mInstance;
    }

    private DariesLocalDataSource() {
        mSpUtils = SpUtils.getInstance(SP_DIARIY_DATA);
        String diaryStr = mSpUtils.get(SP_KEY_ALL_DATA);
        LOCAL_DATA = json2Obj(diaryStr);

        if (LOCAL_DATA == null || LOCAL_DATA.isEmpty()) {
            LOCAL_DATA = MockDaries.mock();
        }
    }


    private String obj2String() {
        return GsonUtils.toJson(LOCAL_DATA);
    }

    private LinkedHashMap<String, DiaryModel> json2Obj(String diaryStr) {
            return GsonUtils.fromJson(diaryStr, new TypeToken<LinkedHashMap<String, DiaryModel>>(){}.getType());
    }

    @Override
    public void getAll(DataCallback<List<DiaryModel>> callback) {
        if (LOCAL_DATA.isEmpty()) {
            callback.onError();
        } else {
            callback.onSuccess(new ArrayList<DiaryModel>(LOCAL_DATA.values()));
        }
    }

    @Override
    public void get(String id, DataCallback<DiaryModel> callback) {
        if (LOCAL_DATA.isEmpty()) {
            callback.onError();
        } else {
            final DiaryModel diaryModel = LOCAL_DATA.get(id);
            if (diaryModel == null) {
                callback.onError();
            } else {
                callback.onSuccess(diaryModel);
            }
        }
    }

    @Override
    public void update(DiaryModel diary) {
        if (diary == null) {
            return;
        }

        LOCAL_DATA.put(diary.getId(), diary);
        mSpUtils.put(SP_KEY_ALL_DATA, obj2String());
    }

    @Override
    public void clear() {
        LOCAL_DATA.clear();
        mSpUtils.remove(SP_KEY_ALL_DATA);
    }

    @Override
    public void delete(String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }

        LOCAL_DATA.remove(id);
        mSpUtils.remove(id);
    }
}
