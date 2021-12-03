package com.example.rxjavaexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodos;

    TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTodos();
    }

    LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public void insert(Todo todo) {
        new InsertAsyncTask(mTodoDao).execute(todo);
    }

    private class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao mAsyncTaskDao;

        InsertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            mAsyncTaskDao.insert(todos[0]);
            return null;
        }
    }
}
