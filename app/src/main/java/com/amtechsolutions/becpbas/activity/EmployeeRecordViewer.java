package com.amtechsolutions.becpbas.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtechsolutions.becpbas.R;

public class EmployeeRecordViewer extends AppCompatActivity {

    private static final String TAG = "EmployeeRecordViewer";
    private TextView employeeCodeTxt, clockInGeoNameTxt, clockInGeoLatTxt, clockInGeoLonTxt, clockInGeoDateTxt, clockInGeoTimeTxt;
    private TextView clockOutGeoNameTxt, clockOutGeoLatTxt, clockOutGeoLonTxt, clockOutGeoDateTxt, clockOutGeoTimeTxt;
    private ImageView displayClockInImage, displayClockOutImage;
    private LinearLayout clockinBody, clockoutBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_record_viewer);
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
        actionBar.setTitle("Record Details");
        initView();
        setData(getIntent());
        if(!getIntent().getStringExtra("clock_in_geo_name").equals("null"))
        {
            clockinBody.setVisibility(View.VISIBLE);
            clockoutBody.setVisibility(View.GONE);
        }
        else
        {
            clockinBody.setVisibility(View.GONE);
            clockoutBody.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        employeeCodeTxt = (TextView)findViewById(R.id.emp_code_txt);
        clockInGeoNameTxt = (TextView)findViewById(R.id.chck_in_geo_loc_txt);
        clockInGeoLatTxt = (TextView)findViewById(R.id.chck_in_geo_code_txt);
        clockInGeoDateTxt = (TextView)findViewById(R.id.chck_in_date_txt);
        clockInGeoTimeTxt = (TextView)findViewById(R.id.chck_in_time_txt);
        clockOutGeoNameTxt = (TextView)findViewById(R.id.chck_out_geo_loc_txt);
        clockOutGeoLatTxt = (TextView)findViewById(R.id.chck_out_geo_code_txt);
        clockOutGeoDateTxt = (TextView)findViewById(R.id.chck_out_date_txt);
        clockOutGeoTimeTxt = (TextView)findViewById(R.id.chck_out_time_txt);
        displayClockInImage = (ImageView)findViewById(R.id.clockin_display_img);
        displayClockOutImage = (ImageView)findViewById(R.id.clockout_display_img);
        clockinBody = (LinearLayout)findViewById(R.id.clockin_body);
        clockoutBody = (LinearLayout)findViewById(R.id.clockout_body);
    }

    private void setData(Intent intent) {
        employeeCodeTxt.setText(intent.getStringExtra("emp_code"));
        clockInGeoNameTxt.setText(intent.getStringExtra("clock_in_geo_name"));
        clockInGeoLatTxt.setText(intent.getStringExtra("clock_in_geo_lat")+","+intent.getStringExtra("clock_in_geo_lon"));
        clockInGeoDateTxt.setText(intent.getStringExtra("clock_in_geo_date"));
        clockInGeoTimeTxt.setText(intent.getStringExtra("clock_in_geo_time"));
        clockOutGeoNameTxt.setText(intent.getStringExtra("clock_out_geo_name"));
        clockOutGeoLatTxt.setText(intent.getStringExtra("clock_out_geo_lat")+","+intent.getStringExtra("clock_out_geo_lon"));
        clockOutGeoDateTxt.setText(intent.getStringExtra("clock_out_geo_date"));
        clockOutGeoTimeTxt.setText(intent.getStringExtra("clock_out_geo_time"));
        Log.i(TAG, "setData: img_encoded: "+intent.getStringExtra("img_encoded"));
        Log.i(TAG, "setData: img_encoded_clckout: "+intent.getStringExtra("img_encoded_clckout"));
//        Uri uri = Uri.parse(intent.getStringExtra("img_uri"));
//        Picasso.get().load(uri).into(dispImg);
        if(!intent.getStringExtra("img_encoded").equals("null"))
        {
            decodeToBitmap(intent.getStringExtra("img_encoded"), true);
        }
        else
        {
            decodeToBitmap(intent.getStringExtra("img_encoded_clckout"), false);
        }



    }
    private void decodeToBitmap(String encodeStr, boolean clckin)
    {
        //Decode base64 string
        byte[] bytes = Base64.decode(encodeStr, Base64.DEFAULT);
        //initialize bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if(clckin)
        {
            displayClockInImage.setImageBitmap(bitmap);
        }
        else
        {
            displayClockOutImage.setImageBitmap(bitmap);
        }

    }
}