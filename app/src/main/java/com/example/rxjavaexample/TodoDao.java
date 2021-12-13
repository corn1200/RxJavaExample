package com.example.rxjavaexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Update
    void update(Todo... todos);

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    LiveData<List<Todo>> getAllTodos();
}
