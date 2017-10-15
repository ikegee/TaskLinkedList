package com.example.android.tasklinkedlist;

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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Project: TaskLinkedList File: AddTaskActivity.java
 * Date: Feb. 15, 2017 Time: 10:34:43 PM
 * Author: G.E. Eidsness
 */

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddTaskActivity.class.getSimpleName();
    private static final String DIALOG_DATE = "DialogDate";

    private static TaskList taskInstance = TaskList.getInstance();

    private Button taskDate;
    private EditText taskTitle, taskDescription;
    private CheckBox taskCheckBox;
    private Spinner spCategory, spPriority;

    private static String theTitle, theDate, theDescription;
    private static String theCategory, thePriority;
    private static boolean isChecked = false;
    private static Date myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        spCategory = (Spinner) findViewById(R.id.spinner_category);
        spPriority = (Spinner) findViewById(R.id.spinner_priority);
        taskTitle = (EditText) findViewById(R.id.task_title);
        taskDescription = (EditText) findViewById(R.id.taskDescription);

        taskDate = (Button) findViewById(R.id.button_task_date);
        taskDate.setText(new Date().toString());
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DialogFragment newFragment = new TaskDatePicker();
                newFragment.show(manager, DIALOG_DATE);

            }
        });

        taskCheckBox = (CheckBox)findViewById(R.id.chkTask);
        taskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskCheckBox.isChecked()){
                    isChecked = true;
                    taskCheckBox.setText(R.string.task_checked_completed);
                } else {
                    isChecked = true;
                    taskCheckBox.setText(R.string.task_checked_pending);
                }
            }
        });

        /* Category Menu */
        ArrayAdapter<CharSequence> adapterCat = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d(TAG, "adapterCat:" + adapterCat);
        Log.d(TAG, spCategory.toString());
        spCategory.setAdapter(adapterCat);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] items = getResources().getStringArray(R.array.category_array);
                theCategory = items[position];
                //Toast.makeText(AddTaskActivity.this, "Selected : " + theCategory, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                theCategory = "Home";
            }
        });
        /* End Category Menu */

        /* Priority Menu */
        ArrayAdapter<CharSequence> adapterPriority = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapterPriority);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] items = getResources().getStringArray(R.array.priority_array);
                thePriority = items[position];
                //Toast.makeText(AddTaskActivity.this, "Selected : " + thePriority, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                thePriority = "Low";
            }
        });
        /* End Priority Menu */

        Button btnAdd = (Button) findViewById(R.id.button_add_task);
        btnAdd.setOnClickListener(AddTaskActivity.this);
    }

    // Perform Operation to Add item to Data Set.
    // notifyItemInserted(position) method on adapter
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_task:
                theTitle = taskTitle.getText().toString();
                if(theTitle.isEmpty() || theTitle.trim().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter Title", Toast.LENGTH_SHORT).show();
                    taskTitle.setText(null);
                    taskTitle.setHint("Enter Title");
                    return;
                }
                theDescription = taskDescription.getText().toString();
                if(theDescription.isEmpty() || theDescription.trim().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                theDate = taskDate.getText().toString();
                theCategory = spCategory.getSelectedItem().toString();
                thePriority = spPriority.getSelectedItem().toString();
                isChecked = taskCheckBox.isChecked();
                this.populateAndSaveDataFromSingleton();
                this.returnHome();
                break;

            default:
                this.returnHome();
                break;
        }
    }

    private void populateAndSaveDataFromSingleton() {
        Task task = new Task();
        task.setTitle(theTitle);
        task.setDescription(theDescription);
        task.setDate(formatDate(theDate));
        task.setCategory(theCategory);
        task.setPriority(thePriority);
        task.setTasked(isChecked);
        taskInstance.addTask(task);
    }

    private static Date formatDate(String strDate){
        String DATEFORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT, Locale.CANADA);
        try {
            myDate = sdf.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    private static void createCategoryMenu() {
    }

    private static void createPriorityMenu() {
    }

    public void returnHome() {
        Intent mainIntent = new Intent(getApplicationContext(), DisplayTasksActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }
}
