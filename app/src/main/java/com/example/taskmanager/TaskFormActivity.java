package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskFormActivity extends AppCompatActivity {

    TaskDatabase taskDatabase;
    Task task;

    EditText titleText;
    EditText descriptionText;
    EditText dueDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_form);

        taskDatabase = new TaskDatabase(this);

        titleText = findViewById(R.id.titleEdit);
        descriptionText = findViewById(R.id.descriptionEdit);
        dueDateText = findViewById(R.id.dueDateEdit);
        TextView formTitle = findViewById(R.id.formTitle);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            formTitle.setText("Edit Task");

            String task_id = getIntent().getStringExtra("id");
            task = taskDatabase.getTask(task_id);

            titleText.setText(task.getTitle());
            descriptionText.setText(task.getDescription());
            dueDateText.setText(task.getDueDateString());
        }
    }

    public void Submit(View view) {
        try {
            String title = titleText.getText().toString();
            if (title.isEmpty()) {
                Toast.makeText(this, "Task requires a title", Toast.LENGTH_LONG).show();
            }

            String description = descriptionText.getText().toString();
            if (description.isEmpty()) {
                Toast.makeText(this, "Task requires a description", Toast.LENGTH_LONG).show();
            }

            Date dueDate = Task.DATE_FORMAT.parse(dueDateText.getText().toString());

            if (task == null) {
                task = new Task(title, description, dueDate);
                taskDatabase.addTask(task);
            }
            else {
                task.setTitle(title);
                task.setDescription(description);
                task.setDueDate(dueDate);

                taskDatabase.updateTask(task);
            }

            this.finish();
        } catch (ParseException e) {
            Toast.makeText(this, "Date is not of valid format", Toast.LENGTH_LONG).show();
        }
    }

    public void Back(View view){
        this.finish();
    }
}