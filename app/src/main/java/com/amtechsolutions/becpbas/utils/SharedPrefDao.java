package com.amtechsolutions.becpbas.utils;

import android.app.Application;

import com.amtechsolutions.becpbas.db.Employee;


public class SharedPrefDao {
    private static SharedPrefDao instance = null;

    private static Application application;
    private SharedPrefManager prefManager = SharedPrefManager.getInstance(application.getApplicationContext());

    private SharedPrefDao() {

    }

    public static SharedPrefDao getInstance(Application app) {
        application = app;

        if (instance == null) {
            synchronized (SharedPrefDao.class) {
                if (instance == null) {
                    instance = new SharedPrefDao();
                }
            }
        }
        return instance;
    }

    /**
     * Saves User's shared preference
     * @param user identifies user
     */
    public void saveUserPref(Employee user) {
        prefManager.saveUserPref(user);
    }
    public void saveImgPref(Employee user) {
        prefManager.saveImgPref(user);
    }
    public Employee getEmployeePref()
    {
        return prefManager.getEmployee();
    }

    /**
     * Removes user's  shared preference
     */
    public void removeUserPref() {
        prefManager.removeUserPref();
    }

    /**
     * Checks if user's data exists in shared Preference
     * @return True if user exists else false
     */
    public boolean isUserLoggedIn() {
        return prefManager.isUserLoggedIn();
    }

    /**
     * Provides user's id from Shared preference
     * @return user's id
     */
    public String getUserIdFromPref() {
        return prefManager.getUserId();
    }
    public String getUserTokenFromPref()
    {
        return prefManager.getToken();
    }
}
