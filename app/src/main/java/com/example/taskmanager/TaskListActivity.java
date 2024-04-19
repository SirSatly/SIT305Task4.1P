package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;


public class TaskListActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    VerticalAdapter vAdapter;
    TaskDatabase taskDataBase;
    List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        recyclerViewMain = findViewById(R.id.recyclerView);
        taskDataBase = new TaskDatabase(this);
        createTaskList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        createTaskList();
    }

    private void createTaskList()
    {
        taskList = taskDataBase.getAllTask();
        taskList.sort(new TaskComparer());
        vAdapter = new VerticalAdapter(taskList, new onItemClickListener()
        {
            @Override
            public void itemClick(Task task) {
                Intent intent = new Intent(TaskListActivity.this, TaskShowActivity.class);
                intent.putExtra("id", task.getId());
                startActivity(intent);
            }
        });
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(vAdapter);
    }

    public void CreateTask(View view) {
        Intent intent = new Intent(TaskListActivity.this, TaskFormActivity.class);
        startActivity(intent);
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

        private List<Task> taskList;
        private final onItemClickListener listener;

        public VerticalAdapter(List<Task> taskList, onItemClickListener listener) {
            this.taskList = taskList;
            this.listener = listener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleTextView;
            private TextView dueDateTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleText);
                dueDateTextView = itemView.findViewById(R.id.dueDateText);
            }

            public void bind(Task task, final onItemClickListener listener) {
                titleTextView.setText(task.getTitle());
                dueDateTextView.setText(task.getDueDateString());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.itemClick(task);
                    }
                });
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Task task = taskList.get(position);
            holder.bind(task, listener);
        }

        @Override
        public int getItemCount() {
            if (this.taskList == null) {
                return 0;
            }
            return this.taskList.size();
        }
    }

    public interface onItemClickListener
    {
        void itemClick(Task task);
    }

    static class TaskComparer implements Comparator<Task> {
        @Override
        public int compare(Task a, Task b) {
            return a.getDueDate().compareTo(b.getDueDate());
        }
    }
}