package com.example.androidarchitecture;

public interface DataCallback<T> {
    void onSuccess(T data);

    void onError();
}
