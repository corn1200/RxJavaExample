package com.example.rxjavaexample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// 테이블 명 지정
@Entity(tableName = "todo_table")
public class Todo {
    // 기본키로 설정 및 데이터 추가 시 값 자동 증가
    @PrimaryKey(autoGenerate = true)
    private int id;

    // 컬럼 명을 지정
    @NonNull
    @ColumnInfo(name = "todo")
    private String mTodo;

    // 기본 생성자
    public Todo(@NonNull String todo) {
        this.mTodo = todo;
    }

    // id 와 데이터 내용 값으로 객체 생성
    // 데이터 모델은 하나의 생성자만 가질 수 있기 때문에 Ignore 어노테이션 추가
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
