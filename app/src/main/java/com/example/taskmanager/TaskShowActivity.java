package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TaskShowActivity extends AppCompatActivity {

    TaskDatabase taskDatabase;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_show);

        taskDatabase = new TaskDatabase(this);

        String task_id = getIntent().getStringExtra("id");

        task = taskDatabase.getTask(task_id);

        displayTask();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        task = taskDatabase.getTask(task.getId());
        displayTask();
    }

    public void displayTask() {
        TextView titleText = findViewById(R.id.titleText);
        titleText.setText(task.getTitle());

        TextView descriptionText = findViewById(R.id.descriptionText);
        descriptionText.setText(task.getDescription());

        TextView dueDateText = findViewById(R.id.dueDateText);
        dueDateText.setText("Due Date: " + task.getDueDateString());
    }

    public void UpdateTask(View view) {
        Intent intent = new Intent(this, TaskFormActivity.class);
        intent.putExtra("id", task.getId());
        startActivity(intent);
    }

    public void DeleteTask(View view) {
        taskDatabase.deleteTask(task.getId());
        this.finish();
    }

    public void Back(View view){
        this.finish();
    }
}