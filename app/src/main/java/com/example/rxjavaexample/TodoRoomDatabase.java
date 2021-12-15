package com.example.rxjavaexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Callback;
import androidx.sqlite.db.SupportSQLiteDatabase;

// 데이터베이스에 테이블을 추가, 데이터베이스 버전 설정
@Database(entities = {Todo.class}, version = 2, exportSchema = false)
public abstract class TodoRoomDatabase extends RoomDatabase {
    // 데이터베이스를 정적 필드로 가짐
    public abstract TodoDao todoDao();
    private static TodoRoomDatabase INSTANCE;

    // 데이터베이스 명을 지정하고 데이터베이스 생성 후 반환
    public static TodoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class, "todo_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
