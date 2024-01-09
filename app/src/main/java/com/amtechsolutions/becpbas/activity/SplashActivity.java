package com.amtechsolutions.becpbas.activity;

import static com.amtechsolutions.becpbas.utils.AppConstants.capPhotoMand;
import static com.amtechsolutions.becpbas.utils.AppConstants.captureMode;
import static com.amtechsolutions.becpbas.utils.AppConstants.companyKey;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentEmpCode;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentEmpKey;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentUserKey;
import static com.amtechsolutions.becpbas.utils.AppConstants.currentUsername;
import static com.amtechsolutions.becpbas.utils.AppConstants.geoArrStr;
import static com.amtechsolutions.becpbas.utils.AppConstants.geoFenceReq;
import static com.amtechsolutions.becpbas.utils.AppConstants.token;
import static com.amtechsolutions.becpbas.utils.AppConstants.userType;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.amtechsolutions.becpbas.BuildConfig;
import com.amtechsolutions.becpbas.PermissionActivity;
import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.utils.GeneralInternet;

import org.jsoup.Jsoup;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    Boolean GPS = false, LOCATION = false, CAMERA = false;
    private Dialog dialog;
    int SPLASH_DISPLAY_LENGTH = 3;
    private String mCurrentVersion, mLatestVersion;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initDialog();
        if (checkIfLoggedIn() != null) {
            if (checkIfLoggedIn().equals("1")) {
                startDashBoard();
            } else if (checkIfLoggedIn().equals("2")) {
                startDashBoard();
            } else {
                if (checkInternet()) {
                    //Get latest version from playstore
                    dialog.dismiss();
                    //new GetLatestVersion().execute();//TODO: uncomment later
                    startDashBoard();
                } else {
                    dialog.show();
                }
            }
        } else {
            if (checkInternet()) {
                //Get latest version from playstore
                dialog.dismiss();
                //new GetLatestVersion().execute();//TODO: uncomment later
                startDashBoard();
            } else {
                dialog.show();
            }
        }

    }

    public void initDialog() {
        dialog = new Dialog(SplashActivity.this);
        dialog.setContentView(R.layout.no_internet_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.no_internet_bg));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button btnTryAgain = dialog.findViewById(R.id.btn_try);
        Button btnExit = dialog.findViewById(R.id.btn_exit);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInternet()) {
                    //Get latest version from playstore
                    dialog.dismiss();
                    startDashBoard();
                    //new GetLatestVersion().execute();//TODO: uncomment later
                } else {
                    dialog.show();
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private String checkIfLoggedIn() {
        SharedPreferences pref = getSharedPreferences("BecGroupApp", Context.MODE_PRIVATE);
        String isLogin = pref.getString("login", null);
        return isLogin;
    }

    public void startDashBoard() {

        new Handler().postDelayed(() -> {
            SharedPreferences pref = getSharedPreferences("BecGroupApp", Context.MODE_PRIVATE);
            String isLogin = pref.getString("login", null);
            if (isLogin != null) {
                if (isLogin.equals("1")) {
                    //patientID = pref.getString("doctorId", null);
                    currentUserKey = pref.getInt("currUserKey", 0);
                    currentEmpKey = pref.getInt("currEmpKey", 0);
                    currentUsername = pref.getString("currUserName", null);
                    currentEmpCode = pref.getString("currEmpCode", null);
                    userType = pref.getString("usrType", null);
                    companyKey = pref.getInt("compKey", 0);
                    token = pref.getString("tokn", null);
                    geoArrStr = pref.getString("geoarrstr", null);
//                    geoArrStr = "27.1589322|91.9357649|600-26.1499322|91.7357649|600-";
                    geoFenceReq = pref.getString("geofencereq", null);
                    capPhotoMand = pref.getString("capphotomand", null);
                    captureMode = pref.getString("capturemode", null);
                    if (ContextCompat.checkSelfPermission(SplashActivity.this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        CAMERA = true;
                    }

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

                    GPS = isLocationEnabled(this);
                    if (CAMERA && LOCATION && GPS) {
                        finish();
                        startActivity(new Intent(SplashActivity.this, SupervisorMainActivity.class));
                    } else {
                        finish();
                        startActivity(new Intent(SplashActivity.this, PermissionActivity.class));
                    }

                } else if (isLogin.equals("2")) {
                    currentUserKey = pref.getInt("currUserKey", 0);
                    currentEmpKey = pref.getInt("currEmpKey", 0);
                    currentUsername = pref.getString("currUserName", null);
                    currentEmpCode = pref.getString("currEmpCode", null);
                    userType = pref.getString("usrType", null);
                    companyKey = pref.getInt("compKey", 0);
                    token = pref.getString("tokn", null);
                    geoArrStr = pref.getString("geoarrstr", null);
                    geoFenceReq = pref.getString("geofencereq", null);
                    capPhotoMand = pref.getString("capphotomand", null);
                    captureMode = pref.getString("capturemode", null);
                    Log.i(TAG, "startDashBoard: currentEmpCode = " + currentEmpCode + " currentUsername = " + currentUsername);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, NewLoginActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, NewLoginActivity.class));
            }
            finish();
        }, 5000);
    }

    public boolean checkInternet() {
        if (GeneralInternet.checkInternet(this)) {
            return true;
        }
        return false;
    }

    public void updateAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Please Update to Continue");
        builder.setCancelable(false);

        //
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //open playstore
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }
        });
        builder.show();
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

    private class GetLatestVersion extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                mLatestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName())
                        .timeout(30000)
                        .get()
                        .select("div.hAyfc:nth-child(4)>" +
                                "span:nth-child(2) > div:nth-child(1)" +
                                "> span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mLatestVersion;
        }

        @Override
        protected void onPostExecute(String s) {
            //Get current version
            mCurrentVersion = BuildConfig.VERSION_NAME;
            if (mLatestVersion != null) {
                float cVersion = Float.parseFloat(mCurrentVersion);
                float lVersion = Float.parseFloat(mLatestVersion);
                if (lVersion > cVersion) {
                    updateAlertDialog();
                } else {
                    startDashBoard();
                    Toast.makeText(SplashActivity.this, "Start Dashboard", Toast.LENGTH_SHORT).show();


                }

            }

        }
    }
}