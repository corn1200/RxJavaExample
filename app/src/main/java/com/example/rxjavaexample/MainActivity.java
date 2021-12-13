package com.example.rxjavaexample;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    FloatingActionButton fab;
    private TodoViewModel mTodoViewModel;
    public static final String EXTRA_DATA_UPDATE_TODO = "extra_todo_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_ID = "extra_data_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        final TodoListAdapter adapter = new TodoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        mTodoViewModel.getAllTodos().observe(this, todos -> {
            adapter.setTodos(todos);
        });

        fab = findViewById(R.id.fab);

        ActivityResultLauncher<Intent> insertActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK) {
                                Todo todo = new Todo(result.getData()
                                        .getStringExtra(NewTodoActivity.EXTRA_REPLY));
                                mTodoViewModel.insert(todo);
                            } else {
                                Toast.makeText(this,
                                        R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
            insertActivityResultLauncher.launch(intent);
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Todo myTodo = adapter.getTodoAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myTodo.getTodo(), Toast.LENGTH_LONG).show();

                        mTodoViewModel.delete(myTodo);
                    }
                }
        );

        helper.attachToRecyclerView(recyclerView);

        ActivityResultLauncher<Intent> updateActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent data = result.getData();
                                String todo = data.getStringExtra(NewTodoActivity.EXTRA_REPLY);
                                int id = data.getIntExtra
                                        (NewTodoActivity.EXTRA_REPLY_ID, -1);

                                if (id != -1) {
                                    mTodoViewModel.update(new Todo(id, todo));
                                } else {
                                    Toast.makeText(this, "can not update",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(this,
                                        R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        adapter.setOnItemClickListener((v, adapterPosition) -> {
            Todo todo = adapter.getTodoAtPosition(adapterPosition);

            Intent intent = new Intent(this, NewTodoActivity.class);
            intent.putExtra(EXTRA_DATA_UPDATE_TODO, todo.getTodo());
            intent.putExtra(EXTRA_DATA_UPDATE_ID, todo.getId());
            updateActivityResultLauncher.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.clear_data:
                Toast.makeText(this, "Clearing the data...", Toast.LENGTH_LONG).show();
                mTodoViewModel.deleteAll();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
