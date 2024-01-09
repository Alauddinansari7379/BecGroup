package com.amtechsolutions.becpbas;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.amtechsolutions.becpbas.activity.SupervisorMainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class PermissionActivity extends AppCompatActivity {
    Boolean GPS = false, LOCATION = false, CAMERA = false;
    public static final int LOCATION_RESULT = 1001;
    public static final int GPS_RESULT = 1002;
    public static final int CAMERA_RESULT = 1003;
    TextView locationBtn, gpsBtn, cameraBtn;
    ImageView locationCheck, gpsCheck, cameraCheck;
    CountDownTimer f;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        locationBtn = findViewById(R.id.locationBtn);
        gpsBtn = findViewById(R.id.gpsBtn);
        cameraBtn = findViewById(R.id.cameraBtn);
        locationCheck = findViewById(R.id.locationCheck);
        gpsCheck = findViewById(R.id.gpsCheck);
        cameraCheck = findViewById(R.id.cameraCheck);

        GPS = isLocationEnabled(this);
        checkLocationPermission();
        cameraBtn.setOnClickListener(v -> {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    LOCATION_RESULT);
        });
        gpsBtn.setOnClickListener(v -> {
            displayLocationSettingsRequest(this);
        });
        locationBtn.setOnClickListener(v -> {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_RESULT);
        });
        long maxCounter = 30000;
        long diff = 1;
        checkValidation();
        f = new CountDownTimer(maxCounter, diff) {
            public void onTick(long millisUntilFinished) {
                checkLocationPermissionState(PermissionActivity.this);
                if (ContextCompat.checkSelfPermission(PermissionActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    CAMERA = true;
                } else {
                    CAMERA = false;
                }
                checkValidation();
                if (CAMERA && LOCATION && GPS) {
                    f.cancel();
                    f.onFinish();

                }
            }

            public void onFinish() {
                finish();
                startActivity(new Intent(PermissionActivity.this, SupervisorMainActivity.class));
            }

        }.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkLocationPermissionState(this);
        if (requestCode == LOCATION_RESULT && resultCode == RESULT_OK) {

        } else if (requestCode == GPS_RESULT && resultCode == RESULT_OK) {
            GPS = isLocationEnabled(this);
        }
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                    LOCATION_RESULT);
            LOCATION = false;
        } else {
            LOCATION = true;
        }
    }

    private void checkLocationPermissionState(Context context) {
        int fineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            int bgLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            boolean isAppLocationPermissionGranted = (bgLocation == PackageManager.PERMISSION_GRANTED) &&
                    (coarseLocation == PackageManager.PERMISSION_GRANTED);
            boolean preciseLocationAllowed = (fineLocation == PackageManager.PERMISSION_GRANTED)
                    && (coarseLocation == PackageManager.PERMISSION_GRANTED);
            if (preciseLocationAllowed) {
                Log.d("PERMISSIONPERMISSION", "Precise location is enabled in Android 12");
                LOCATION = true;
            } else {
                LOCATION = false;
                Log.d("PERMISSIONPERMISSION", "Precise location is disabled in Android 12");
            }

            if (isAppLocationPermissionGranted) {
                LOCATION = true;
                Log.d("PERMISSIONPERMISSION", "Location is allowed all the time");
            } else if (coarseLocation == PackageManager.PERMISSION_GRANTED) {
                LOCATION = true;
                Log.d("PERMISSIONPERMISSION", "Location is allowed while using the app");
            } else {
                LOCATION = false;
                Log.d("PERMISSIONPERMISSION", "Location is not allowed.");
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            int bgLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

            boolean isAppLocationPermissionGranted = (bgLocation == PackageManager.PERMISSION_GRANTED) &&
                    (coarseLocation == PackageManager.PERMISSION_GRANTED);

            if (isAppLocationPermissionGranted) {
                LOCATION = true;
                Log.d("PERMISSIONPERMISSION", "Location is allowed all the time");
            } else if (coarseLocation == PackageManager.PERMISSION_GRANTED) {
                LOCATION = true;
                Log.d("PERMISSIONPERMISSION", "Location is allowed while using the app");
            } else {
                LOCATION = false;
                Log.d("PERMISSIONPERMISSION", "Location is not allowed.");
            }

        } else {

            boolean isAppLocationPermissionGranted = (fineLocation == PackageManager.PERMISSION_GRANTED) &&
                    (coarseLocation == PackageManager.PERMISSION_GRANTED);

            if (isAppLocationPermissionGranted) {
                LOCATION = true;
                Log.d("PERMISSIONPERMISSION", "Location permission is granted");
            } else {
                LOCATION = false;
                Log.d("PERMISSIONPERMISSION", "Location permission is not granted");
            }
        }
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            status.startResolutionForResult(PermissionActivity.this, GPS_RESULT);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    public void checkValidation() {
        if (isLocationEnabled(this)) {
            gpsBtn.setVisibility(View.GONE);
            gpsCheck.setVisibility(View.VISIBLE);
        } else {
            gpsBtn.setVisibility(View.VISIBLE);
            gpsCheck.setVisibility(View.GONE);
        }
        if (LOCATION) {
            locationBtn.setVisibility(View.GONE);
            locationCheck.setVisibility(View.VISIBLE);
        } else {
            locationBtn.setVisibility(View.VISIBLE);
            locationCheck.setVisibility(View.GONE);
        }

        if (CAMERA) {
            cameraBtn.setVisibility(View.GONE);
            cameraCheck.setVisibility(View.VISIBLE);
        } else {
            cameraBtn.setVisibility(View.VISIBLE);
            cameraCheck.setVisibility(View.GONE);
        }

    }
}