package com.example.dhavaldesai.myapplication;

import android.app.Fragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dhavaldesai on 8/12/16.
 */

public class RegisterFragment extends Fragment {


    private EditText input_username;
    private EditText input_register_emailid;
    private Button btn_register;
    Activity activity;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        input_username=(EditText)getView().findViewById(R.id.input_username);
        input_register_emailid=(EditText)getView().findViewById(R.id.input_register_emailid);
        btn_register=(Button)getView().findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OTPFragment otpf = new OTPFragment();
                    ((Handler1)activity).exchange(otpf);
                }
                catch(ClassCastException cce){

                }
            }
        });
    }

    public interface Handler1{
        public void exchange(Fragment frag);
    }
}