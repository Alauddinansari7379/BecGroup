package com.amtechsolutions.becpbas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amtechsolutions.becpbas.db.Employee;


public class SharedPrefManager {
    //The constants
    private static final String TAG = SharedPrefManager.class.getSimpleName();
    private static final String USER_PREF_NAME = "TB_USER_PREF";

    private static final String KEY_USER_ID = "USER_ID";
    private static final String KEY_USER_TOKEN = "TOKEN";
    private static final String KEY_CLOCKIN_LOC = "CLOCKIN_LOC";
    private static final String KEY_CLOCKIN_LAT = "CLOCKIN_LAT";
    private static final String KEY_CLOCKIN_LON = "CLOCKIN_LON";
    private static final String KEY_CLOCKIN_DATE = "CLOCKIN_DATE";
    private static final String KEY_CLOCKIN_TIME = "CLOCKIN_TIME";
    private static final String KEY_IMG_URI = "IMG_URI";
    private static final String KEY_ENCODED_IMG = "ENCODED_IMG";

    private static SharedPrefManager mInstance;
    private Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // If the user checks Keep me logged in checkbox
    //this method will store the user data in shared preferences
    public void saveUserPref(Employee employee) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CLOCKIN_LOC, employee.getClockinGeoName());
        editor.putString(KEY_CLOCKIN_LAT, employee.getClockinGeoLat());
        editor.putString(KEY_CLOCKIN_LON, employee.getClockinGeoLon());
        editor.putString(KEY_CLOCKIN_DATE, employee.getClockinGeoDate());
        editor.putString(KEY_CLOCKIN_TIME, employee.getClockinGeoTime());
        //editor.putInt(KEY_USER_ID, employee.getId());
        editor.apply();
        Log.i(TAG + "saveUserPref", "User data saved in SharedPreference");
    }
    public void saveImgPref(Employee employee) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMG_URI, employee.getImageUri());
        editor.putString(KEY_ENCODED_IMG, employee.getImageEncoded());
        //editor.putInt(KEY_USER_ID, employee.getId());
        editor.apply();
        Log.i(TAG + "saveUserPref", "User data saved in SharedPreference");
    }

    // If the user uncheck Keep me logged in checkbox
    //this method will remove the user data in shared preferences
    public void removeUserPref() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Log.i(TAG + "removeUserPref", "User data removed from SharedPreference");
    }

    //this method will check whether user is already logged in or not
    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, "empty") != "empty";
    }

    //this method will give the logged in user's id
    public String getUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, "empty");
    }
    //this method will return the saved employee record
    public Employee getEmployee() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        Employee employee = new Employee();
        employee.setClockinGeoName(sharedPreferences.getString(KEY_CLOCKIN_LOC, null));
        employee.setClockinGeoLat(sharedPreferences.getString(KEY_CLOCKIN_LAT, null));
        employee.setClockinGeoLon(sharedPreferences.getString(KEY_CLOCKIN_LON, null));
        employee.setClockinGeoDate(sharedPreferences.getString(KEY_CLOCKIN_DATE, null));
        employee.setClockinGeoTime(sharedPreferences.getString(KEY_CLOCKIN_TIME, null));
        employee.setImageUri(sharedPreferences.getString(KEY_IMG_URI, null));
        employee.setImageEncoded(sharedPreferences.getString(KEY_ENCODED_IMG, null));
        return employee;
    }
    //this method will return the saved employee record
    public String getToken()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME
                , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TOKEN, "empty");
    }

}
