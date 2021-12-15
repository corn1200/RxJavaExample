package com.example.rxjavaexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

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
        new InsertAsyncTask(mTodoDao).execute(todo);
    }

    // 백그라운드에서 데이터를 삭제
    public void delete(Todo todo) {
        new DeleteAsyncTask(mTodoDao).execute(todo);
    }

    // 백그라운드에서 데이터를 업데이트
    public void update(Todo todo) {
        new UpdateAsyncTask(mTodoDao).execute(todo);
    }

    // 백그라운드에서 모든 데이터 삭제
    public void deleteAll() {
        new DeleteAllAsyncTask(mTodoDao).execute();
    }

    // AsyncTask 에서 사용할 dao 를 초기화 후 파라미터 값 삽입
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

    // AsyncTask 에서 사용할 dao 를 초기화 후 파라미터 값 삭제
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

    // AsyncTask 에서 사용할 dao 를 초기화 후 데이터 전체 삭제
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

    // AsyncTask 에서 사용할 dao 를 초기화 후 데이터 업데이트
    private static class UpdateAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDao mAsyncTaskDao;

        UpdateAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mAsyncTaskDao.update(todos[0]);
            return null;
        }
    }
}
