package com.amtechsolutions.becpbas.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.activity.EmployeeRecordViewer;
import com.amtechsolutions.becpbas.db.AppDatabase;
import com.amtechsolutions.becpbas.db.Supervisor;

import java.util.List;

/**
 * Created by Shourav Paul on 12-03-2022.
 **/
public class SupervisorAdapter extends RecyclerView.Adapter<SupervisorAdapter.ViewHolder> {

    private Activity context;
    private List<Supervisor> list;
    private AppDatabase database;

    public SupervisorAdapter(Activity context, List<Supervisor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.emp_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Supervisor model = list.get(position);
        holder.chckInGeoLocTxt.setText(model.getClockinGeoName());
        holder.chckOutGeoLocTxt.setText(model.getClockoutGeoName());
        holder.chckInDateTxt.setText(model.getClockinGeoDate());
        holder.chckOutDateTxt.setText(model.getClockoutGeoDate());
        if(!model.getClockinGeoName().equals("null"))
        {
            holder.clockTitleTxt.setText("CLOCK IN");
            holder.clockinValues.setVisibility(View.VISIBLE);
            holder.clockoutvalues.setVisibility(View.GONE);
        }
        else
        {
            holder.clockTitleTxt.setText("CLOCK OUT");
            holder.clockinValues.setVisibility(View.GONE);
            holder.clockoutvalues.setVisibility(View.VISIBLE);
        }
        holder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmployeeRecordViewer.class);
                intent.putExtra("emp_code", model.getEmpCode());
                intent.putExtra("clock_in_geo_name", model.getClockinGeoName());
                intent.putExtra("clock_in_geo_lat", model.getClockinGeoLat());
                intent.putExtra("clock_in_geo_lon", model.getClockinGeoLon());
                intent.putExtra("clock_in_geo_date", model.getClockinGeoDate());
                intent.putExtra("clock_in_geo_time", model.getClockinGeoTime());
                intent.putExtra("clock_out_geo_name", model.getClockoutGeoName());
                intent.putExtra("clock_out_geo_lat", model.getClockoutGeoLat());
                intent.putExtra("clock_out_geo_lon", model.getClockoutGeoLon());
                intent.putExtra("clock_out_geo_date", model.getClockoutGeoDate());
                intent.putExtra("clock_out_geo_time", model.getClockoutGeoTime());
                intent.putExtra("img_uri", model.getImageUri());
                intent.putExtra("img_encoded", model.getImageEncoded());
                intent.putExtra("img_encoded_clckout", model.getImageEncodedClockOut());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView chckInGeoLocTxt, chckOutGeoLocTxt, chckInDateTxt, chckOutDateTxt, clockTitleTxt;
        private LinearLayout clockinValues, clockoutvalues;
        private LinearLayout clickLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clockTitleTxt = (TextView)itemView.findViewById(R.id.clck_title);
            chckInGeoLocTxt = (TextView)itemView.findViewById(R.id.chck_in_geo_loc_txt);
            chckOutGeoLocTxt = (TextView)itemView.findViewById(R.id.chck_out_geo_loc_txt);
            chckInDateTxt = (TextView)itemView.findViewById(R.id.chck_in_date_txt);
            chckOutDateTxt = (TextView)itemView.findViewById(R.id.chck_out_date_txt);
            clickLayout = (LinearLayout)itemView.findViewById(R.id.click_layout);
            clockinValues = (LinearLayout)itemView.findViewById(R.id.clockin_values);
            clockoutvalues = (LinearLayout)itemView.findViewById(R.id.clockout_values);
        }
    }
}
