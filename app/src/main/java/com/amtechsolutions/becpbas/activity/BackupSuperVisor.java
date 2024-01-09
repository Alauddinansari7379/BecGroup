package com.amtechsolutions.becpbas.activity;

import static com.amtechsolutions.becpbas.utils.AppConstants.token;

import com.android.volley.AuthFailureError;

import java.util.HashMap;
import java.util.Map;

public class BackupSuperVisor {
//
//    /*
//    package com.amtechsolutions.becpbas.activity;
//
//import static com.amtechsolutions.becpbas.utils.AppConstants.capPhotoMand;
//import static com.amtechsolutions.becpbas.utils.AppConstants.captureMode;
//import static com.amtechsolutions.becpbas.utils.AppConstants.companyKey;
//import static com.amtechsolutions.becpbas.utils.AppConstants.currentUserKey;
//import static com.amtechsolutions.becpbas.utils.AppConstants.currentUsername;
//import static com.amtechsolutions.becpbas.utils.AppConstants.dataSyncUrl;
//import static com.amtechsolutions.becpbas.utils.AppConstants.geoArrStr;
//import static com.amtechsolutions.becpbas.utils.AppConstants.geoFenceReq;
//import static com.amtechsolutions.becpbas.utils.AppConstants.saveRecBySupervisorUrl;
//import static com.amtechsolutions.becpbas.utils.AppConstants.token;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Looper;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.util.Base64;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.widget.AppCompatButton;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.amtechsolutions.becpbas.BaseActivity;
//import com.amtechsolutions.becpbas.R;
//import com.amtechsolutions.becpbas.api.service.MainService;
//import com.amtechsolutions.becpbas.db.AppDatabase;
//import com.amtechsolutions.becpbas.db.Supervisor;
//import com.amtechsolutions.becpbas.models.LocationModel;
//import com.amtechsolutions.becpbas.newdataModel.DataItem;
//import com.amtechsolutions.becpbas.newdataModel.SuperData;
//import com.amtechsolutions.becpbas.utils.GeneralInternet;
//import com.amtechsolutions.becpbas.utils.GeneralUtils;
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.HurlStack;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.security.ProviderInstaller;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.gson.JsonNull;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class SupervisorMainActivity extends BaseActivity {
//    ProgressBar progressBar;
//    private static final String TAG = "SupervisorMainActivity";
//    private Dialog dialogChckIn, dialogChckOut;
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
//    private ImageView currImgView;
//    private String currEmpCode;
//    private ImageView dispImg;
//    //newlocation request vars
//    private LocationRequest locationRequest;
//    public static final int REQUEST_CHECK_SETTING = 1001;
//    public static final int LOCATION = 1002;
//    //
//    private ProgressDialog progressDialog;
//    private List<Supervisor> list = new ArrayList<>();
//    private Dialog dialog;
//    private TextView usernameTxt;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_supervisor_main);
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
//        actionBar.setTitle("DashBoard");
//        mLocation = new FusedLocationProviderClient(SupervisorMainActivity.this);
//        initClockInDialog();
//        initClockOutDialog();
//        initView();
//        setListener();
//        initVar();
//        initLogoutDialog();
//        checkPermissions();
//
//        usernameTxt.setText("Username : " + currentUsername);
//        //temp TODO: delete
//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
//        int version = Build.VERSION.SDK_INT;
//        String versionRelease = Build.VERSION.RELEASE;
//
//        Log.e(TAG, "manufacturer " + manufacturer
//                + " \n model " + model
//                + " \n version " + version
//                + " \n versionRelease " + versionRelease
//        );
////        Button check = findViewById(R.id.check);
////        check.setOnClickListener(v -> {
////            if (GeneralInternet.checkInternet(this)) {
////                getAllPendingRecords();
////            }
////        });
//        //temp
//    }
//
//    private void initView() {
//        clockInBtn = (AppCompatButton) findViewById(R.id.clockin_btn);
//        clockOutBtn = (AppCompatButton) findViewById(R.id.clockout_btn);
//        updateBtn = (AppCompatButton) findViewById(R.id.update_btn);
//        checkDisplayTxt = (TextView) findViewById(R.id.geo_loc_txt);
//        usernameTxt = (TextView) findViewById(R.id.username_txt);
//        progressBar = (ProgressBar) findViewById(R.id.progress);
//    }
//
//    private void setListener() {
//        clockInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogChckIn.show();
//            }
//        });
//        clockOutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogChckOut.show();
//            }
//        });
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SupervisorMainActivity.this, PendingRecordSupActivity.class));
//            }
//        });
//    }
//
//    private void initClockInDialog() {
//        dialogChckIn = new Dialog(SupervisorMainActivity.this);
//        dialogChckIn.setContentView(R.layout.clockin_form_dialog);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            dialogChckIn.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bground));
//        }
//        dialogChckIn.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogChckIn.setCancelable(false);
//        dialogChckIn.getWindow().getAttributes().windowAnimations = R.style.animation;
//        EditText empCodeEdTxt = dialogChckIn.findViewById(R.id.emp_code_txt);
//        EditText notesEdTxt = dialogChckIn.findViewById(R.id.notes_txt);
//        TextView captureBtn = dialogChckIn.findViewById(R.id.capture_btn);
//        ImageView imageView = dialogChckIn.findViewById(R.id.disp_img);
//        AppCompatButton saveBtn = dialogChckIn.findViewById(R.id.save_btn);
//        ImageView canBtn = dialogChckIn.findViewById(R.id.can_btn);
////        if(capPhotoMand.equals("N"))
////        {
////            captureBtn.setVisibility(View.GONE);
////        }
//
//        captureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currImgView = imageView;
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        });
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (capPhotoMand.equals("Y")) {
//                    if (empCodeEdTxt.getText().toString().isEmpty()) {
//                        Toast.makeText(SupervisorMainActivity.this, "Enter Employee Code!", Toast.LENGTH_SHORT).show();
//                    } else if (encodedImageString == null) {
//                        Toast.makeText(SupervisorMainActivity.this, "Capture Image of Employee!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (isPermissionGranted) {
//                            currEmpCode = empCodeEdTxt.getText().toString().trim();
//                            resetForm(empCodeEdTxt, imageView, notesEdTxt);
//                            initLocationRequest(true);
////                    getCurrentLocation(true);
//                        }
//                    }
//                } else {
//                    if (empCodeEdTxt.getText().toString().isEmpty()) {
//                        Toast.makeText(SupervisorMainActivity.this, "Enter Employee Code!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (isPermissionGranted) {
//                            currEmpCode = empCodeEdTxt.getText().toString().trim();
//                            if (encodedImageString == null) {
//                                encodedImageString = "";
//                            }
//                            resetForm(empCodeEdTxt, imageView, notesEdTxt);
//                            initLocationRequest(true);
////                    getCurrentLocation(true);
//                        }
//                    }
//                }
//
//            }
//        });
//        canBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetForm(empCodeEdTxt, imageView, notesEdTxt);
//                encodedImageString = null;
//                currImgView = null;
//                dialogChckIn.dismiss();
//            }
//        });
//    }
//
//    private void resetForm(EditText empcodeEdTxt, ImageView displayImage, EditText notesEdTxt) {
//        empcodeEdTxt.getText().clear();
//        notesEdTxt.getText().clear();
//        displayImage.setImageDrawable(ContextCompat.getDrawable(SupervisorMainActivity.this, R.drawable.user));
//    }
//
//    private void initClockOutDialog() {
//        dialogChckOut = new Dialog(SupervisorMainActivity.this);
//        dialogChckOut.setContentView(R.layout.cloclout_form_dialog);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            dialogChckOut.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bground));
//        }
//        dialogChckOut.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogChckOut.setCancelable(false);
//        dialogChckOut.getWindow().getAttributes().windowAnimations = R.style.animation;
//        EditText empCodeEdTxt = dialogChckOut.findViewById(R.id.emp_code_txt);
//        EditText notesEdTxt = dialogChckOut.findViewById(R.id.notes_txt);
//        TextView captureBtn = dialogChckOut.findViewById(R.id.capture_btn);
//        ImageView imageView = dialogChckOut.findViewById(R.id.disp_img);
//        AppCompatButton saveBtn = dialogChckOut.findViewById(R.id.save_btn);
//        ImageView canBtn = dialogChckOut.findViewById(R.id.can_btn);
//        captureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currImgView = imageView;
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        });
//        saveBtn.setOnClickListener(v -> {
//            if (capPhotoMand.equals("Y")) {
//                if (empCodeEdTxt.getText().toString().isEmpty()) {
//                    Toast.makeText(SupervisorMainActivity.this, "Enter Employee Code!", Toast.LENGTH_SHORT).show();
//                } else if (encodedImageString == null) {
//                    Toast.makeText(SupervisorMainActivity.this, "Capture Image of Employee!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (isPermissionGranted) {
//                        currEmpCode = empCodeEdTxt.getText().toString().trim();
//                        if (encodedImageString == null) {
//                            encodedImageString = "";
//                        }
//                        resetForm(empCodeEdTxt, imageView, notesEdTxt);
//                        initLocationRequest(false);
////                    getCurrentLocation(true);
//                    }
//                }
//            } else {
//                if (empCodeEdTxt.getText().toString().isEmpty()) {
//                    Toast.makeText(SupervisorMainActivity.this, "Enter Employee Code!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (isPermissionGranted) {
//                        currEmpCode = empCodeEdTxt.getText().toString().trim();
//                        resetForm(empCodeEdTxt, imageView, notesEdTxt);
//                        initLocationRequest(false);
////                    getCurrentLocation(true);
//                    }
//                }
//            }
//        });
//        canBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetForm(empCodeEdTxt, imageView, notesEdTxt);
//                encodedImageString = null;
//                currImgView = null;
//                dialogChckOut.dismiss();
//            }
//        });
//    }
//
//    private void initVar() {
//        database = AppDatabase.getInstance(this);
//    }
//
//    public void checkPermissions() {
//        Dexter.withContext(getApplicationContext())
//                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {
//                            isPermissionGranted = true;
//                            if (isPermissionGranted) {
//                                initLocationRequestForDisplay();
//                                Log.d(TAG, "onPermissionsChecked: G");
//                            }
////                            getAllPendingRecords();
//                            //Toast.makeText(MainActivity.this, "All permissions granted!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (report.isAnyPermissionPermanentlyDenied()) {
//                                // showGoToAppSettingsDialog();
//                                Log.d(TAG, "onPermissionsChecked: R");
//                                displayLocationSettingsRequest(SupervisorMainActivity.this);
//                            } else {
//                                showPermissionWarningDialog();
//                                Log.d(TAG, "onPermissionsChecked: M");
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
//
//    public void showPermissionWarningDialog() {
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
//
//    private void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(LocationServices.API).build();
//        googleApiClient.connect();
//
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(10000 / 2);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i(TAG, "All location settings are satisfied.");
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
//
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            status.startResolutionForResult(SupervisorMainActivity.this, LOCATION);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
//                        break;
//                }
//            }
//        });
//    }
//
//    public void showGoToAppSettingsDialog() {
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
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        //   checkPermissions();
//    }
//
//    private void checkInAction(LocationModel model) {
//        if (model.getGeoLocationName() != null) {
//            if (captureMode.equals("1")) {
//                if (GeneralInternet.checkInternet(this)) {
//                    //TODO: call direct clockin API
//                    directClockinAction(model);
//                } else {
//                    storeRecord(model, true);
//                }
//            } else {
//                if (GeneralInternet.checkInternet(this)) {
//                    //TODO: call direct clockin API
//                    directClockinAction(model);
//                } else {
//                    Toast.makeText(SupervisorMainActivity.this, "Internet Connection is mandatory for Clock In!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else {
//            Toast.makeText(SupervisorMainActivity.this, "Location is null!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void checkOutAction(LocationModel model) {
//        if (model.getGeoLocationName() != null) {
//            if (captureMode.equals("1")) {
//                if (GeneralInternet.checkInternet(this)) {
//                    //TODO: call direct clockout API
//                    directClockoutAction(model);
//                } else {
//                    storeRecord(model, false);
//                }
//            } else {
//                if (GeneralInternet.checkInternet(this)) {
//                    //TODO: call direct clockout API
//                    directClockoutAction(model);
//                } else {
//                    Toast.makeText(SupervisorMainActivity.this, "Internet Connection is mandatory for Clock In!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else {
//            Toast.makeText(SupervisorMainActivity.this, "Location is null!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void storeRecord(LocationModel model, boolean isClockin) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
//        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//        Supervisor employee = new Supervisor();
//        if (isClockin) {
//            if (encodedImageString == null) {
//                Toast.makeText(SupervisorMainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (geoFenceReq.equals("Y")) {
//                Log.i(TAG, "directClockinAction: geoArrStr = " + geoArrStr);
//                //check if distance between the given coordinates and the current geocodes is greater than the radius
//                //if yes return and show not inside the geofence
//                boolean inRange = false;
//                String[] geoArr = geoArrStr.split("-");
//                Log.i(TAG, "directClockinAction: geoArr length : " + geoArr.length);
//                for (int i = 0; i < geoArr.length; i++) {
//                    String geoObjStr = geoArr[i];
//                    Log.i(TAG, "directClockinAction: geoObjStr" + geoObjStr);
//                    String[] geoItemArr = geoObjStr.split("\\|");
//                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[0]);
//                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[1]);
//                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[2]);
//
//                    double desLat = Double.parseDouble(geoItemArr[0]);
//                    double desLon = Double.parseDouble(geoItemArr[1]);
//                    double rad = Double.parseDouble(geoItemArr[2]);
//                    double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
//                    double disInMetre = mDistance * 1000;
//                    Log.i(TAG, "directClockinAction: disInMetre -> " + disInMetre);
//                    if (disInMetre <= rad) {
//                        inRange = true;
//                        break;
//                    }
//                }
//                if (!inRange) {
//                    Toast.makeText(SupervisorMainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//            }
//            employee.setEmpCode(currEmpCode);
//            employee.setClockinGeoName(model.getGeoLocationName());
//            employee.setClockinGeoLat(String.valueOf(model.getGeoLat()));
//            employee.setClockinGeoLon(String.valueOf(model.getGeoLon()));
//            employee.setClockinGeoDate(dateStr);
//            employee.setClockinGeoTime(timeStr);
//            employee.setClockinGeoTimeZone(new GeneralUtils().getCurrentTimeZone());
//            employee.setImageUri("null");
//            employee.setImageEncoded(encodedImageString);
//            employee.setClockoutGeoName("null");
//            employee.setClockoutGeoLat("null");
//            employee.setClockoutGeoLon("null");
//            employee.setClockoutGeoDate("null");
//            employee.setClockoutGeoTime("null");
//            employee.setImageUriClockOut("null");
//            employee.setImageEncodedClockOut("null");
//            database.superVisorDao().insert(employee);
//            Toast.makeText(this, "Clocked in Successfully!", Toast.LENGTH_SHORT).show();
//            encodedImageString = null;
//            currImgView = null;
//            currEmpCode = null;
//        } else {
//            if (encodedImageString == null) {
//                Toast.makeText(SupervisorMainActivity.this, "Image not captured yet!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (geoFenceReq.equals("Y")) {
//                Log.i(TAG, "directClockinAction: geoArrStr = " + geoArrStr);
//                //check if distance between the given coordinates and the current geocodes is greater than the radius
//                //if yes return and show not inside the geofence
//                boolean inRange = false;
//                String[] geoArr = geoArrStr.split("-");
//                Log.i(TAG, "directClockinAction: geoArr length : " + geoArr.length);
//                for (int i = 0; i < geoArr.length; i++) {
//                    String geoObjStr = geoArr[i];
//                    Log.i(TAG, "directClockinAction: geoObjStr" + geoObjStr);
//                    String[] geoItemArr = geoObjStr.split("\\|");
//                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[0]);
//                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[1]);
//                    Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[2]);
//
//                    double desLat = Double.parseDouble(geoItemArr[0]);
//                    double desLon = Double.parseDouble(geoItemArr[1]);
//                    double rad = Double.parseDouble(geoItemArr[2]);
//                    double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
//                    double disInMetre = mDistance * 1000;
//                    Log.i(TAG, "directClockinAction: disInMetre -> " + disInMetre);
//                    if (disInMetre <= rad) {
//                        inRange = true;
//                        break;
//                    }
//                }
//                if (!inRange) {
//                    Toast.makeText(SupervisorMainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//            }
//            employee.setEmpCode(currEmpCode);
//            employee.setClockinGeoName("null");
//            employee.setClockinGeoLat("null");
//            employee.setClockinGeoLon("null");
//            employee.setClockinGeoDate("null");
//            employee.setClockinGeoTime("null");
//            employee.setImageUri("null");
//            employee.setImageEncoded("null");
//            employee.setClockoutGeoName(model.getGeoLocationName());
//            employee.setClockoutGeoLat(String.valueOf(model.getGeoLat()));
//            employee.setClockoutGeoLon(String.valueOf(model.getGeoLon()));
//            employee.setClockoutGeoDate(dateStr);
//            employee.setClockoutGeoTime(timeStr);
//            employee.setClockoutGeoTimeZone(new GeneralUtils().getCurrentTimeZone());
//            employee.setImageUriClockOut("null");
//            employee.setImageEncodedClockOut(encodedImageString);
//            database.superVisorDao().insert(employee);
//            Toast.makeText(this, "Clocked out Successfully!", Toast.LENGTH_SHORT).show();
//            encodedImageString = null;
//            currImgView = null;
//            currEmpCode = null;
////            startActivity(new Intent(SupervisorMainActivity.this, PendingRecordActivity.class));
//        }
//        //SharedPrefDao.getInstance(getApplication()).removeUserPref();
//
//    }
//
//    @SuppressLint("MissingPermission")
//    private void getCurrentLocation(boolean isCheckIn) {
//        LocationModel locationModel = new LocationModel();
//        mLocation.getLastLocation().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Location location = task.getResult();
//                if (location != null) {
//                    Geocoder geocoder = new Geocoder(SupervisorMainActivity.this, Locale.getDefault());
//                    List<Address> addresses = null;
//                    try {
//                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    String geoLocationName = addresses.get(0).getAddressLine(0);
//                    //
//                    Address returnedAddress = addresses.get(0);
//                    StringBuilder strReturnedAddress = new StringBuilder("");
//
//                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
//                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
//                    }
//                    //
//                    checkDisplayTxt.setText(geoLocationName);
//                    //Toast.makeText(SupervisorMainActivity.this, "address:"+geoLocationName, Toast.LENGTH_SHORT).show();
//                    locationModel.setGeoLocationName(geoLocationName);
//                    locationModel.setGeoLat(location.getLatitude());
//                    locationModel.setGeoLon(location.getLongitude());
//                    //
//                    if (isCheckIn) {
//                        checkInAction(locationModel);
//                    } else {
//                        checkOutAction(locationModel);
//                    }
//                }
//
//                //goToLocation(location.getLatitude(), location.getLongitude());
//            }
//        });
//    }
//
//    private void initLogoutDialog() {
//        dialog = new Dialog(SupervisorMainActivity.this);
//        dialog.setContentView(R.layout.custom_alert_dialog);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bground));
//        }
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//        TextView okBtn = dialog.findViewById(R.id.ok_btn);
//        TextView canBtn = dialog.findViewById(R.id.can_btn);
//        okBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences pref = getSharedPreferences("BecGroupApp", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                //String rememberMe = pref.getString("remember", "noValue");
//                editor.clear();
////                if(rememberMe.equals("YES"))
////                {
////                    editor.putString("login", "0");
////                }
////                else if(rememberMe.equals("NO"))
////                {
////                    editor.clear();
////                }
//                editor.apply();
//                dialog.dismiss();
//                startActivity(new Intent(SupervisorMainActivity.this, NewLoginActivity.class));
//                finish();
//
//            }
//        });
//        canBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sup_main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.logout) {
//            dialog.show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
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
//            currImgView.setImageBitmap(capturedImage);
//            encodeBitmapImage(capturedImage);
//            //
////            Employee employee = new Employee();
////            employee.setImageUri("empty");
////            employee.setImageEncoded(encodedImageString);
////            SharedPrefDao.getInstance(getApplication()).saveImgPref(employee);
//        } else if (requestCode == LOCATION) {
//            switch (resultCode) {
//                case Activity.RESULT_OK:
//                    progressBar.setVisibility(View.VISIBLE);
//                    com.google.android.gms.location.LocationRequest locationRequest = new com.google.android.gms.location.LocationRequest();
//                    locationRequest.setInterval(10000);
//                    locationRequest.setFastestInterval(3000);
//                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                    if (ActivityCompat.checkSelfPermission(SupervisorMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    LocationServices.getFusedLocationProviderClient(SupervisorMainActivity.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//                                @Override
//                                public void onLocationResult(LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//                                    LocationServices.getFusedLocationProviderClient(SupervisorMainActivity.this)
//                                            .removeLocationUpdates(this);
//                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
//                                        int latestLocationIndex = locationResult.getLocations().size() - 1;
//                                        Double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
//                                        Double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
//                                        getAddress(latitude, longitude, checkDisplayTxt);
//                                    } else {
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            }, Looper.getMainLooper());
//                    break;
//                case Activity.RESULT_CANCELED:
//                    Toast.makeText(this, "GPS is required for the app to work!", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//
//    public void getAddress(double lat, double lng, TextView location) {
//        Geocoder geocoder = new Geocoder(SupervisorMainActivity.this, Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
//            Address obj = addresses.get(0);
//            String add = obj.getAddressLine(0);
//            //   add = add + "\n" + obj.getCountryName();
//            //add = add + "\n" + obj.getCountryCode();
////            add = add + "\n" + obj.getAdminArea();
////            add = add + "\n" + obj.getPostalCode();
////            add = add + "\n" + obj.getSubAdminArea();
////            add = add + "\n" + obj.getLocality();
////            add = add + "\n" + obj.getSubThoroughfare();
//            //   location.setEnabled(false);
//            location.setText(add);
//            progressBar.setVisibility(View.GONE);
//        } catch (IOException e) {
//            e.printStackTrace();
//            progressBar.setVisibility(View.GONE);
//        }
//    }
//
//    private void encodeBitmapImage(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
//        encodedImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);//image finally encoded to string
//    }
//
//    //new location request
//    private void initLocationRequest(boolean isCheckIn) {
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
//                    if (isCheckIn) {
//                        getCurrentLocation(true);
//                    } else {
//                        getCurrentLocation(false);
//                    }
//
//                } catch (ApiException e) {
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes
//                                .RESOLUTION_REQUIRED:
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(SupervisorMainActivity.this, REQUEST_CHECK_SETTING);
//                            } catch (IntentSender.SendIntentException sendIntentException) {
//
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            break;
//                    }
//                }
//            }
//        });
//
//    }
//
//    private void initLocationRequestForDisplay() {
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
//
//                    getCurrentLocationForDisplay();
//
//
//                } catch (ApiException e) {
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes
//                                .RESOLUTION_REQUIRED:
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(SupervisorMainActivity.this, REQUEST_CHECK_SETTING);
//                            } catch (IntentSender.SendIntentException sendIntentException) {
//
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            break;
//                    }
//                }
//            }
//        });
//
//    }
//
//    @SuppressLint("MissingPermission")
//    private void getCurrentLocationForDisplay() {
//        progressBar.setVisibility(View.VISIBLE);
//        LocationModel locationModel = new LocationModel();
//        com.google.android.gms.location.LocationRequest locationRequest = new com.google.android.gms.location.LocationRequest();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        if (ActivityCompat.checkSelfPermission(SupervisorMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        LocationServices.getFusedLocationProviderClient(SupervisorMainActivity.this)
//                .requestLocationUpdates(locationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        super.onLocationResult(locationResult);
//                        LocationServices.getFusedLocationProviderClient(SupervisorMainActivity.this)
//                                .removeLocationUpdates(this);
//                        if (locationResult != null && locationResult.getLocations().size() > 0) {
//                            int latestLocationIndex = locationResult.getLocations().size() - 1;
//                            Double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
//                            Double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
//                            Geocoder geocoder = new Geocoder(SupervisorMainActivity.this, Locale.getDefault());
//                            String add = "";
//                            try {
//                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                                Address obj = addresses.get(0);
//                                add = obj.getAddressLine(0);
//                                progressBar.setVisibility(View.GONE);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                progressBar.setVisibility(View.GONE);
//                            }
//                            checkDisplayTxt.setText(add);
//                            locationModel.setGeoLocationName(add);
//                            locationModel.setGeoLat(latitude);
//                            locationModel.setGeoLon(longitude);
//                        } else {
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                }, Looper.getMainLooper());
////        mLocation.getLastLocation().addOnCompleteListener(task -> {
////            if (task.isSuccessful()) {
////                Location location = task.getResult();
////                if (location != null) {
////                    Geocoder geocoder = new Geocoder(SupervisorMainActivity.this, Locale.getDefault());
////                    List<Address> addresses = null;
////                    try {
////                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    String geoLocationName = addresses.get(0).getAddressLine(0);
////                    checkDisplayTxt.setText(geoLocationName);
////                    locationModel.setGeoLocationName(geoLocationName);
////                    locationModel.setGeoLat(location.getLatitude());
////                    locationModel.setGeoLon(location.getLongitude());
////                    //
////
////                }
////
////                //goToLocation(location.getLatitude(), location.getLongitude());
////            }
////        });
//    }
//
//    //
//    //sync data to the backend server
//    private void syncDataToBackEnd() {
//        JSONArray jsonArray = new JSONArray();
//        try {
//
//            for (int i = 0; i < list.size(); i++) {
//                Supervisor empModel = list.get(i);
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.put("taTxnCompKey", companyKey);
//                jsonObject.put("taEmpCode", empModel.getEmpCode());
//                if (!empModel.getClockinGeoDate().equals("null"))//check if clock in data
//                {
//                    jsonObject.put("taTxnDate", new GeneralUtils().getMonthInMMM(empModel.getClockinGeoDate()));
//                    jsonObject.put("taTime", new GeneralUtils().getTimeInHHMMFormat(empModel.getClockinGeoTime()));
//                    jsonObject.put("taTimeZone", empModel.getClockinGeoTimeZone());
//                    jsonObject.put("taAttendIndicator", "0");
//                    jsonObject.put("taEmployeeImageTemp", empModel.getImageEncoded());
//                    jsonObject.put("taGeoLatitude", empModel.getClockinGeoLat());
//                    jsonObject.put("taGeoLangtitude", empModel.getClockinGeoLon());
//                    jsonObject.put("taGeoLocationName", empModel.getClockinGeoName());
//                } else {
//                    jsonObject.put("taTxnDate", new GeneralUtils().getMonthInMMM(empModel.getClockoutGeoDate()));
//                    jsonObject.put("taTime", new GeneralUtils().getTimeInHHMMFormat(empModel.getClockoutGeoTime()));
//                    jsonObject.put("taTimeZone", empModel.getClockoutGeoTimeZone());
//                    jsonObject.put("taAttendIndicator", "1");
//                    jsonObject.put("taEmployeeImageTemp", empModel.getImageEncodedClockOut());
//                    jsonObject.put("taGeoLatitude", empModel.getClockoutGeoLat());
//                    jsonObject.put("taGeoLangtitude", empModel.getClockoutGeoLon());
//                    jsonObject.put("taGeoLocationName", empModel.getClockoutGeoName());
//                }
//
//                jsonObject.put("createdUser", currentUserKey);
//                jsonObject.put("taAutoManual", "A");
//                jsonObject.put("taSyncStatus", "N");
//                jsonObject.put("taSource", "Mobile");
//                jsonObject.put("taEmployeeImage", "");
//                jsonObject.put("taDeviceName", Build.MANUFACTURER + " " + Build.MODEL);
//                jsonObject.put("taDeviceOperatingSystem", "Andriod " + Build.VERSION.RELEASE);
//                jsonObject.put("taStatus", "N");
//                jsonObject.put("taErrorDesc", "TEST");
//                //add the jsonobject to the jsonArray
//                jsonArray.put(jsonObject);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        progressDialog = ProgressDialog.show(SupervisorMainActivity.this, "", "Please wait...");
////        Map<String, String> postParam= new HashMap<String, String>();
////        postParam.put("username", usernameStr);
////        postParam.put("password", passStr);
//
//
//        Map<String, JSONArray> postParam = new HashMap<String, JSONArray>();
//        postParam.put("data", jsonArray);
//        Log.i(TAG, "syncDataToBackEnd: " + postParam.toString());
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, dataSyncUrl, new JSONObject(postParam), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                progressDialog.dismiss();
//                try {
//                    Log.e("synced", response.getString("message"));
//                    JSONObject jsonObject = response;
//                    String status = jsonObject.getString("keyword");
//                    if (status.equals("success")) {
//                        Toast.makeText(SupervisorMainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
////                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                        //TODO: empty the local database
////                        database.superVisorDao().deleteAll(list);
////                        list.clear();
//
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(SupervisorMainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                    Toast.makeText(SupervisorMainActivity.this, R.string.common_error + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SupervisorMainActivity.this, R.string.common_error, Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "onErrorResponse: " + error.getMessage());
//            }
//        }) {
//
//            /**
//             * Passing some request headers
//             * */
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("x-auth-token", token);
//        //headers.put("Content-Type", "application/json; charset=utf-8");
//        return headers;
//    }
//};
//        RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStack(null, ClientSSLSocketFactory.getSocketFactory(this)));
//                Log.d(TAG, "syncDataToBackEnd: " + jsonObjectRequest);
//                requestQueue.add(jsonObjectRequest);
//                }
//
//private void getAllPendingRecords() {
//        if (!database.superVisorDao().getAll().isEmpty()) {
//        list.addAll(database.superVisorDao().getAll());
//        //  second();
//
//        Toast.makeText(this, "Internal Server Error ", Toast.LENGTH_SHORT).show();
//        } else {
//        showMsg("No Pending Records found for syncing");
//        }
//        }
//
//private void showMsg(String msg) {
//        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
//        }
//
////
////Direct Clock in
//private void directClockinAction(LocationModel model) {
//        Log.i(TAG, "directClockinAction: currentlocation = " + model.getGeoLat() + " , " + model.getGeoLon());
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
//        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//        if (geoFenceReq.equals("Y")) {
//        Log.i(TAG, "directClockinAction: geoArrStr = " + geoArrStr);
//        //check if distance between the given coordinates and the current geocodes is greater than the radius
//        //if yes return and show not inside the geofence
//        boolean inRange = false;
//        String[] geoArr = geoArrStr.split("-");
//        Log.i(TAG, "directClockinAction: geoArr length : " + geoArr.length);
//        for (int i = 0; i < geoArr.length; i++) {
//        String geoObjStr = geoArr[i];
//        Log.i(TAG, "directClockinAction: geoObjStr" + geoObjStr);
//        String[] geoItemArr = geoObjStr.split("\\|");
//        Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[0]);
//        Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[1]);
//        Log.i(TAG, "directClockinAction: geoItemArr" + i + "->" + geoItemArr[2]);
//
//        double desLat = Double.parseDouble(geoItemArr[0]);
//        double desLon = Double.parseDouble(geoItemArr[1]);
//        double rad = Double.parseDouble(geoItemArr[2]);
//        double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
//        double disInMetre = mDistance * 1000;
//        Log.i(TAG, "directClockinAction: disInMetre -> " + disInMetre);
//        if (disInMetre <= rad) {
//        inRange = true;
//        break;
//        }
//        }
//        if (!inRange) {
//        Toast.makeText(SupervisorMainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
//        return;
//        }
//        }
//        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("taTxnCompKey", String.valueOf(companyKey));
//        postParam.put("taEmpCode", String.valueOf(currEmpCode));//
//        postParam.put("taTxnDate", new GeneralUtils().getMonthInMMM(dateStr));
//        postParam.put("taTime", new GeneralUtils().getTimeInHHMMFormat(timeStr));
//        postParam.put("taTimeZone", new GeneralUtils().getCurrentTimeZone());
//        postParam.put("taAttendIndicator", "0");
//        postParam.put("taEmployeeImageTemp", encodedImageString.trim());//empModel.getImageEncoded()
//        postParam.put("taGeoLatitude", String.valueOf(model.getGeoLat()));
//        postParam.put("taGeoLangtitude", String.valueOf(model.getGeoLon()));
//        postParam.put("taGeoLocationName", model.getGeoLocationName());
//        postParam.put("createdUser", String.valueOf(currentUserKey));
//        postParam.put("taAutoManual", "A");
//        postParam.put("taSyncStatus", "N");
//        postParam.put("taSource", "Mobile");
//        postParam.put("taEmployeeImage", "");
//        postParam.put("taDeviceName", Build.MANUFACTURER + " " + Build.MODEL);
//        postParam.put("taDeviceOperatingSystem", "Andriod " + Build.VERSION.RELEASE);
//        postParam.put("taStatus", "N");
//        postParam.put("taErrorDesc", "TEST");
//
//        progressDialog = ProgressDialog.show(SupervisorMainActivity.this, "Please wait...", "Processing Clock In...");
//        Log.i(TAG, "syncDataToBackEnd: " + postParam.toString());
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, saveRecBySupervisorUrl, new JSONObject(postParam), new Response.Listener<JSONObject>() {
//@Override
//public void onResponse(JSONObject response) {
//        progressDialog.dismiss();
//        try {
//        Log.e("clockin", response.getString("message"));
//        JSONObject jsonObject = response;
//        String status = jsonObject.getString("keyword");
//        if (status.equals("success")) {
//        Toast.makeText(SupervisorMainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//        encodedImageString = null;
//        currImgView = null;
//        currEmpCode = null;
//        } else {
//        progressDialog.dismiss();
//        Toast.makeText(SupervisorMainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onResponse: failed to sync : " + jsonObject.getString("message"));
//        }
//        } catch (JSONException e) {
//        e.printStackTrace();
//        progressDialog.dismiss();
//        Log.i(TAG, "onResponse: JSONException : " + e.getMessage());
//        Toast.makeText(SupervisorMainActivity.this, R.string.common_error + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        }
//        }, new Response.ErrorListener() {
//@Override
//public void onErrorResponse(VolleyError error) {
//        progressDialog.dismiss();
//        Toast.makeText(SupervisorMainActivity.this, R.string.common_error, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onErrorResponse: " + error.getMessage());
//        }
//        }) {
//
///**
// * Passing some request headers
// * */
//@Override
//public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("x-auth-token", token);
//        //headers.put("Content-Type", "application/json; charset=utf-8");
//        return headers;
//        }
//        };
//        RequestQueue queue = Volley.newRequestQueue(SupervisorMainActivity.this);
//        queue.add(jsonObjectRequest);
//
//
//        }
//
////
////Direct Clock out
//private void directClockoutAction(LocationModel model) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String dateStr = date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900);
//        String timeStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
//        if (geoFenceReq.equals("Y")) {
//        Log.i(TAG, "directClockoutAction: geoArrStr = " + geoArrStr);
//        //check if distance between the given coordinates and the current geocodes is greater than the radius
//        //if yes return and show not inside the geofence
//        boolean inRange = false;
//        String[] geoArr = geoArrStr.split("-");
//        Log.i(TAG, "directClockoutAction: geoArr length : " + geoArr.length);
//        for (int i = 0; i < geoArr.length; i++) {
//        String geoObjStr = geoArr[i];
//        Log.i(TAG, "directClockoutAction: geoObjStr" + geoObjStr);
//        String[] geoItemArr = geoObjStr.split("\\|");
//        Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[0]);
//        Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[1]);
//        Log.i(TAG, "directClockoutAction: geoItemArr" + i + "->" + geoItemArr[2]);
//
//        double desLat = Double.parseDouble(geoItemArr[0]);
//        double desLon = Double.parseDouble(geoItemArr[1]);
//        double rad = Double.parseDouble(geoItemArr[2]);
//        double mDistance = GeneralUtils.getDistance(model.getGeoLat(), model.getGeoLon(), desLat, desLon);
//        double disInMetre = mDistance * 1000;
//        Log.i(TAG, "directClockoutAction: disInMetre -> " + disInMetre);
//        if (disInMetre <= rad) {
//        inRange = true;
//        break;
//        }
//        }
//        if (!inRange) {
//        Toast.makeText(SupervisorMainActivity.this, "You are out of allowed geofences!", Toast.LENGTH_SHORT).show();
//        return;
//        }
//
//        }
//        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("taTxnCompKey", String.valueOf(companyKey));
//        postParam.put("taEmpCode", String.valueOf(currEmpCode));//
//        postParam.put("taTxnDate", new GeneralUtils().getMonthInMMM(dateStr));
//        postParam.put("taTime", new GeneralUtils().getTimeInHHMMFormat(timeStr));
//        postParam.put("taTimeZone", new GeneralUtils().getCurrentTimeZone());
//        postParam.put("taAttendIndicator", "1");
//        postParam.put("taEmployeeImageTemp", encodedImageString);//empModel.getImageEncodedClockOut()
//        postParam.put("taGeoLatitude", String.valueOf(model.getGeoLat()));
//        postParam.put("taGeoLangtitude", String.valueOf(model.getGeoLon()));
//        postParam.put("taGeoLocationName", model.getGeoLocationName());
//        postParam.put("createdUser", String.valueOf(currentUserKey));
//        postParam.put("taAutoManual", "A");
//        postParam.put("taSyncStatus", "N");
//        postParam.put("taSource", "Mobile");
//        postParam.put("taEmployeeImage", "");
//        postParam.put("taDeviceName", Build.MANUFACTURER + " " + Build.MODEL);
//        postParam.put("taDeviceOperatingSystem", "Andriod " + Build.VERSION.RELEASE);
//        postParam.put("taStatus", "N");
//        postParam.put("taErrorDesc", "TEST");
//
//        progressDialog = ProgressDialog.show(SupervisorMainActivity.this, "Please wait...", "Processing Clock Out...");
//        Log.i(TAG, "syncDataToBackEnd: " + postParam.toString());
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, saveRecBySupervisorUrl, new JSONObject(postParam), new Response.Listener<JSONObject>() {
//@Override
//public void onResponse(JSONObject response) {
//        progressDialog.dismiss();
//
//        try {
//        Log.e("clockout", response.getString("message"));
//        JSONObject jsonObject = response;
//        String status = jsonObject.getString("keyword");
//        if (status.equals("success")) {
//        Toast.makeText(SupervisorMainActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//        encodedImageString = null;
//        currImgView = null;
//        currEmpCode = null;
//
//        } else {
//        progressDialog.dismiss();
//        Toast.makeText(SupervisorMainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onResponse: failed to sync : " + jsonObject.getString("message"));
//        }
//        } catch (JSONException e) {
//        e.printStackTrace();
//        progressDialog.dismiss();
//        Log.i(TAG, "onResponse: JSONException : " + e.getMessage());
//        Toast.makeText(SupervisorMainActivity.this, R.string.common_error + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        }
//        }, new Response.ErrorListener() {
//@Override
//public void onErrorResponse(VolleyError error) {
//        Toast.makeText(SupervisorMainActivity.this, R.string.common_error, Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "onErrorResponse: " + error.getMessage());
//        }
//        }) {
//
///**
// * Passing some request headers
// */
//@Override
//public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("x-auth-token", token);
//        //headers.put("Content-Type", "application/json; charset=utf-8");
//        return headers;
//        }
//        };
//        RequestQueue queue = Volley.newRequestQueue(SupervisorMainActivity.this);
//
//        queue.add(jsonObjectRequest);
//        }
//
//public void second() {
//        SuperData superData = new SuperData();
//        List<DataItem> dataItemList = new ArrayList<>();
//
//        JSONArray jsonArray = new JSONArray();
//
//        for (int i = 0; i < list.size(); i++) {
//        Supervisor empModel = list.get(i);
//        DataItem dataItem = new DataItem();
//        dataItem.setTaTxnCompKey(companyKey);
//        dataItem.setTaEmpCode(empModel.getEmpCode());
//        if (!empModel.getClockinGeoDate().equals("null"))//check if clock in data
//        {
//
//        dataItem.setTaTxnDate(new GeneralUtils().getMonthInMMM(empModel.getClockinGeoDate()));
//        dataItem.setTaTime(new GeneralUtils().getTimeInHHMMFormat(empModel.getClockinGeoTime()));
//        dataItem.setTaAttendIndicator("0");
//        dataItem.setTaEmployeeImageTemp("empModel.getImageEncoded()");
//        dataItem.setTaGeoLatitude(empModel.getClockinGeoLat());
//        dataItem.setTaGeoLangtitude(empModel.getClockinGeoLon());
//        dataItem.setTaGeoLocationName(empModel.getClockinGeoName());
//        } else {
//        dataItem.setTaTxnDate(new GeneralUtils().getMonthInMMM(empModel.getClockoutGeoDate()));
//        dataItem.setTaTime(new GeneralUtils().getTimeInHHMMFormat(empModel.getClockoutGeoTime()));
//        dataItem.setTaAttendIndicator("1");
//        dataItem.setTaEmployeeImageTemp("empModel.getImageEncodedClockOut()");
//        dataItem.setTaGeoLatitude(empModel.getClockoutGeoLat());
//        dataItem.setTaGeoLangtitude(empModel.getClockoutGeoLon());
//        dataItem.setTaGeoLocationName(empModel.getClockoutGeoName());
//        dataItem.setCreatedUser(currentUserKey);
//        dataItem.setTaAutoManual("A");
//        dataItem.setTaSyncStatus("N");
//        dataItem.setTaSource("Mobile");
//        dataItem.setTaDeviceName(Build.MANUFACTURER + " " + Build.MODEL);
//        dataItem.setTaDeviceOperatingSystem("Andriod " + Build.VERSION.RELEASE);
//        dataItem.setTaStatus("N");
//        dataItem.setTaErrorDesc("TEST");
//        dataItemList.add(dataItem);
//        }
//        superData.setData(dataItemList);
//        progressDialog = ProgressDialog.show(SupervisorMainActivity.this, "", "Please wait...");
//        Log.i(TAG, "syncDataToBackEnd: " + superData.toString());
//        MainService.officeUpload(this, superData).observe(this, responseLogin -> {
//        if (responseLogin == null) {
//        Toast.makeText(this, "E", Toast.LENGTH_SHORT).show();
//        } else {
//        if (!(responseLogin.getData() instanceof JsonNull)) {
//        if (responseLogin.getData() != null) {
//        Toast.makeText(this, "Effdg", Toast.LENGTH_SHORT).show();
//        } else {
//        Toast.makeText(this, "Egdfsgdfgfd", Toast.LENGTH_SHORT).show();
//        }
//        } else {
//        Toast.makeText(this, "Egsfdgfdgdfgdg", Toast.LENGTH_SHORT).show();
//        }
//        }
//        });
//        }
//        }
//
//private void updateAndroidSecurityProvider() { try {
//        ProviderInstaller.installIfNeeded(this);
//        } catch (Exception e) { e.getMessage(); } }
//
//

}
