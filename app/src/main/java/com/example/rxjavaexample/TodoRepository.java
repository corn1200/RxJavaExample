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

    public void delete(Todo todo) {
        new DeleteAsyncTask(mTodoDao).execute(todo);
    }

    public void update(Todo todo) {
        new UpdateAsyncTask(mTodoDao).execute(todo);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mTodoDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {
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

    private static class DeleteAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao mAsyncTaskDao;

        DeleteAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mAsyncTaskDao.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao mAsyncTaskDao;

        DeleteAllAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDao mAsyncTaskDao;

        public UpdateAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mAsyncTaskDao.update(todos[0]);
            return null;
        }
    }
}
