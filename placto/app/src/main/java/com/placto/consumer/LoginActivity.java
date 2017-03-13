package com.placto.consumer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class LoginActivity extends FragmentActivity{

    SigninFragment signinFragment;
    RegisterFragment rf ;
    OTPFragment otpf =new OTPFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            signinFragment = new SigninFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, signinFragment).commit();
        }
    }
}