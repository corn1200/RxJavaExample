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

    TodoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        if (mTodos != null) {
            Todo current = mTodos.get(position);
            holder.todoItemView.setText(current.getTodo());
        } else {
            holder.todoItemView.setText("No Todo");
        }
    }

    void setTodos(List<Todo> todos) {
        mTodos = todos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTodos != null) {
            return mTodos.size();
        } else {
            return 0;
        }
    }

    public Todo getTodoAtPosition(int position) {
        return mTodos.get(position);
    }

    public interface ClickListener {
        void onItemClick(View v, int adapterPosition);
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView todoItemView;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> {
                clickListener.onItemClick(v, getAdapterPosition());
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TodoListAdapter.clickListener = clickListener;
    }
}
