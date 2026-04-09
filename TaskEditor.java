package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class TaskEditor extends AppCompatActivity {

    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        EditText editText = findViewById(R.id.edit_text_id);

        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId", -1);

        if (taskId != -1){
            editText.setText(MainActivity.tasks.get(taskId));
        } else {
            MainActivity.tasks.add("");
            taskId = MainActivity.tasks.size() -1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.tasks.set(taskId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.task-manager", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(MainActivity.tasks);

                sharedPreferences.edit().putStringSet("tasks", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}