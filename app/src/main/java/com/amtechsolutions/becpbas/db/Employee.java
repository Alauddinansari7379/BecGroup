package com.amtechsolutions.becpbas.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Shourav Paul on 09-03-2022.
 **/
@Entity(tableName = "table_name")
public class Employee implements Serializable {
    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "empcode")
    private String empCode;
    //clockin fields
    @ColumnInfo(name = "clockingeolocname")
    private String clockinGeoName;
    @ColumnInfo(name = "clockingeolat")
    private String clockinGeoLat;
    @ColumnInfo(name = "clockingeolon")
    private String clockinGeoLon;
    @ColumnInfo(name = "clockindate")
    private String clockinGeoDate;
    @ColumnInfo(name = "clockintime")
    private String clockinGeoTime;
    @ColumnInfo(name = "clockintimezone")
    private String clockinGeoTimeZone;
    //clock out fields
    @ColumnInfo(name = "clockoutgeolocname")
    private String clockoutGeoName;
    @ColumnInfo(name = "clockoutgeolat")
    private String clockoutGeoLat;
    @ColumnInfo(name = "clockoutgeolon")
    private String clockoutGeoLon;
    @ColumnInfo(name = "clockoutdate")
    private String clockoutGeoDate;
    @ColumnInfo(name = "clockouttime")
    private String clockoutGeoTime;
    @ColumnInfo(name = "clockouttimezone")
    private String clockoutGeoTimeZone;
    @ColumnInfo(name = "imgUri")
    private String imageUri;
    @ColumnInfo(name = "imgUriclckout")
    private String imageUriClockOut;
    @ColumnInfo(name = "imgEncoded")
    private String imageEncoded;
    @ColumnInfo(name = "imgEncodedclckout")
    private String imageEncodedClockOut;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClockinGeoName() {
        return clockinGeoName;
    }

    public void setClockinGeoName(String clockinGeoName) {
        this.clockinGeoName = clockinGeoName;
    }

    public String getClockinGeoLat() {
        return clockinGeoLat;
    }

    public void setClockinGeoLat(String clockinGeoLat) {
        this.clockinGeoLat = clockinGeoLat;
    }

    public String getClockinGeoLon() {
        return clockinGeoLon;
    }

    public void setClockinGeoLon(String clockinGeoLon) {
        this.clockinGeoLon = clockinGeoLon;
    }

    public String getClockinGeoDate() {
        return clockinGeoDate;
    }

    public void setClockinGeoDate(String clockinGeoDate) {
        this.clockinGeoDate = clockinGeoDate;
    }

    public String getClockinGeoTime() {
        return clockinGeoTime;
    }

    public void setClockinGeoTime(String clockinGeoTime) {
        this.clockinGeoTime = clockinGeoTime;
    }

    public String getClockoutGeoName() {
        return clockoutGeoName;
    }

    public void setClockoutGeoName(String clockoutGeoName) {
        this.clockoutGeoName = clockoutGeoName;
    }

    public String getClockoutGeoLat() {
        return clockoutGeoLat;
    }

    public void setClockoutGeoLat(String clockoutGeoLat) {
        this.clockoutGeoLat = clockoutGeoLat;
    }

    public String getClockoutGeoLon() {
        return clockoutGeoLon;
    }

    public void setClockoutGeoLon(String clockoutGeoLon) {
        this.clockoutGeoLon = clockoutGeoLon;
    }

    public String getClockoutGeoDate() {
        return clockoutGeoDate;
    }

    public void setClockoutGeoDate(String clockoutGeoDate) {
        this.clockoutGeoDate = clockoutGeoDate;
    }

    public String getClockoutGeoTime() {
        return clockoutGeoTime;
    }

    public void setClockoutGeoTime(String clockoutGeoTime) {
        this.clockoutGeoTime = clockoutGeoTime;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageEncoded() {
        return imageEncoded;
    }

    public void setImageEncoded(String imageEncoded) {
        this.imageEncoded = imageEncoded;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getImageUriClockOut() {
        return imageUriClockOut;
    }

    public void setImageUriClockOut(String imageUriClockOut) {
        this.imageUriClockOut = imageUriClockOut;
    }

    public String getImageEncodedClockOut() {
        return imageEncodedClockOut;
    }

    public void setImageEncodedClockOut(String imageEncodedClockOut) {
        this.imageEncodedClockOut = imageEncodedClockOut;
    }

    public String getClockinGeoTimeZone() {
        return clockinGeoTimeZone;
    }

    public void setClockinGeoTimeZone(String clockinGeoTimeZone) {
        this.clockinGeoTimeZone = clockinGeoTimeZone;
    }

    public String getClockoutGeoTimeZone() {
        return clockoutGeoTimeZone;
    }

    public void setClockoutGeoTimeZone(String clockoutGeoTimeZone) {
        this.clockoutGeoTimeZone = clockoutGeoTimeZone;
    }
}
