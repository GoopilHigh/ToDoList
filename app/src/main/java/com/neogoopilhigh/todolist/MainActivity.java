package com.neogoopilhigh.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button btnAdd;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        final ListView listView = (ListView) findViewById(R.id.TodoListview);
        ArrayList<String> theList = new ArrayList<>();

        Cursor data = myDB.getListContents();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,theList);
        if(data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in your To Do list!",Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()) {
                theList.add(data.getString(1));
                listView.setAdapter(adapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedFromList =(String) (listView.getItemAtPosition(myItemInt));
                RemoveData(selectedFromList);
                Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

            btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PopActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public void RemoveData(String newEntry) {

        boolean insertData = myDB.deleteData(newEntry);

        if(insertData==true){
            Toast.makeText(this, "Task Successfully Removed!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}
