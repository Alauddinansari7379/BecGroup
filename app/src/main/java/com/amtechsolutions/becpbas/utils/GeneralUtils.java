package com.amtechsolutions.becpbas.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Shourav Paul on 17-03-2022.
 **/
public class GeneralUtils {

    public String getMonthInMMM(String dateStr)
    {
        String[] arr = dateStr.split("-");
        String newMonthFormat = null;
        switch (arr[1])
        {
            case "1" : newMonthFormat = "Jan";
            break;
            case "2" : newMonthFormat = "Feb";
            break;
            case "3" : newMonthFormat = "Mar";
            break;
            case "4" : newMonthFormat = "Apr";
            break;
            case "5" : newMonthFormat = "May";
            break;
            case "6" : newMonthFormat = "Jun";
            break;
            case "7" : newMonthFormat = "Jul";
            break;
            case "8" : newMonthFormat = "Aug";
            break;
            case "9" : newMonthFormat = "Sep";
            break;
            case "10" : newMonthFormat = "Oct";
            break;
            case "11" : newMonthFormat = "Nov";
            break;
            case "12" : newMonthFormat = "Dec";

        }
        return arr[0]+"-"+newMonthFormat+"-"+arr[2];
    }
    public String getTimeInHHMMFormat(String timeStr)
    {
        String[] arr = timeStr.split(":");
        String newHrStr = arr[0];
        String newMinStr = arr[1];
        if(newHrStr.length() == 1)
        {
            newHrStr = "0"+newHrStr;
        }
        if(newMinStr.length() == 1)
        {
            newMinStr = "0"+newMinStr;
        }
        return newHrStr+":"+newMinStr;
    }
    public String getTimeInHHMMSSFormat(String timeStr)
    {
        String[] arr = timeStr.split(":");
        String newHrStr = arr[0];
        String newMinStr = arr[1];
        String newSecStr = arr[2];
        if(newHrStr.length() == 1)
        {
            newHrStr = "0"+newHrStr;
        }
        if(newMinStr.length() == 1)
        {
            newMinStr = "0"+newMinStr;
        }
        if(newSecStr.length() == 1)
        {
            newSecStr = "0"+newSecStr;
        }
        return newHrStr+":"+newMinStr+":"+newSecStr;
    }
    public String getCurrentTimeZone()
    {
        //<TIMEZONE>
        Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar1.getTime();

        DateFormat date1 = new SimpleDateFormat("ZZZZZ",Locale.getDefault());
        String timeZoneStr = date1.format(currentLocalTime);
        return timeZoneStr;
        //</TIMEZONE>
    }
    public static double getDistance(double lat1, double lon1, double lat2, double lon2)
    {
        //convert degree to radian
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        //Haversine formula to calculate the shortest distance between two points on a sphere
        double diffLat = lat2 - lat1;
        double diffLon = lon2 - lon1;

        double a = Math.pow(Math.sin(diffLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(diffLon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        //Radius of earth in kilometers
        double r = 6371;
        //calculate final result
        return (c * r);


    }
}
