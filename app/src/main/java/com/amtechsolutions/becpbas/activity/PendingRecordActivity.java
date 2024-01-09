package com.amtechsolutions.becpbas.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.adapter.EmployeeAdapter;
import com.amtechsolutions.becpbas.db.AppDatabase;
import com.amtechsolutions.becpbas.db.Employee;

import java.util.ArrayList;
import java.util.List;

public class PendingRecordActivity extends AppCompatActivity {

    private static final String TAG = "PendingRecordActivity";
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private List<Employee> list = new ArrayList<>();
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_record);
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#05143a"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Pending Records");
        initVar();
        initRecycler();
        getAllPendingRecords();
    }
    private void initVar()
    {
        database = AppDatabase.getInstance(this);
    }
    private void initRecycler()
    {
        recyclerView = (RecyclerView)findViewById(R.id.record_rec);
        adapter = new EmployeeAdapter(PendingRecordActivity.this, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingRecordActivity.this));
        recyclerView.setAdapter(adapter);
    }
    private void getAllPendingRecords()
    {
        if(!database.employeeDao().getAll().isEmpty())
        {
            list.addAll(database.employeeDao().getAll());
        }
        adapter.notifyDataSetChanged();
    }
}