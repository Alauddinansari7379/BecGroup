package com.amtechsolutions.becpbas.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by Shourav Paul on 09-03-2022.
 **/
@Dao
public interface EmpDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(Employee employee);
    //Delete query
    @Delete
    void delete(Employee employee);

    //Delete all query
    @Delete
    void deleteAll(List<Employee> employeeList);

    //Update query
//    @Query("UPDATE table_name SET clockingeolocname = :vclockingeolocname, clockingeolat = :vclockingeolat, clockingeolon = :vclockingeolon, " +
//            "clockindate = :vclockindate, clockintime = :vclockintime, clockoutgeolocname = :vclockoutgeolocname, clockoutgeolat = :vclockoutgeolat, clockoutgeolon = :vclockoutgeolon, " +
//            "clockoutdate = :vclockoutdate, clockouttime = :vclockouttime WHERE id = :vid")
//    void update(String vclockingeolocname, String vclockingeolat, String vclockingeolon, String vclockindate, String vclockintime, String vclockoutgeolocname, String vclockoutgeolat, String vclockoutgeolon, String vclockoutdate, String vclockouttime, String vid);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<Employee> getAll();

}
