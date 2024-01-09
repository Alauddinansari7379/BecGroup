package com.amtechsolutions.becpbas.discardedcodes;//package com.amtech.becgroup.activity;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.widget.AppCompatButton;
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.amtech.becgroup.R;
//import com.amtech.becgroup.db.AppDatabase;
//import com.amtech.becgroup.db.Employee;
//import com.amtech.becgroup.models.LocationModel;
//import com.amtech.becgroup.utils.SharedPrefDao;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class MainActivity extends AppCompatActivity{
//    private static final String TAG = "MainActivity";
//    private AppDatabase database;
//    private AppCompatButton clockInBtn, clockOutBtn, updateBtn;
//    private boolean isPermissionGranted;
//    private AlertDialog permissionDialog;
//    private LocationManager locationManager;
//    private FusedLocationProviderClient mLocation;
//    private TextView checkDisplayTxt, checkDateTxt, checkTimeTxt, checkinLocation;
//    private String imgStr;
//    private CircleImageView proPicImg;
//    private TextView captureBtn;
//    private final int REQUEST_CODE = 1;
//    private Bitmap bitmap;
//    private String encodedImageString;
//    private ImageView dispImg;
//    //newlocation request vars
//    private LocationRequest locationRequest;
//    public static final int REQUEST_CHECK_SETTING = 1001;
//    //
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        // Define ActionBar object
//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//
//        // Define ColorDrawable object and parse color
//        // using parseColor method
//        // with color hash code as its parameter
//        ColorDrawable colorDrawable
//                = new ColorDrawable(Color.parseColor("#05143a"));
//
//        // Set BackgroundDrawable
//        actionBar.setBackgroundDrawable(colorDrawable);
//        mLocation = new FusedLocationProviderClient(MainActivity.this);
//        initView();
//        setListener();
//        initVar();
//        checkPermissions();
//
//        //
//    }
//
//    private void initView() {
//        checkDisplayTxt = (TextView) findViewById(R.id.check_display_txt);
//        checkDateTxt = (TextView) findViewById(R.id.geo_loc_date);
//        checkTimeTxt = (TextView) findViewById(R.id.geo_loc_time);
//        checkinLocation = (TextView) findViewById(R.id.geo_loc_txt);
//        clockInBtn = (AppCompatButton) findViewById(R.id.clockin_btn);
//        clockOutBtn = (AppCompatButton) findViewById(R.id.clockout_btn);
//        updateBtn = (AppCompatButton) findViewById(R.id.update_btn);
//        captureBtn = (TextView)findViewById(R.id.capture_btn);
//        dispImg = (ImageView)findViewById(R.id.display_img);
//    }
//
//    private void setListener() {
//        clockInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPermissionGranted)
//                {
//                    initLocationRequest(true);
////                    getCurrentLocation(true);
//                }
//
//            }
//        });
//        clockOutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPermissionGranted)
//                {
//                    initLocationRequest(false);
////                    getCurrentLocation(false);
//                }
//            }
//        });
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, PendingRecordActivity.class));
//            }
//        });
//        dispImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (SharedPrefDao.getInstance(getApplication()).getEmployeePref().getClockinGeoName() == null)
//                {
//                    Toast.makeText(MainActivity.this, "Clock in first!", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, REQUEST_CODE);
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
////            Uri filepath = data.getData();
////            String imageStr = filepath.toString();
//            //Get captured image
//            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
//            //Set captured image to imageview
//            dispImg.setImageBitmap(capturedImage);
//            encodeBitmapImage(capturedImage);
//            //
//            Employee employee = new Employee();
//            employee.setImageUri("empty");
//            employee.setImageEncoded(encodedImageString);
//            SharedPrefDao.getInstance(getApplication()).saveImgPref(employee);
//        }
//        else if(requestCode == REQUEST_CHECK_SETTING)
//        {
//            switch (resultCode)
//            {
//                case Activity.RESULT_OK:
//                    Toast.makeText(this, "GPS is turned", Toast.LENGTH_SHORT).show();
//                    break;
//                case Activity.RESULT_CANCELED:
//                    Toast.makeText(this, "GPS is required for the app to work!", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//    private void encodeBitmapImage(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
//        encodedImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);//image finally encoded to string
//    }
//
//    private void initVar() {
//        database = AppDatabase.getInstance(this);
//    }
//
//    private void storeRecord(Employee emp, LocationModel model) {
//        if(emp.getImageUri() == null || emp.getImageEncoded() == null)
//        {
//            Toast.makeText(MainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
//            return;
//        }
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
//
//    private void saveEmpInPref(LocationModel model) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String dateStr = date.getDay() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900);
//        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//        Employee employee = new Employee();
//        employee.setClockinGeoName(model.getGeoLocationName());
//        employee.setClockinGeoLat(String.valueOf(model.getGeoLat()));
//        employee.setClockinGeoLon(String.valueOf(model.getGeoLon()));
//        employee.setClockinGeoDate(dateStr);
//        employee.setClockinGeoTime(timeStr);
//        SharedPrefDao.getInstance(getApplication()).saveUserPref(employee);
//    }
//
//    private void showClockInDetails(Employee employee) {
//        checkDisplayTxt.setText("Clock IN Time Information");
//        checkinLocation.setText(employee.getClockinGeoName());
//        checkDateTxt.setText("Date : "+employee.getClockinGeoDate());
//        checkTimeTxt.setText("Clock In Time : "+employee.getClockinGeoTime());
//    }
//
//    public void checkPermissions() {
//        Dexter.withActivity(MainActivity.this)
//                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {
//                            isPermissionGranted = true;
//
//                            //Toast.makeText(MainActivity.this, "All permissions granted!", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            if(report.isAnyPermissionPermanentlyDenied())
//                            {
//                                showGoToAppSettingsDialog();
//                            }
//                            else
//                            {
//                                showPermissionWarningDialog();
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//    }
//    public void showPermissionWarningDialog()
//    {
//        permissionDialog = new AlertDialog.Builder(this)
//                .setTitle("PERMISSIONS REQUIRED")
//                .setMessage("Give permissions for proper app functionality")
//                .setCancelable(false)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        checkPermissions();
//                    }
//                }).show();
//    }
//    public void showGoToAppSettingsDialog()
//    {
//        permissionDialog = new AlertDialog.Builder(this)
//                .setTitle("PERMISSIONS PERMANENTLY DENIED")
//                .setMessage("Go to app settings to enable permissions")
//                .setCancelable(false)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//
//                    }
//                }).show();
//    }
//    @SuppressLint("MissingPermission")
//    private void getCurrentLocation(boolean isCheckIn)
//    {
//        LocationModel locationModel = new LocationModel();
//        mLocation.getLastLocation().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Location location = task.getResult();
//                if(location != null)
//                {
//                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
//                    List<Address> addresses = null;
//                    try {
//                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude() ,1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    String geoLocationName = addresses.get(0).getAddressLine(0);
//                    Toast.makeText(MainActivity.this, "address:"+geoLocationName, Toast.LENGTH_SHORT).show();
//                    locationModel.setGeoLocationName(geoLocationName);
//                    locationModel.setGeoLat(location.getLatitude());
//                    locationModel.setGeoLon(location.getLongitude());
//                    //
//                    if(isCheckIn)
//                    {
//                        checkInAction(locationModel);
//                    }
//                    else
//                    {
//                        checkOutAction(locationModel);
//                    }
//                }
//
//                //goToLocation(location.getLatitude(), location.getLongitude());
//            }
//        });
//    }
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
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        checkPermissions();
//    }
//    //new location request
//    private void initLocationRequest(boolean isCheckIn)
//    {
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setInterval(2000);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
//                .checkLocationSettings(builder.build());
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    //Toast.makeText(MainActivity.this, "GPS is on!", Toast.LENGTH_SHORT).show();
//                    if(isCheckIn)
//                    {
//                        getCurrentLocation(true);
//                    }
//                    else
//                    {
//                        getCurrentLocation(false);
//                    }
//
//                } catch (ApiException e) {
//                    switch (e.getStatusCode())
//                    {
//                        case LocationSettingsStatusCodes
//                                .RESOLUTION_REQUIRED:
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException)e;
//                                resolvableApiException.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTING);
//                            } catch (IntentSender.SendIntentException sendIntentException) {
//
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE :
//                            break;
//                    }
//                }
//            }
//        });
//
//    }
//
//    //
//}