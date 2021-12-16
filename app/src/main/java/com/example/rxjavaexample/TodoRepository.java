package com.example.rxjavaexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodos;

    // 룸 데이터베이스 객체를 생성한 후 dao 를 통해 모든 데이터를 반환
    TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTodos();
    }

    // 현재 레포지토리에 저장 된 모든 데이터를 반환
    LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    // 백그라운드에서 데이터를 삽입
    public void insert(Todo todo) {
        mTodoDao.insert(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    // 백그라운드에서 데이터를 삭제
    public void delete(Todo todo) {
        mTodoDao.delete(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    // 백그라운드에서 데이터를 업데이트
    public void update(Todo todo) {
        mTodoDao.update(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    // 백그라운드에서 모든 데이터 삭제
    public void deleteAll() {
        mTodoDao.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
