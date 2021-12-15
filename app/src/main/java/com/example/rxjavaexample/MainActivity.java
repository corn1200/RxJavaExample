package com.example.rxjavaexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 리사이클러 뷰의 어댑터와 레이아웃 매니저를 설정
        recyclerView = findViewById(R.id.recyclerview);
        final TodoListAdapter adapter = new TodoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 뷰모델 클래스를 상속하여 정의한 클래스는 직접 생성자를 통해 인스턴스를 생성할 수 없다
        // LiveData 객체에 관찰자를 붙여서 데이터가 변경 될 때 마다 특정한 작업 수행
        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        mTodoViewModel.getAllTodos().observe(this, todos -> {
            adapter.setTodos(todos);
        });

        // 요청 전송 후 요청 결과에 대한 응답 분기 처리
        // 요청 후 작업 성공 시 응답 내용 insert
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
                                        R.string.empty_not_saved,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        // 플로팅 버튼 바인딩 후 클릭 이벤트 생성
        // 현재 액티비티에서 데이터 작성을 위한 액티비티로 이동 후 응답 받은 내용으로 작업
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewTodoActivity.class);
            insertActivityResultLauncher.launch(intent);
        });

        // 리사이클러 뷰 안에 있는 요소를 좌우로 스와이프 할 시 특정 작업 실행
        // 스와이프 한 해당 요소의 포지션과 포지션 기반으로 어댑터 속 데이터 데이터를 반환
        // 뷰모델을 통해 해당 데이터 요소를 삭제한다
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

        // 스와이프 기능을 리사이클러 뷰에 할당
        helper.attachToRecyclerView(recyclerView);

        // 요청 성공 후 회신 받은 인텐트 결과에서 데이터 내용과 id를 저장
        // 만약 id 값이 있을 경우 해당 id의 내용을 회신 받은 데이터로 전환
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

        // 어댑터의 아이템에 대한 클릭 이벤트 정의
        // 리사이클러 뷰의 요소 클릭 시 클릭한 요소의 포지션 반환
        // 현재 액티비티에서 데이터 내용을 수정 할 수 있는 액티비티로 이동
        // 포지션에 해당하는 요소의 id 와 데이터 내용을 전달
        adapter.setOnItemClickListener((v, adapterPosition) -> {
            Todo todo = adapter.getTodoAtPosition(adapterPosition);

            Intent intent = new Intent(this, NewTodoActivity.class);
            intent.putExtra(EXTRA_DATA_UPDATE_TODO, todo.getTodo());
            intent.putExtra(EXTRA_DATA_UPDATE_ID, todo.getId());
            updateActivityResultLauncher.launch(intent);
        });
    }

    // 메뉴를 할당
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 각 메뉴에 대한 이벤트 처리
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
