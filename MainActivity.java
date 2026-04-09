package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> tasks = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_task) {
            Intent intent = new Intent(getApplicationContext(), TaskEditor.class);

            startActivity(intent);

            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.task manager", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>)  sharedPreferences.getStringSet("tasks", null);

        if (set == null) {
            tasks.add("Example note");

        } else {
            tasks = new ArrayList<>(set);
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), TaskEditor.class);
            intent.putExtra("taskId", i);
            startActivity(intent);});

        listView.setOnItemLongClickListener((adapterView, view, i, l)-> {

            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this task?")
                    .setPositiveButton("Yes", ((dialog, which) -> {

                        tasks.remove(i);
                        arrayAdapter.notifyDataSetChanged();

                        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("com.example.task manager", Context.MODE_PRIVATE);

                        HashSet<String> set1 = new HashSet<>(MainActivity.tasks);

                        sharedPreferences1.edit().putString("tasks", String.valueOf(set1)).apply();
                    }))
                    .setNegativeButton("No", null);





            return true;
        });
    }
}