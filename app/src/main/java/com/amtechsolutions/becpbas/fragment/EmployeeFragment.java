package com.amtechsolutions.becpbas.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.amtechsolutions.becpbas.R;
import com.amtechsolutions.becpbas.activity.MainActivity;

/**
 * Created by Shourav Paul on 09-03-2022.
 **/
public class EmployeeFragment extends Fragment {

    private static final String TAG = "EmployeeFragment";
    private Context mContext;
    private AppCompatButton loginBtn;
    private EditText empCodeEdTxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        initView(view);
        setListener();
        return view;
    }
    private void initView(View view)
    {
        loginBtn = (AppCompatButton)view.findViewById(R.id.super_login_btn);
        empCodeEdTxt = (EditText)view.findViewById(R.id.empcode_edtxt);

    }
    private void setListener()
    {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empCodeEdTxt.getText().toString().isEmpty())
                {
                    Toast.makeText(mContext, "Enter Employee Code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startDashBoard(empCodeEdTxt.getText().toString().trim());
                }
            }
        });
    }
    private void startDashBoard(String empCode)
    {
        startActivity(new Intent(mContext, MainActivity.class).putExtra("emp_code", empCode));
        ((Activity)mContext).finish();
    }


}
