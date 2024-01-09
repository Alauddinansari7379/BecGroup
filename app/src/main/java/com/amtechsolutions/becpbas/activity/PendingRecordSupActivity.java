package com.amtechsolutions.becpbas.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.adapter.SupervisorAdapter;
import com.amtechsolutions.becpbas.db.AppDatabase;
import com.amtechsolutions.becpbas.db.Supervisor;

import java.util.ArrayList;
import java.util.List;

public class PendingRecordSupActivity extends AppCompatActivity {

    private static final String TAG = "PendingRecordSupActivit";
    private RecyclerView recyclerView;
    private SupervisorAdapter adapter;
    private List<Supervisor> list = new ArrayList<>();
    private AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_record_sup);
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
        adapter = new SupervisorAdapter(PendingRecordSupActivity.this, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingRecordSupActivity.this));
        recyclerView.setAdapter(adapter);
    }
    private void getAllPendingRecords()
    {
        if(!database.superVisorDao().getAll().isEmpty())
        {
            list.addAll(database.superVisorDao().getAll());
        }
        adapter.notifyDataSetChanged();
    }
}