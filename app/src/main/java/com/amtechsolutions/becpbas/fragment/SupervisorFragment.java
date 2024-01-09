package com.amtechsolutions.becpbas.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.activity.SupervisorMainActivity;

/**
 * Created by Shourav Paul on 09-03-2022.
 **/
public class SupervisorFragment extends Fragment {

    private static final String TAG = "SupervisorFragment";
    private Context mContext;
    private AppCompatButton loginBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supervisor, container, false);
        initView(view);
        setListener();
        return view;
    }
    private void initView(View view)
    {
        loginBtn = (AppCompatButton)view.findViewById(R.id.super_login_btn);

    }
    private void setListener()
    {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDashBoard();
            }
        });
    }
    private void startDashBoard()
    {
        startActivity(new Intent(mContext, SupervisorMainActivity.class));
        ((Activity)mContext).finish();
    }

}
