package com.example.rxjavaexample;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface TodoDao {
    @Insert
    void insert(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table ORDER BY todo ASC")
    LiveData<List<Todo>> getAllTodos();
}
