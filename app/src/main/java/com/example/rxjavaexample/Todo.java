package com.example.rxjavaexample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "todo")
    private String mTodo;

    public Todo(@NonNull String todo) {
        this.mTodo = todo;
    }

    public String getTodo() {
        return this.mTodo;
    }
}
