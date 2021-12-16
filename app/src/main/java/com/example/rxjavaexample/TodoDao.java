package com.example.rxjavaexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

// 데이터 접근 객체로 지정
@Dao
public interface TodoDao {
    // Completable 은 데이터를 발행하지 않고 수행만 한 후 종료한다
    // 중복 삽입으로 인한 예외 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(Todo todo);

    // 해당 데이터를 삭제
    @Delete
    Completable delete(Todo todo);

    // 모든 데이터를 삭제
    @Query("DELETE FROM todo_table")
    Completable deleteAll();

    // 데이터 업데이트
    @Update
    Completable update(Todo... todos);

    // 모든 데이터를 조회
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    LiveData<List<Todo>> getAllTodos();
}
