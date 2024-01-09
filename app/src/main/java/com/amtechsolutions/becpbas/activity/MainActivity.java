package com.amtechsolutions.becpbas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.db.AppDatabase;
import com.amtechsolutions.becpbas.db.Employee;
import com.amtechsolutions.becpbas.models.LocationModel;
import com.amtechsolutions.becpbas.utils.GeneralInternet;
import com.amtechsolutions.becpbas.utils.GeneralUtils;
import com.amtechsolutions.becpbas.utils.SharedPrefDao;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.amtechsolutions.becpbas.utils.AppConstants.capPhotoMand;
import static com.amtechsolutions.becpbas.utils.AppConstants.captureMode;
import static com.amtechsolutions.becpbas.utils.AppConstants.companyKey;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentEmpCode;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentUserKey;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentUsername;
import static com.amtechsolutions.becpbas.utils.AppConstants.dataSyncUrl;
import static com.amtechsolutions.becpbas.utils.AppConstants.geoArrStr;
import static com.amtechsolutions.becpbas.utils.AppConstants.geoFenceReq;
import static com.amtechsolutions.becpbas.utils.AppConstants.saveRecBySelfUrl;
import static com.amtechsolutions.becpbas.utils.AppConstants.token;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppDatabase database;
    private AppCompatButton clockInBtn, clockOutBtn, updateBtn;
    private TextView empCodeTxt, usernameTxt;
    private boolean isPermissionGranted;
    private AlertDialog permissionDialog;
    private LocationManager locationManager;
    private FusedLocationProviderClient mLocation;
    private TextView checkDisplayTxt, checkDateTxt, checkTimeTxt, checkinLocation;
    private String imgStr;
    private CircleImageView proPicImg;
    private TextView captureBtn;
    private final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private String encodedImageString;
    private ImageView dispImg;
    //newlocation request vars
    private LocationRequest locationRequest;
    public static final int REQUEST_CHECK_SETTING = 1001;
    //
    private ProgressDialog progressDialog;
    private List<Employee> list = new ArrayList<>();
    private Dialog dialog;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        actionBar.setTitle("DashBoard");
        mLocation = new FusedLocationProviderClient(MainActivity.this);
        initView();
        setListener();
        initVar();
        initLogoutDialog();
        checkPermissions();
        if (GeneralInternet.checkInternet(this)) {
            getAllPendingRecords();
        }
        empCodeTxt.setText("Employee Code : " + currentEmpCode);
        usernameTxt.setText("Username : " + currentUsername);
        setCurrentDateAndTime();
        startClock();

        //
    }

    private void setCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        checkDateTxt.setText("Date : " + dateStr);
        checkTimeTxt.setText("Time : " + timeStr);
    }

    private void startClock() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String timeStr = new GeneralUtils().getTimeInHHMMSSFormat(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
                checkTimeTxt.setText("Time : " + timeStr);
                startClock();
            }
        }, 1000);
    }

    private void initView() {
        checkDisplayTxt = (TextView) findViewById(R.id.check_display_txt);
        checkDateTxt = (TextView) findViewById(R.id.geo_loc_date);
        checkTimeTxt = (TextView) findViewById(R.id.geo_loc_time);
        checkinLocation = (TextView) findViewById(R.id.geo_loc_txt);
        clockInBtn = (AppCompatButton) findViewById(R.id.clockin_btn);
        clockOutBtn = (AppCompatButton) findViewById(R.id.clockout_btn);
        updateBtn = (AppCompatButton) findViewById(R.id.update_btn);
        captureBtn = (TextView) findViewById(R.id.capture_btn);
        dispImg = (ImageView) findViewById(R.id.display_img);
        empCodeTxt = (TextView) findViewById(R.id.emp_code_txt);
        usernameTxt = (TextView) findViewById(R.id.username_txt);
    }

    private void setListener() {
        clockInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capPhotoMand.equals("Y")) {
                    if (encodedImageString == null) {
                        Toast.makeText(MainActivity.this, "Capture Image of Clock In!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isPermissionGranted) {
                            initLocationRequest(true);
//                    getCurrentLocation(true);
                        }
                    }
                } else {
                    if (isPermissionGranted) {
                        if (encodedImageString == null || encodedImageString.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
                            encodedImageString = "";
                            return;
                        }
                        initLocationRequest(true);
//                    getCurrentLocation(true);
                    }
                }
            }
        });
        clockOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capPhotoMand.equals("Y")) {
                    if (encodedImageString == null || encodedImageString.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
                        encodedImageString = "";
                    } else {
                        if (isPermissionGranted) {
                            initLocationRequest(false);
//                    getCurrentLocation(false);
                        }
                    }
                } else {
                    if (isPermissionGranted) {
                        if (encodedImageString == null || encodedImageString.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
                            encodedImageString = "";
                            return;
                        }
                        initLocationRequest(false);
//                    getCurrentLocation(true);
                    }
                }
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PendingRecordActivity.class));
            }
        });
        dispImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void initLogoutDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bground));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        TextView okBtn = dialog.findViewById(R.id.ok_btn);
        TextView canBtn = dialog.findViewById(R.id.can_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("BecGroupApp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                //String rememberMe = pref.getString("remember", "noValue");
                editor.clear();
//                if(rememberMe.equals("YES"))
//                {
//                    editor.putString("login", "0");
//                }
//                else if(rememberMe.equals("NO"))
//                {
//                    editor.clear();
//                }
                editor.apply();
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
                finish();

            }
        });
        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            Uri filepath = data.getData();
//            String imageStr = filepath.toString();
            //Get captured image
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            //Set captured image to imageview
            dispImg.setImageBitmap(capturedImage);
            encodeBitmapImage(capturedImage);
            //
//            Employee employee = new Employee();
//            employee.setImageUri("empty");
//            employee.setImageEncoded(encodedImageString);
//            SharedPrefDao.getInstance(getApplication()).saveImgPref(employee);
        } else if (requestCode == REQUEST_CHECK_SETTING) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "GPS is turned", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS is required for the app to work!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodedImageString = Base64.encodeToString(bytesOfImage, Base64.DEFAULT);//image finally encoded to string
    }

    private void initVar() {
        database = AppDatabase.getInstance(this);
    }

    private void storeRecord(LocationModel model, boolean isClockin) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        checkDateTxt.setText("Date : " + dateStr);
        checkTimeTxt.setText("Time : " + timeStr);
        Employee employee = new Employee();
        if (isClockin) {
            if (encodedImageString == null) {
                Toast.makeText(MainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
                return;
            } else if (geoFenceReq.equals("Y")) {
                Log.i(TAG, "directClockinAction: geoArrStr = " + geoArrStr);
                //check if distance between the given coordinates and the current geocodes is greater than the radius
                //if yes return and show not inside the geofence
                boolean inRange = false;
                String[] geoArr = geoArrStr.split("-");
                Log.i(TAG, "directClockinAction: geoArr length : " + geoArr.length);
                for (int i = 0; i < geoArr.length; i++) {
                    String geoObjStr = geoArr[i];
                    Log.i(TAG, "directClockinAction: geoObjStr" + geoObjStr);
                    String[] geoItemArr = geoObjStr.split("\\|");
                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[0]);
                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[1]);
                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[2]);

                    double desLat = Double.parseDouble(geoItemArr[0]);
                    double desLon = Double.parseDouble(geoItemArr[1]);
                    double rad = Double.parseDouble(geoItemArr[2]);
                    double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
                    double disInMetre = mDistance * 1000;
                    Log.i(TAG, "directClockinAction: disInMetre -> " + disInMetre);
                    if (disInMetre <= rad) {
                        inRange = true;
                        break;
                    }
                }
                if (!inRange) {
                    Toast.makeText(MainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            checkDisplayTxt.setText("CLOCK IN TIME INFORMATION");
            employee.setEmpCode(currentEmpCode);
            employee.setClockinGeoName(model.getGeoLocationName());
            employee.setClockinGeoLat(String.valueOf(model.getGeoLat()));
            employee.setClockinGeoLon(String.valueOf(model.getGeoLon()));
            employee.setClockinGeoDate(dateStr);
            employee.setClockinGeoTime(timeStr);
            employee.setClockinGeoTimeZone(new GeneralUtils().getCurrentTimeZone());
            employee.setImageUri("null");
            employee.setImageEncoded(encodedImageString);
            employee.setClockoutGeoName("null");
            employee.setClockoutGeoLat("null");
            employee.setClockoutGeoLon("null");
            employee.setClockoutGeoDate("null");
            employee.setClockoutGeoTime("null");
            employee.setClockoutGeoTimeZone("null");
            employee.setImageUriClockOut("null");
            employee.setImageEncodedClockOut("null");
            database.employeeDao().insert(employee);
            Toast.makeText(this, "Clocked in Successfully!", Toast.LENGTH_SHORT).show();
            encodedImageString = null;
            dispImg.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.snapshot));
        } else {
            if (encodedImageString == null) {
                Toast.makeText(MainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
                return;
            } else if (geoFenceReq.equals("Y")) {
                Log.i(TAG, "directClockoutAction: geoArrStr = " + geoArrStr);
                //check if distance between the given coordinates and the current geocodes is greater than the radius
                //if yes return and show not inside the geofence
                boolean inRange = false;
                String[] geoArr = geoArrStr.split("-");
                Log.i(TAG, "directClockoutAction: geoArr length : " + geoArr.length);
                for (int i = 0; i < geoArr.length; i++) {
                    String geoObjStr = geoArr[i];
                    Log.i(TAG, "directClockoutAction: geoObjStr" + geoObjStr);
                    String[] geoItemArr = geoObjStr.split("\\|");
                    Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[0]);
                    Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[1]);
                    Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[2]);

                    double desLat = Double.parseDouble(geoItemArr[0]);
                    double desLon = Double.parseDouble(geoItemArr[1]);
                    double rad = Double.parseDouble(geoItemArr[2]);
                    double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
                    double disInMetre = mDistance * 1000;
                    Log.i(TAG, "directClockoutAction: disInMetre -> " + disInMetre);
                    if (disInMetre <= rad) {
                        inRange = true;
                        break;
                    }
                }
                if (!inRange) {
                    Toast.makeText(MainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            checkDisplayTxt.setText("CLOCK OUT TIME INFORMATION");
            employee.setEmpCode(currentEmpCode);
            employee.setClockinGeoName("null");
            employee.setClockinGeoLat("null");
            employee.setClockinGeoLon("null");
            employee.setClockinGeoDate("null");
            employee.setClockinGeoTime("null");
            employee.setClockinGeoTimeZone("null");
            employee.setImageUri("null");
            employee.setImageEncoded("null");
            employee.setClockoutGeoName(model.getGeoLocationName());
            employee.setClockoutGeoLat(String.valueOf(model.getGeoLat()));
            employee.setClockoutGeoLon(String.valueOf(model.getGeoLon()));
            employee.setClockoutGeoDate(dateStr);
            employee.setClockoutGeoTime(timeStr);
            employee.setClockoutGeoTimeZone(new GeneralUtils().getCurrentTimeZone());
            employee.setImageUriClockOut("null");
            employee.setImageEncodedClockOut(encodedImageString);
            database.employeeDao().insert(employee);
            Toast.makeText(this, "Clocked out Successfully!", Toast.LENGTH_SHORT).show();
            encodedImageString = null;
            dispImg.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.snapshot));
//            startActivity(new Intent(SupervisorMainActivity.this, PendingRecordActivity.class));
        }
        //SharedPrefDao.getInstance(getApplication()).removeUserPref();

    }

    //    private void storeRecord(Employee emp, LocationModel model) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String dateStr = date.getDay() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900);
//        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//        Employee employee = new Employee();
//        employee.setClockinGeoName(emp.getClockinGeoName());
//        employee.setClockinGeoLat(emp.getClockinGeoLat());
//        employee.setClockinGeoLon(emp.getClockinGeoLon());
//        employee.setClockinGeoDate(emp.getClockinGeoDate());
//        employee.setClockinGeoTime(emp.getClockinGeoTime());
//        employee.setClockoutGeoName(model.getGeoLocationName());
//        employee.setClockoutGeoLat(String.valueOf(model.getGeoLat()));
//        employee.setClockoutGeoLon(String.valueOf(model.getGeoLon()));
//        employee.setClockoutGeoDate(dateStr);
//        employee.setClockoutGeoTime(timeStr);
//        employee.setImageUri(emp.getImageUri());
//        employee.setImageEncoded(emp.getImageEncoded());
//        database.employeeDao().insert(employee);
//        SharedPrefDao.getInstance(getApplication()).removeUserPref();
//        Toast.makeText(this, "Clocked out Successfully!", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(MainActivity.this, PendingRecordActivity.class));
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEmpInPref(LocationModel model) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStr = date.getDay() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        Employee employee = new Employee();
        employee.setClockinGeoName(model.getGeoLocationName());
        employee.setClockinGeoLat(String.valueOf(model.getGeoLat()));
        employee.setClockinGeoLon(String.valueOf(model.getGeoLon()));
        employee.setClockinGeoDate(dateStr);
        employee.setClockinGeoTime(timeStr);
        SharedPrefDao.getInstance(getApplication()).saveUserPref(employee);
    }

    private void showClockInDetails(Employee employee) {
        checkDisplayTxt.setText("Clock IN Time Information");
        checkinLocation.setText(employee.getClockinGeoName());
        checkDateTxt.setText("Date : " + employee.getClockinGeoDate());
        checkTimeTxt.setText("Clock In Time : " + employee.getClockinGeoTime());
    }

    public void checkPermissions() {
        Dexter.withContext(getApplicationContext())
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            isPermissionGranted = true;
                            if (isPermissionGranted) {
                                initLocationRequestForDisplay();
                            }

                            //Toast.makeText(MainActivity.this, "All permissions granted!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                showGoToAppSettingsDialog();
                            } else {
                                showPermissionWarningDialog();
                            }
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void showPermissionWarningDialog() {
        permissionDialog = new AlertDialog.Builder(this)
                .setTitle("PERMISSIONS REQUIRED")
                .setMessage("Give permissions for proper app functionality")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermissions();
                    }
                }).show();
    }

    public void showGoToAppSettingsDialog() {
        permissionDialog = new AlertDialog.Builder(this)
                .setTitle("PERMISSIONS PERMANENTLY DENIED")
                .setMessage("Go to app settings to enable permissions")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }
                }).show();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(boolean isCheckIn) {
        LocationModel locationModel = new LocationModel();
        mLocation.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String geoLocationName = addresses.get(0).getAddressLine(0);
                    //
//                    Address returnedAddress = addresses.get(0);
//                    StringBuilder strReturnedAddress = new StringBuilder("");
//
//                    strReturnedAddress.append(returnedAddress.getAddressLine(0))
//                            .append(" | ").append(returnedAddress.getAdminArea())
//                            .append(" | ").append(returnedAddress.getCountryCode())
//                            .append(" | ").append(returnedAddress.getCountryName())
//                            .append(" | ").append(returnedAddress.getFeatureName())
//                            .append(" | ").append(returnedAddress.getPremises())
//                            .append(" | ").append(returnedAddress.getLocality())
//                            .append(" | ").append(returnedAddress.getPostalCode())
//                            .append(" | ").append(returnedAddress.getSubAdminArea())
//                            .append(" | ").append(returnedAddress.getSubLocality())
//                            .append(" | ").append(returnedAddress.getSubThoroughfare())
//                            .append(" | ").append(returnedAddress.getThoroughfare());

//                    Log.i(TAG, "address : "+1+" : "+returnedAddress.getAddressLine(0));
//                    Log.i(TAG, "address : "+2+" : "+returnedAddress.getAddressLine(1));
//                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
//                        if(returnedAddress.getAddressLine(i) != null)
//                        {
//                            strReturnedAddress.append(returnedAddress.getAddressLine(i));
//                        }
//                    }
//                    Log.i(TAG, "getCurrentLocation: paul = "+strReturnedAddress.toString());
                    checkinLocation.setText(geoLocationName);
                    //Toast.makeText(MainActivity.this, "address:"+geoLocationName, Toast.LENGTH_SHORT).show();
                    locationModel.setGeoLocationName(geoLocationName);
                    locationModel.setGeoLat(location.getLatitude());
                    locationModel.setGeoLon(location.getLongitude());
                    //
                    if (isCheckIn) {
                        checkInAction(locationModel);
                    } else {
                        checkOutAction(locationModel);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Sorry! Location not found Please check GPS", Toast.LENGTH_SHORT).show();
                }

                //goToLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocationForDisplay() {
        LocationModel locationModel = new LocationModel();
        mLocation.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String geoLocationName = addresses.get(0).getAddressLine(0);
                    checkinLocation.setText(geoLocationName);
                    //Toast.makeText(MainActivity.this, "address:"+geoLocationName, Toast.LENGTH_SHORT).show();
                    locationModel.setGeoLocationName(geoLocationName);
                    locationModel.setGeoLat(location.getLatitude());
                    locationModel.setGeoLon(location.getLongitude());
                    //

                }

                //goToLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void checkInAction(LocationModel model) {
        if (model.getGeoLocationName() != null) {
            if (captureMode.equals("1")) {
                if (GeneralInternet.checkInternet(this)) {
                    //TODO: call direct clockin API
                    directClockinAction(model);
                } else {
                    storeRecord(model, true);
                }
            } else {
                if (GeneralInternet.checkInternet(this)) {
                    //TODO: call direct clockin API
                    directClockinAction(model);
                } else {
                    Toast.makeText(MainActivity.this, "Internet Connection is mandatory for Clock In!", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(MainActivity.this, "Location is null!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkOutAction(LocationModel model) {
        if (model.getGeoLocationName() != null) {
            if (captureMode.equals("1")) {
                if (GeneralInternet.checkInternet(this)) {
                    //TODO: call direct clockout API
                    directClockoutAction(model);
                } else {
                    storeRecord(model, false);
                }
            } else {
                if (GeneralInternet.checkInternet(this)) {
                    //TODO: call direct clockout API
                    directClockoutAction(model);
                } else {
                    Toast.makeText(MainActivity.this, "Internet Connection is mandatory for Clock out!", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(MainActivity.this, "Location is null!", Toast.LENGTH_SHORT).show();
        }
    }

    //    private void checkInAction(LocationModel model)
//    {
//        if(model.getGeoLocationName() != null)
//        {
//            if (SharedPrefDao.getInstance(getApplication()).getEmployeePref().getClockinGeoName() == null)
//            {
//                saveEmpInPref(model);
//                Calendar calendar = Calendar.getInstance();
//                Date date = calendar.getTime();
//                String dateStr = date.getDay() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900);
//                String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//                checkDisplayTxt.setText("Clock IN Time Information");
//                checkinLocation.setText(model.getGeoLocationName());
//                checkDateTxt.setText("Date : "+dateStr);
//                checkTimeTxt.setText("Clock In Time : "+timeStr);
//                Toast.makeText(this, "Clocked in Successfully!", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                showClockInDetails(SharedPrefDao.getInstance(getApplication()).getEmployeePref());
//                Toast.makeText(this, "Already Clocked in!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this, "Location is null!", Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void checkOutAction(LocationModel model)
//    {
//        if(model.getGeoLocationName() != null)
//        {
//            if (SharedPrefDao.getInstance(getApplication()).getEmployeePref().getClockinGeoName() == null)
//            {
//                Toast.makeText(MainActivity.this, "You have not clocked in yet!", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                storeRecord(SharedPrefDao.getInstance(getApplication()).getEmployeePref(), model);
//                //showClockInDetails(SharedPrefDao.getInstance(getApplication()).getEmployeePref());
//            }
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this, "Location is null!", Toast.LENGTH_SHORT).show();
//        }
//    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startClock();
        checkPermissions();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }

    //new location request
    private void initLocationRequest(boolean isCheckIn) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    //Toast.makeText(MainActivity.this, "GPS is on!", Toast.LENGTH_SHORT).show();
                    if (isCheckIn) {
                        getCurrentLocation(true);
                    } else {
                        getCurrentLocation(false);
                    }

                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTING);
                            } catch (IntentSender.SendIntentException sendIntentException) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });

    }

    private void initLocationRequestForDisplay() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    //Toast.makeText(MainActivity.this, "GPS is on!", Toast.LENGTH_SHORT).show();

                    getCurrentLocationForDisplay();


                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes
                                .RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTING);
                            } catch (IntentSender.SendIntentException sendIntentException) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });

    }

    //sync data to the backend server
    private void syncDataToBackEnd() {
        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i < list.size(); i++) {
                Employee empModel = list.get(i);
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("taTxnCompKey", companyKey);
                jsonObject.put("taEmpCode", empModel.getEmpCode());//
                if (!empModel.getClockinGeoDate().equals("null"))//check if clock in data
                {
                    jsonObject.put("taTxnDate", new GeneralUtils().getMonthInMMM(empModel.getClockinGeoDate()));
                    jsonObject.put("taTime", new GeneralUtils().getTimeInHHMMFormat(empModel.getClockinGeoTime()));
                    jsonObject.put("taTimeZone", empModel.getClockinGeoTimeZone());
                    jsonObject.put("taAttendIndicator", "0");
                    jsonObject.put("taEmployeeImageTemp", empModel.getImageEncoded());//empModel.getImageEncoded()
                    jsonObject.put("taGeoLatitude", empModel.getClockinGeoLat());
                    jsonObject.put("taGeoLangtitude", empModel.getClockinGeoLon());
                    jsonObject.put("taGeoLocationName", empModel.getClockinGeoName());
                } else {
                    jsonObject.put("taTxnDate", new GeneralUtils().getMonthInMMM(empModel.getClockoutGeoDate()));
                    jsonObject.put("taTime", new GeneralUtils().getTimeInHHMMFormat(empModel.getClockoutGeoTime()));
                    jsonObject.put("taTimeZone", empModel.getClockoutGeoTimeZone());
                    jsonObject.put("taAttendIndicator", "1");
                    jsonObject.put("taEmployeeImageTemp", empModel.getImageEncodedClockOut());//empModel.getImageEncodedClockOut()
                    jsonObject.put("taGeoLatitude", empModel.getClockoutGeoLat());
                    jsonObject.put("taGeoLangtitude", empModel.getClockoutGeoLon());
                    jsonObject.put("taGeoLocationName", empModel.getClockoutGeoName());
                }

                jsonObject.put("createdUser", currentUserKey);
                jsonObject.put("taAutoManual", "A");
                jsonObject.put("taSyncStatus", "N");
                jsonObject.put("taSource", "Mobile");
                jsonObject.put("taEmployeeImage", "");
                jsonObject.put("taDeviceName", Build.MANUFACTURER + " " + Build.MODEL);
                jsonObject.put("taDeviceOperatingSystem", "Andriod " + Build.VERSION.RELEASE);
                jsonObject.put("taStatus", "N");
                jsonObject.put("taErrorDesc", "TEST");
                //add the jsonobject to the jsonArray
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
//        Map<String, String> postParam= new HashMap<String, String>();
//        postParam.put("username", usernameStr);
//        postParam.put("password", passStr);


        Map<String, JSONArray> postParam = new HashMap<String, JSONArray>();
        postParam.put("data", jsonArray);
        Log.i(TAG, "syncDataToBackEnd: " + postParam.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, dataSyncUrl + "12-Mar-2022", new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    Log.e("synced", response.getString("message"));
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("keyword");
                    if (status.equals("success")) {
                        Toast.makeText(MainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        //TODO: empty the local database
                        database.employeeDao().deleteAll(list);
                        list.clear();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: failed to sync : " + jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.i(TAG, "onResponse: JSONException : " + e.getMessage());
                    Toast.makeText(MainActivity.this, R.string.common_error + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, R.string.common_error, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
                progressDialog.dismiss();

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-auth-token", token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjectRequest);
    }

    private void getAllPendingRecords() {
        if (!database.employeeDao().getAll().isEmpty()) {
            list.addAll(database.employeeDao().getAll());
            syncDataToBackEnd();
        } else {
            showMsg("No Pending Records found for syncing");
        }
    }
    private void showMsg(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    //Direct Clock in
    private void directClockinAction(LocationModel model) {
        Log.e("TokenClockIn",token);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        if (geoFenceReq.equals("Y")) {
            Log.i(TAG, "directClockinAction: geoArrStr = " + geoArrStr);
            //check if distance between the given coordinates and the current geocodes is greater than the radius
            //if yes return and show not inside the geofence
            boolean inRange = false;
            String[] geoArr = geoArrStr.split("-");
            Log.i(TAG, "directClockinAction: geoArr length : " + geoArr.length);
            for (int i = 0; i < geoArr.length; i++) {
                String geoObjStr = geoArr[i];
                Log.i(TAG, "directClockinAction: geoObjStr" + geoObjStr);
                String[] geoItemArr = geoObjStr.split("\\|");
                Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[0]);
                Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[1]);
                Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[2]);

                double desLat = Double.parseDouble(geoItemArr[0]);
                double desLon = Double.parseDouble(geoItemArr[1]);
                double rad = Double.parseDouble(geoItemArr[2]);
                double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
                double disInMetre = mDistance * 1000;
                Log.i(TAG, "directClockinAction: disInMetre -> " + disInMetre);
                if (disInMetre <= rad) {
                    inRange = true;
                    break;
                }
            }
            if (!inRange) {
                Toast.makeText(MainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("taTxnCompKey", String.valueOf(companyKey));
        postParam.put("taEmpCode", currentEmpCode);//
        postParam.put("taTxnDate", new GeneralUtils().getMonthInMMM(dateStr));
        postParam.put("taTime", new GeneralUtils().getTimeInHHMMFormat(timeStr));
        postParam.put("taTimeZone", new GeneralUtils().getCurrentTimeZone());
        postParam.put("taAttendIndicator", "0");
        postParam.put("taEmployeeImageTemp", encodedImageString.trim());//empModel.getImageEncoded()
        postParam.put("taGeoLatitude", String.valueOf(model.getGeoLat()));
        postParam.put("taGeoLangtitude", String.valueOf(model.getGeoLon()));
        postParam.put("taGeoLocationName", model.getGeoLocationName());
        postParam.put("createdUser", String.valueOf(currentUserKey));
        postParam.put("taAutoManual", "A");
        postParam.put("taSyncStatus", "N");
        postParam.put("taSource", "Mobile");
        postParam.put("taEmployeeImage", "");
        postParam.put("taDeviceName", Build.MANUFACTURER + " " + Build.MODEL);
        postParam.put("taDeviceOperatingSystem", "Andriod " + Build.VERSION.RELEASE);
        postParam.put("taStatus", "N");
        postParam.put("taErrorDesc", "TEST");


        Log.i(TAG, "taTxnCompKey: " +String.valueOf(companyKey));
        Log.i(TAG, "taEmpCode: " +String.valueOf(currentEmpCode));
        Log.i(TAG, "taTxnDate: " +String.valueOf(dateStr));
        Log.i(TAG, "taTime: " +String.valueOf(timeStr));
        Log.i(TAG, "taTimeZone: " +new GeneralUtils().getCurrentTimeZone());
        Log.i(TAG, "taAttendIndicator: " +"0");
        Log.i(TAG, "taEmployeeImageTemp: " +encodedImageString.trim());
        Log.i(TAG, "taGeoLatitude: " +String.valueOf(model.getGeoLat()));
        Log.i(TAG, "taGeoLangtitude: " + String.valueOf(model.getGeoLon()));
        Log.i(TAG, "taGeoLocationName: " +  model.getGeoLocationName());
        Log.i(TAG, "createdUser: " +  String.valueOf(currentUserKey));
        Log.i(TAG, "taAutoManual: " +  "A");
        Log.i(TAG, "taSyncStatus: " +  "A");
        Log.i(TAG, "taSource: " +  "Mobile");
        Log.i(TAG, "taEmployeeImage: " +  "Mobile");
        Log.i(TAG, "taDeviceName: " + Build.MANUFACTURER + " " + Build.MODEL);
        Log.i(TAG, "taDeviceOperatingSystem: "+"Andriod " + Build.VERSION.RELEASE);
        Log.i(TAG, "taStatus:  " + "N");
        Log.i(TAG, "taErrorDesc:  " +"TEST");


        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing Clock In...");
        Log.i(TAG, "syncDataToBackEnd: " + postParam.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, saveRecBySelfUrl, new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    Log.e("clockin", response.getString("message"));
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("keyword");
                    if (status.equals("success")) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        encodedImageString = null;
                        dispImg.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.snapshot));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: failed to sync : " + jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.i(TAG, "onResponse: JSONException : " + e.getMessage());
                    Toast.makeText(MainActivity.this, R.string.common_error + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, R.string.common_error, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-auth-token", token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjectRequest);
    }

    //
    //Direct Clock out
    private void directClockoutAction(LocationModel model) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        if (geoFenceReq.equals("Y")) {
            Log.i(TAG, "directClockoutAction: geoArrStr = " + geoArrStr);
            //check if distance between the given coordinates and the current geocodes is greater than the radius
            //if yes return and show not inside the geofence
            boolean inRange = false;
            String[] geoArr = geoArrStr.split("-");
            Log.i(TAG, "directClockoutAction: geoArr length : " + geoArr.length);
            for (int i = 0; i < geoArr.length; i++) {
                String geoObjStr = geoArr[i];
                Log.i(TAG, "directClockoutAction: geoObjStr" + geoObjStr);
                String[] geoItemArr = geoObjStr.split("\\|");
                Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[0]);
                Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[1]);
                Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[2]);

                double desLat = Double.parseDouble(geoItemArr[0]);
                double desLon = Double.parseDouble(geoItemArr[1]);
                double rad = Double.parseDouble(geoItemArr[2]);
                double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
                double disInMetre = mDistance * 1000;
                Log.i(TAG, "directClockoutAction: disInMetre -> " + disInMetre);
                if (disInMetre <= rad) {
                    inRange = true;
                    break;
                }
            }
            if (!inRange) {
                Toast.makeText(MainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("taTxnCompKey", String.valueOf(companyKey));
        postParam.put("taEmpCode", currentEmpCode);//
        postParam.put("taTxnDate", new GeneralUtils().getMonthInMMM(dateStr));
        postParam.put("taTime", new GeneralUtils().getTimeInHHMMFormat(timeStr));
        postParam.put("taTimeZone", new GeneralUtils().getCurrentTimeZone());
        postParam.put("taAttendIndicator", "1");
        postParam.put("taEmployeeImageTemp", encodedImageString);//empModel.getImageEncodedClockOut()
        postParam.put("taGeoLatitude", String.valueOf(model.getGeoLat()));
        postParam.put("taGeoLangtitude", String.valueOf(model.getGeoLon()));
        postParam.put("taGeoLocationName", model.getGeoLocationName());
        postParam.put("createdUser", String.valueOf(currentUserKey));
        postParam.put("taAutoManual", "A");
        postParam.put("taSyncStatus", "N");
        postParam.put("taSource", "Mobile");
        postParam.put("taEmployeeImage", "");
        postParam.put("taDeviceName", Build.MANUFACTURER + " " + Build.MODEL);
        postParam.put("taDeviceOperatingSystem", "Andriod " + Build.VERSION.RELEASE);
        postParam.put("taStatus", "N");
        postParam.put("taErrorDesc", "TEST");

        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Processing Clock Out...");
        Log.i(TAG, "syncDataToBackEnd: " + postParam.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, saveRecBySelfUrl, new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    Log.e("clockout", response.getString("message"));
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("keyword");
                    if (status.equals("success")) {
                        Toast.makeText(MainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        encodedImageString = null;
                        dispImg.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.snapshot));

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: failed to sync : " + jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.i(TAG, "onResponse: JSONException : " + e.getMessage());
                    Toast.makeText(MainActivity.this, R.string.common_error + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, R.string.common_error, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
                progressDialog.dismiss();

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-auth-token", token);
                //headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }
}