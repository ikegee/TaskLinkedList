package com.example.android.tasklinkedlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.android.tasklinkedlist.R.id.task_list_item_descriptionTextView;
import static com.example.android.tasklinkedlist.R.id.task_list_item_titleTextView;

/**
 * Project: TaskLinkedList File: TaskListAdapter.java
 * Created by G.E. Eidsness on 2017-08-15.
 */

class TaskListAdapter extends ArrayAdapter<Task> {

    private static final String TAG = TaskListAdapter.class.getSimpleName();
    private final Context context;
    private List<Task> mTasks;

    TaskListAdapter(Context ctx, List<Task> tasks) {
        super(ctx, R.layout.list_task_items, tasks);
        context = ctx;
        mTasks = tasks;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View rowView = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_task_items, parent, false);
        } else
            rowView = convertView;

        Task task = mTasks.get(position);

        TextView titleTextView = (TextView) rowView.findViewById(task_list_item_titleTextView);
        titleTextView.setText(task.getTitle());
        TextView descriptionTextView = (TextView) rowView.findViewById(task_list_item_descriptionTextView);
        descriptionTextView.setText(task.getDescription());
        TextView dateTextView = (TextView) rowView.findViewById(R.id.task_list_item_dateTextView);
        dateTextView.setText(task.getDate().toString());
        TextView categoryTextView = (TextView) rowView.findViewById(R.id.task_list_item_categoryTextView);
        categoryTextView.setText(String.format("Category: %s", task.getCategory()));
        TextView priorityTextView = (TextView) rowView.findViewById(R.id.task_list_item_priorityTextView);
        priorityTextView.setText(String.format("Priority: %s", task.getPriority()));
        CheckBox statusCheckBox = (CheckBox) rowView.findViewById(R.id.task_list_item_taskCheckBox);
        statusCheckBox.setChecked(task.isTasked());
        ImageView icon = (ImageView) rowView.findViewById(R.id.task_list_item_iconView);
        setPriorityIcon(task, icon);
        return rowView;
    }

    private void setPriorityIcon(Task task, ImageView icon) {
        switch (task.getPriority()) {
            case "Low":
                icon.setImageResource(R.drawable.ic_error_black_24dp);
                break;
            case "Medium":
                icon.setImageResource(R.drawable.ic_error_outline_black_24dp);
                break;
            case "High":
                icon.setImageResource(R.drawable.ic_warning_black_24dp);
                break;
            default:
                Log.d(TAG, "default");
                icon.setImageResource(R.drawable.ic_priority_high);
        }
        //Log.d(TAG, task.getPriority());
    }
}

