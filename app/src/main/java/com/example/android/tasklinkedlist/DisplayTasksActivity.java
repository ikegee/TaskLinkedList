package com.example.android.tasklinkedlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Project: TaskLinkedList File: DisplayTasksActivity.java
 * Created by G.E. Eidsness on 2017-08-15.
 */

public class DisplayTasksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = DisplayTasksActivity.class.getSimpleName();

    private List<Task> mTasks;
    private TaskList taskList = TaskList.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tasks);
        TextView taskText = (TextView) findViewById(R.id.taskListTitle);
        ListView lvTasks = (ListView) findViewById(R.id.taskListView);

        mTasks = taskList.getTasks();

        if (mTasks.isEmpty() || mTasks == null) {
            taskText.setText(R.string.taskListEmpty);
            taskText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                    startActivity(intent);
                }
            });
        }

        Log.d(TAG, "getTasks()" + mTasks.toString());

        TaskListAdapter taskListAdapter = new TaskListAdapter(this, mTasks);
        lvTasks.setAdapter(taskListAdapter);

        lvTasks.setTextFilterEnabled(true);
        lvTasks.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent editIntent = new Intent(getApplicationContext(), EditTaskActivity.class);
        editIntent.putExtra("taskID", mTasks.get(position).getId());
        editIntent.putExtra("taskTitle", mTasks.get(position).getTitle());
        editIntent.putExtra("taskDescription", mTasks.get(position).getDescription());
        editIntent.putExtra("taskDate", mTasks.get(position).getDate().toString());
        editIntent.putExtra("taskStatus", mTasks.get(position).isTasked());
        editIntent.putExtra("taskCategory", mTasks.get(position).getCategory());
        editIntent.putExtra("taskPriority", mTasks.get(position).getPriority());
        editIntent.putExtra("taskPosition", position);
        startActivity(editIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
