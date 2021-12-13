package com.example.rxjavaexample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "todo")
    private String mTodo;

    public Todo(@NonNull String todo) {
        this.mTodo = todo;
    }

    @Ignore
    public Todo(int id, @NonNull String todo) {
        this.id = id;
        this.mTodo = todo;
    }

    public String getTodo() {
        return this.mTodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
