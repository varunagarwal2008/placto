package com.varun.user.codeshastra;

/**
 * Created by User on 1/28/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class ScannerActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    public static final String MyPREFERENCES = "MyPrefs";
    private QRCodeReaderView mydecoderview;
    SharedPreferences mSharedPreference;
    String dist = "";
    String name = "";
    String uid = "";
    String addr1 = "";
    String addr2 = "";
    String addr = "";
    String base_JSON_URL = "http://itsdesai.comlu.com/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (mSharedPreference == null) {
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


        //Volley
//        JSONObject params = new JSONObject();
//        try {
//            params.put("aid",uid);
//            params.put("name",name);
//            params.put("address",addr);
//            params.put("district",dist);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                base_JSON_URL,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String error = response.getString("error");
                            if (error.equals("true")) {
                                Toast.makeText(ScannerActivity.this, "Please re-enter credentials", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(ScannerActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.d(TAG, "Error catch");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error aave che");
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params1 = new HashMap<String, String>();
                params1.put("aid", uid);
                params1.put("name", name);
                params1.put("address", addr);
                params1.put("district", dist);
                return params1;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }
}







