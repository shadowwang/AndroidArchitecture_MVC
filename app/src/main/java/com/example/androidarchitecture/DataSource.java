package com.example.androidarchitecture;

import java.util.List;

public interface DataSource<T> {

    void getAll(DataCallback<List<T>> callback);

    void get(String ids, DataCallback<T> callback);

    void update(T diary);

    void clear();

    void delete(String id);
}
