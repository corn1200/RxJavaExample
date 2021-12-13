package com.example.rxjavaexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mRepository;
    private LiveData<List<Todo>> mAllTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodos = mRepository.getAllTodos();
    }

    LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public void insert(Todo todo) {
        mRepository.insert(todo);
    }

    public void delete(Todo todo) {
        mRepository.delete(todo);
    }

    public void update(Todo todo) {
        mRepository.update(todo);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
