package com.myproject;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserListViewActivity extends AppCompatActivity {

    ListView listView;
    UserListAdapter adapter;
    DatabaseHelper databaseHelper;
GridView gridView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userlistview_layout);
        listView = findViewById(R.id.listview);
        gridView = findViewById(R.id.gridview);
        databaseHelper = new DatabaseHelper(this);
        adapter = new UserListAdapter(this, databaseHelper.getUsers());
        listView.setAdapter(adapter);
        gridView.setAdapter(adapter);

    }
}
