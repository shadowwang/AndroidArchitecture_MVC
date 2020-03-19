package com.example.androidarchitecture;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DiaryModel {

    private List<Observer<DiaryModel>> observers = new ArrayList<>();

    public String getId() {
        return id;
    }

    private String id;

    private String title;

    private String content;

    public DiaryModel(String title, String content) {
        this.title = title;
        this.content = content;
        this.id = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyObservers();
    }

    interface Observer<T> {
        void onUpdate(T data);
    }

    public void registObserver(Observer<DiaryModel> observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        if (observers == null || observers.isEmpty()) {
            return;
        }

        for (Observer<DiaryModel> observer : observers) {
            observer.onUpdate(this);
        }
    }
}
