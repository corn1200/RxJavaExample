package com.example.rxjavaexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {
    private final LayoutInflater mInflater;
    private List<Todo> mTodos;
    private static ClickListener clickListener;

    // 레이아웃 인플레이터 설정
    TodoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // 아이템에 인플레이터로 데이터를 넣고 반환
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    // 각 데이터의 포지션을 기준으로 뷰홀더 아이템에 텍스트 추가
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        if (mTodos != null) {
            Todo current = mTodos.get(position);
            holder.todoItemView.setText(current.getTodo());
        } else {
            holder.todoItemView.setText("No Todo");
        }
    }

    // 데이터 변경이 있을 시 실제 데이터를 어댑터 필드에 저장하고
    // LiveData 객체에 데이터가 변경 됐다는 알림을 보냄
    void setTodos(List<Todo> todos) {
        mTodos = todos;
        notifyDataSetChanged();
    }

    // 어댑터에 있는 데이터의 길이 반환
    @Override
    public int getItemCount() {
        if (mTodos != null) {
            return mTodos.size();
        } else {
            return 0;
        }
    }

    // 포지션에 해당하는 데이터를 반환
    public Todo getTodoAtPosition(int position) {
        return mTodos.get(position);
    }

    // onItemClick 을 추상 메서드로 만들어서 필요 시 재정의 하도록 클래스 작성
    public interface ClickListener {
        void onItemClick(View v, int adapterPosition);
    }

    // 뷰 데이터를 홀드 해두는 클래스, 리사이클러 뷰의 뷰홀더를 상속 받음
    public class TodoViewHolder extends RecyclerView.ViewHolder {
        // 리사이클러 뷰의 아이템
        private final TextView todoItemView;

        // 어댑터에서 뷰 홀더를 생성하며 각 아이템을 바인딩 및 클릭 이벤트 추가
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> {
                clickListener.onItemClick(v, getAdapterPosition());
            });
        }
    }

    // 어댑터의 클릭리스너를 재정의 하는 메서드
    public void setOnItemClickListener(ClickListener clickListener) {
        TodoListAdapter.clickListener = clickListener;
    }
}
