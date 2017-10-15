package com.example.android.tasklinkedlist;

import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Project: TaskLinkedList File: TaskList.java
 * Created by G.E. Eidsness on 2017-08-15.
 */

class TaskList {

    private static final String TAG = TaskList.class.getSimpleName();
    private List<Task> tasks  = new LinkedList<>();
    private static TaskList sTaskList = new TaskList();

    private TaskList() {
        super();
    }

    static TaskList getInstance() {
        if (sTaskList == null) {
            sTaskList = new TaskList();
        }
        return sTaskList;
    }

    List<Task> getTasks() {
        return tasks;
    }

    Task getTask(UUID id) {
        if (id != null) {
            for (Task task : tasks) {
                if (task.getId().equals(id)) {
                    return task;
                }
            }
        }
        return null;
    }

    boolean addTask(Task task) {
        boolean isAdded = false;
        if (task != null) {
            synchronized (this) {
                tasks.add(task);
                isAdded = true;
            }
        }
        Log.d(TAG, "Created: " + isAdded);
        return isAdded;
    }

    boolean deleteTaskWithId(UUID id) {
        boolean isDeleted = false;
        if (id != null) {
            for (Task task : tasks) {
                if (task.getId().equals(id)) {
                    tasks.removeAll(Collections.singleton(task));
                    isDeleted = true;
                    break;
                }
            }
        }
        Log.d(TAG, "Deleted: " + isDeleted);
        return isDeleted;
    }

    boolean updateTask(Task editTask) {
        boolean isUpdated = false;
        int j = 0;
        if (editTask != null) {
            for (Task task : tasks) {
                if (editTask.equals(task)) {
                    Log.d(TAG, "index:" + j);
                    tasks.set(j, editTask);
                    isUpdated = true;
                    break;
                }
                j++;
            }
        }
        Log.d(TAG, "Updated: " + isUpdated);
        return isUpdated;
    }

    /* unused */
    public boolean deleteTask(Task deleteTask) {
        boolean isDeleted = false;
        if (deleteTask != null) {
            for (Task task : tasks) {
                if (deleteTask.equals(task)) {
                    Log.d(TAG, "Delete Task:" + task.getId());
                    tasks.remove(deleteTask);
                    isDeleted = true;
                    break;
                }
            }
        }
        return isDeleted;
    }
}

