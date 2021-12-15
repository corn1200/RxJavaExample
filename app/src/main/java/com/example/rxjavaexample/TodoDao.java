package com.example.rxjavaexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// 데이터 접근 객체로 지정
@Dao
public interface TodoDao {
    // 중복 삽입으로 인한 예외 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo todo);

    // 해당 데이터를 삭제
    @Delete
    void delete(Todo todo);

    // 모든 데이터를 삭제
    @Query("DELETE FROM todo_table")
    void deleteAll();

    // 데이터 업데이트
    @Update
    void update(Todo... todos);

    // 모든 데이터를 조회
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    LiveData<List<Todo>> getAllTodos();
}
