package com.example.dhavaldesai.myapplication;

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


public class SigninFragment extends Fragment {

    private static final String TAG = "SigninFragment";
    private static final int REQUEST_SIGNUP = 0;
    private EditText input_email;
    private EditText input_password;
    private TextView link_signup;
    Activity activity;
    signinHandler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            handler = (signinHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement signinHandler");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        input_email=(EditText)getView().findViewById(R.id.input_email);
        input_password=(EditText)getView().findViewById(R.id.input_password);
        link_signup=(TextView)getView().findViewById(R.id.link_signup);

        link_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    RegisterFragment rf = new RegisterFragment();
                    ((signinHandler)activity).signinexchange();
                }
                catch(ClassCastException cce){

                }
            }
        });



    }

    public interface signinHandler{
        public void signinexchange();
    }





    //DHAVAL DESAI My CODE
}
