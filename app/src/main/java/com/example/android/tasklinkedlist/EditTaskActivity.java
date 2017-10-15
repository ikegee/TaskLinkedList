package com.example.android.tasklinkedlist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Project: TaskLinkedList File: EditTaskActivity.java
 * Date: Feb. 15, 2017 Time: 10:34:43 PM
 * Author: G.E. Eidsness
 */

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EditTaskActivity.class.getSimpleName();
    private static final String DIALOG_DATE = "DialogDate";
    private static final String EMAILADDRESS = "joeblow@gmail.ca";
    private static boolean EMAILSENT = false;

    private TaskList taskInstance = TaskList.getInstance();

    private EditText taskTitle, taskDescription;
    private Button taskDate;
    private CheckBox taskCheckBox;
    private Spinner spCategory, spPriority;

    private static UUID taskId;
    private static String theCategory, thePriority, theEmail;
    private static boolean isChecked;
    private static Date myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        //ArrayList<Task> tskList = getIntent().getParcelableExtra("tskList");
        Log.d(TAG, "hashCode: " + taskInstance.hashCode());
        spCategory = (Spinner) findViewById(R.id.spinner_category);
        spPriority = (Spinner) findViewById(R.id.spinner_priority);

        taskTitle = (EditText) findViewById(R.id.task_title);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskDate = (Button) findViewById(R.id.button_task_date);
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DialogFragment newFragment = new TaskDatePicker();
                newFragment.show(manager, DIALOG_DATE);
                //Toast.makeText(EditTaskActivity.this, "Clicked : " + DIALOG_DATE, Toast.LENGTH_SHORT).show();
            }
        });

        taskCheckBox = (CheckBox) findViewById(R.id.chkTask);
        taskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskCheckBox.isChecked()) {
                    isChecked = true;
                    taskCheckBox.setText(R.string.task_checked_completed);
                } else {
                    isChecked = false;
                    taskCheckBox.setText(R.string.task_checked_pending);
                }
                //Toast.makeText(getApplicationContext(), Boolean.toString(isChecked), Toast.LENGTH_SHORT).show();
            }
        });

        /* Category Menu */
        ArrayAdapter<CharSequence> adapterEditCat = ArrayAdapter.createFromResource(EditTaskActivity.this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapterEditCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapterEditCat);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, theCategory);
                String[] items = getResources().getStringArray(R.array.category_array);
                theCategory = items[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setSpinnerValue(spCategory, theCategory);
            }
        });
        /* End Category Menu */

        /* Priority Menu */
        ArrayAdapter<CharSequence> adapterEditPriority = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapterEditPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapterEditPriority);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, thePriority);
                String[] items = getResources().getStringArray(R.array.priority_array);
                thePriority = items[position];
            }@Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setSpinnerValue(spPriority, thePriority);
            }
        });
        /* End Priority Menu */

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            //id = getIntent().getExtras().getString("taskID");
            taskId = (UUID) getIntent().getSerializableExtra("taskID");
            String theTitle = getIntent().getExtras().getString("taskTitle");
            String theDescription = getIntent().getExtras().getString("taskDescription");
            final int position = getIntent().getExtras().getInt("taskPosition");
            theCategory = getIntent().getExtras().getString("taskCategory");
            thePriority = getIntent().getExtras().getString("taskPriority");
            String theDate = getIntent().getExtras().getString("taskDate");
            isChecked = getIntent().getExtras().getBoolean("taskStatus");
            taskTitle.setText(theTitle);
            taskDescription.setText(theDescription);
            this.setTaskStatus(taskCheckBox, isChecked);
            taskCheckBox.setChecked(isChecked);
            this.setSpinnerValue(spCategory, theCategory);
            this.setSpinnerValue(spPriority, thePriority);
            taskDate.setText(theDate);
        } else {
            returnHome();
        }

        Button btnUpdate = (Button) findViewById(R.id.button_update);
        Button btnDelete = (Button) findViewById(R.id.button_delete);
        Button btnEmail = (Button) findViewById(R.id.button_email);

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnEmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                // notifyItemRemoved(position) method on adapter
                //dbManager.update(_id, title, desc);
                this.populateAndSaveDataFromSingleton(taskId);
                //Toast.makeText(EditTaskActivity.this, "Clicked : " + taskInstance.hashCode(), Toast.LENGTH_SHORT).show();
                this.returnHome();
                break;
            case R.id.button_delete:
                // notifyItem(position) method on adapter
                //dbManager.delete(_id);
                this.taskInstance.deleteTaskWithId(taskId);
                this.returnHome();
                break;
            case R.id.button_email:
                this.emailTaskInfo();
                break;
        }
    }

    private void setTaskStatus(CheckBox taskCheckBox, Boolean var){
        if (var){
            taskCheckBox.setText(R.string.task_checked_completed);
        }else
            taskCheckBox.setText(R.string.task_checked_pending);
    }

    private void setSpinnerValue(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void emailTaskInfo() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAILADDRESS});
        email.putExtra(Intent.EXTRA_SUBJECT, String.valueOf(taskTitle.getText()));
        email.putExtra(Intent.EXTRA_TEXT, String.valueOf(taskDescription.getText()));
        email.setType("message/rfc822");
        try {
            this.startActivity(Intent.createChooser(email, "Choose an Email client :"));
            EMAILSENT = true;
            finish();
            Log.d(TAG, "Message Sent");
        } catch (ActivityNotFoundException noSuchActivity) {
            noSuchActivity.getMessage();
        }
    }

    private void populateAndSaveDataFromSingleton(UUID id) {
        Task task = taskInstance.getTask(id);
        task.setTitle(String.valueOf(taskTitle.getText()));
        task.setDescription(String.valueOf(taskDescription.getText()));
        task.setDate(formatDate(String.valueOf(taskDate.getText())));
        task.setCategory(theCategory);
        task.setPriority(thePriority);
        task.setTasked(isChecked);
        taskInstance.updateTask(task);
    }

    private static Date formatDate(String strDate) {
        String DATEFORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT, Locale.CANADA);
        try {
            myDate = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    public void returnHome() {
        Intent mainIntent = new Intent(getApplicationContext(), DisplayTasksActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }
}