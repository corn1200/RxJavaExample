package com.example.rxjavaexample;

import static com.example.rxjavaexample.MainActivity.EXTRA_DATA_UPDATE_ID;
import static com.example.rxjavaexample.MainActivity.EXTRA_DATA_UPDATE_TODO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class NewTodoActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.rxjavaexample.REPLY";
    public static final String EXTRA_REPLY_ID = "com.example.rxjavaexample.REPLY_ID";
    private EditText mEditTodoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTodoView = findViewById(R.id.edit_todo);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String todo = extras.getString(EXTRA_DATA_UPDATE_TODO, "");
            if (!todo.isEmpty()) {
                mEditTodoView.setText(todo);
                mEditTodoView.setSelection(todo.length());
                mEditTodoView.requestFocus();
            }
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTodoView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = mEditTodoView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                if (extras != null && extras.containsKey(EXTRA_DATA_UPDATE_ID)) {
                    int id = extras.getInt(EXTRA_DATA_UPDATE_ID, -1);
                    if (id != -1) {
                        replyIntent.putExtra(EXTRA_REPLY_ID, id);
                    }
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}