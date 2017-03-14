package com.neogoopilhigh.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by stephane on 29/01/17.
 */

public class PopActivity extends Activity {

    DatabaseHelper myDB;
    EditText entryField;
    Button  Okbtn;
    CalendarView calendar;
    String curDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poptask);

        myDB = new DatabaseHelper(this);
        entryField = (EditText) findViewById(R.id.TaskInput);
        Okbtn = (Button) findViewById(R.id.OKbutton);
        calendar = (CalendarView) findViewById(R.id.calendartask);

        Okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEntry = entryField.getText().toString();

                if(entryField.length()!= 0){
                    AddData(newEntry);
                    Intent myIntent = new Intent(PopActivity.this, MainActivity.class);
                    PopActivity.this.startActivity(myIntent);
                }else{
                    Toast.makeText(PopActivity.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                int d = dayOfMonth;
                int m = month + 1;

                curDate = String.valueOf(d);
                curDate += "/";
                curDate += String.valueOf(m);
            }
        });
    }

    public void AddData(String newEntry) {

        boolean insertData;

        if (curDate == null) {
            insertData = myDB.addData(newEntry);
        } else {
            insertData = myDB.addData(newEntry + " " + curDate);
        }

        if(insertData == true) {
            Toast.makeText(this, "Task Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}