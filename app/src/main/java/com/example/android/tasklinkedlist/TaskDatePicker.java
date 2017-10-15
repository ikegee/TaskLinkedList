package com.example.android.tasklinkedlist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Project: TaskLinkedList File: TaskDatePicker.java
 * Created by G.E. Eidsness on 2017-08-15.
 */

public class TaskDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public TaskDatePicker(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), TaskDatePicker.this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Button taskDate = (Button) getActivity().findViewById(R.id.button_task_date);
        Date date = new GregorianCalendar(year, month, day).getTime();
        taskDate.setText(date.toString());
        sendResult(Activity.RESULT_OK, date);
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("taskDate", date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}

