package com.example.rxjavaexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// 뷰모델을 상속한다
public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mRepository;
    private LiveData<List<Todo>> mAllTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodos = mRepository.getAllTodos();
    }

    // 뷰모델 객체 내의 모든 데이터를 반환
    LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    // 레포지토리를 통해 데이터 삽입 메서드 실행
    public void insert(Todo todo) {
        mRepository.insert(todo);
    }

    // 레포지토리를 통해 데이터 삭제 메서드 실행
    public void delete(Todo todo) {
        mRepository.delete(todo);
    }

    // 레포지토리를 통해 데이터 업데이트 메서드 실행
    public void update(Todo todo) {
        mRepository.update(todo);
    }

    // 레포지토리를 통해 모든 데이터 삭제 메서드 실행행
   public void deleteAll() {
        mRepository.deleteAll();
    }
}
