package com.example.dhavaldesai.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements SigninFragment.signinHandler{

    SigninFragment signinFragment;
    RegisterFragment rf ;
    OTPFragment otpf =new OTPFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            signinFragment = new SigninFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, signinFragment).commit();
        }

    }

    public void signinexchange(){
        rf=new RegisterFragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,rf).commit();
    }



}