package com.amtechsolutions.becpbas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amtechsolutions.becpbas.PermissionActivity;
import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.utils.HttpsTrustManager;
import com.amtechsolutions.becpbas.utils.MyStringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import static com.amtechsolutions.becpbas.utils.AppConstants.validateUrl;

public class NewLoginActivity extends AppCompatActivity {

    private static final String TAG = "NewLoginActivity";
    private AppCompatButton loginBtn;
    private EditText userNameEdTxt, passwordEdTxt;
    private String usernameStr, passwordStr;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        initView();
        setListener();
    }
    private void initView()
    {
        loginBtn = (AppCompatButton)findViewById(R.id.super_login_btn);
        userNameEdTxt = (EditText)findViewById(R.id.username_edtxt);
        passwordEdTxt = (EditText)findViewById(R.id.pass_edtxt);

    }
    private void setListener()
    {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    loginUser();
                    //validateUser();
                }
            }
        });
    }
    private boolean validate()
    {
        if(userNameEdTxt.getText().toString().isEmpty())
        {
            showMsg("Enter Username!");
            return false;
        }
        else if(passwordEdTxt.getText().toString().isEmpty())
        {
            showMsg("Enter Password!");
            return false;
        }
        else
        {
            usernameStr = userNameEdTxt.getText().toString().trim();
            passwordStr = passwordEdTxt.getText().toString().trim();
//                    if(userNameEdTxt.getText().toString().trim().substring(0,1).toLowerCase().equals("s"))
//                    {
//                        startSupervisorDashBoard(userNameEdTxt.getText().toString().trim());
//                    }
//                    else if(userNameEdTxt.getText().toString().trim().substring(0,1).toLowerCase().equals("e"))
//                    {
//                        startEmployeeDashBoard(userNameEdTxt.getText().toString().trim());
//                    }
//                    else
//                    {
//                        Toast.makeText(NewLoginActivity.this, "Invalid User Id!", Toast.LENGTH_SHORT).show();
//                    }

        }
        return true;
    }
    private void startEmployeeDashBoard()
    {
        startActivity(new Intent(NewLoginActivity.this, MainActivity.class));
        finish();
    }
    private void startSupervisorDashBoard()
    {
        startActivity(new Intent(NewLoginActivity.this, PermissionActivity.class));
        finish();
    }
//    private void validateUser()
//    {
//        progressDialog = ProgressDialog.show(NewLoginActivity.this, "", "Please wait...");
//        StringRequest request = new StringRequest(Request.Method.POST, validateUrl+"username="+usernameStr+"&password="+passwordStr+"&fromAp=ios", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                String res = response.trim();
//                Log.i(TAG, "onResponse: "+res);
//                try {
//                    JSONObject jsonObject = new JSONObject(res);
//                    boolean status = jsonObject.getBoolean("status");
//                    if(status)
//                    {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                    }
//                    else
//                    {
//                        //no record found
//                        Toast.makeText(NewLoginActivity.this, "No Record Found!", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.i(TAG, "json exception : "+e.getMessage());
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i(TAG, "onErrorResponse: "+error.getMessage());
//            }
//        });
//        RequestQueue queue = Volley.newRequestQueue(NewLoginActivity.this);
//        queue.add(request);
//    }
    private void loginUser()
    {
        HttpsTrustManager.allowAllSSL();
        progressDialog = ProgressDialog.show(NewLoginActivity.this, "Please wait...", "Verifying...");
        MyStringRequest request = new MyStringRequest(Request.Method.POST, validateUrl + "username=" + usernameStr + "&password=" + passwordStr + "&fromAp=ios", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String s = response.trim();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.optString("keyword");
                    if(status.equals("success"))
                    {
                        progressDialog.dismiss();
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                        currentUserKey = jsonObject1.getInt("currentUserKey");
                        //currentEmpKey = jsonObject1.getInt("currentEmpKey");
                        currentUsername = jsonObject1.getString("userName");
                        currentEmpCode = jsonObject1.optString("empCode");
                        userType = jsonObject1.getString("userType");
                        companyKey = jsonObject1.getInt("companyKey");
                        token = jsonObject.getString("header");
                        Log.e("token",token);
                        if (jsonObject1.has("paramMobileData"))
                        {
                            try {
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("paramMobileData");
                                geoFenceReq = jsonObject2.getString("geoFenceReqYN");
                                capPhotoMand = jsonObject2.getString("capPhotoMandYN");
                                captureMode = jsonObject2.getString("captureMode");
                            }catch (Exception e){

                            }
                        }
                        //geo fence data
                        if (jsonObject1.has("geoFenceData"))
                        {
                            try {
                                JSONArray geoFenceArr = jsonObject1.getJSONArray("geoFenceData");
                                for(int i = 0; i < geoFenceArr.length(); i++)
                                {
                                    JSONObject jsonObj = geoFenceArr.getJSONObject(i);
                                    int geoLat = jsonObj.getInt("gfLatitude");
                                    int geoLon = jsonObj.getInt("gfLangtitude");
                                    int geoRad = jsonObj.getInt("gfRadius");
                                    String geoObjStr = geoLat+"|"+geoLon+"|"+geoRad+"-";
                                    geoArrStr = geoArrStr+geoObjStr;
                                }
                            }catch (Exception e){

                            }
                        }

                        if(userType.equals("S"))
                        {
                            saveInSharedPref("2");
                            startEmployeeDashBoard();
                        }
                        else if(userType.equals("O"))
                        {
                            saveInSharedPref("1");
                            startSupervisorDashBoard();
//                            saveInSharedPref("2");
//                            startEmployeeDashBoard();
                        }
                        else
                        {
//                            saveInSharedPref("2");
//                            startEmployeeDashBoard();
//                            saveInSharedPref("1");
//                            startSupervisorDashBoard();
                            showMsg("You are not authorized to login!");
                        }

                        Log.i(TAG, "onResponse: login Response -> "+response);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        showMsg("Incorrect Username/Password!");
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    showMsg("Something Went Wrong!");
                    Log.i(TAG, "onResponse: JSONException : "+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                showMsg("Something Went Wrong!");
                Log.i(TAG, "onErrorResponse: "+error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(NewLoginActivity.this);
        queue.add(request);
    }

    private void showMsg(String msg)
    {
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }
    private void saveInSharedPref(String loginType)
    {
        SharedPreferences pref = getSharedPreferences("BecGroupApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("login", loginType);
        editor.putInt("currUserKey", currentUserKey);
        editor.putInt("currEmpKey", currentEmpKey);
        editor.putString("currUserName", currentUsername);
        editor.putString("currEmpCode", currentEmpCode);
        editor.putString("usrType", userType);
        editor.putInt("compKey", companyKey);
        editor.putString("tokn", token);
        editor.putString("geoarrstr", geoArrStr);
        editor.putString("geofencereq", geoFenceReq);
        editor.putString("capphotomand", capPhotoMand);
        editor.putString("capturemode", captureMode);
        editor.apply();
    }
}