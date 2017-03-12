package com.nealgosalia.market;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class QRScannerActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    private QRCodeReaderView mydecoderview;
    private SharedPreferences mSharedPreference;
    static String dist=" ";
    String name=" ";
    static String uid=" ";
    String addr=" ";
    String addr1=" ";
    String addr2=" ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(mSharedPreference==null) {
            mSharedPreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreference.edit();
            editor.putString("CURRENT_CLUE", "0.0");
            editor.commit();
        }

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
        mydecoderview.setQRDecodingEnabled(true);
        mydecoderview.setAutofocusInterval(2000L);
        mydecoderview.setTorchEnabled(true);
        mydecoderview.setBackCamera();
        mydecoderview.startCamera();
        mydecoderview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        int diststart = text.indexOf("dist");
        int distend = text.indexOf("subdist");
        char[] xml = text.toCharArray();

        for (int i = diststart + 6; i <= distend - 3; i++) {
            dist += xml[i];
        }
        int namestart = text.indexOf("name");
        int nameend = text.indexOf("gender");

        for (int i = namestart + 6; i <= nameend - 3; i++) {
            name += xml[i];
        }
        int uidstart = text.indexOf("uid");
        int uidend = text.indexOf("name");

        for (int i = uidstart + 5; i <= uidend - 3; i++) {
            uid += xml[i];
        }
        uid = uid.substring(0, 5);

        int addrstart = text.indexOf("house");
        int addrend = text.indexOf("street");

        for (int i = addrstart + 7; i <= addrend - 3; i++) {
            addr1 += xml[i];
        }
        int addrstart2 = text.indexOf("street");
        int addrend2 = text.indexOf("loc");
        for (int i = addrstart2 + 8; i <= addrend2 - 3; i++) {
            addr2 += xml[i];
        }

        addr = addr1 + addr2;
        uid=uid.trim();
        addr=addr.trim();
        name=name.trim();
        dist=dist.trim();


        int i=0;
        if(i==0) {
            //firebase database access
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("users");
            String userId = "";
            User user = null;

            userId = myRef.push().getKey();
// creating user object
            user = new User(uid, name, addr, dist);
// pushing user to 'users' node using the userId
            myRef.child(userId).setValue(user);

            i++;
            Intent in = new Intent(QRScannerActivity.this, HomeActivity.class);
            startActivity(in);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("QRScanner", "Failed to read value.", error.toException());
                }
            });

        }
    }



    @Override
    public void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }


    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("user")
                .child(getUid());
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}