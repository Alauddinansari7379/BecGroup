package com.amtechsolutions.becpbas.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Shourav Paul on 09-03-2022.
 **/
@Database(entities = {Employee.class, Supervisor.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //Create Database instance
    private static AppDatabase database;
    //Define Database name
    private static String DATABASE_NAME = "database";

    public synchronized static AppDatabase getInstance(Context context)
    {
        if(database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //Create Employee Dao
    public abstract EmpDao employeeDao();
    //Create Supervisor Dao
    public abstract SupervisorDao superVisorDao();

}
