package com.placto.consumer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dhavaldesai on 8/12/16.
 */

public class RegisterFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    private Button btnRegister;
    private TextView btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        inputEmail= (EditText) view.findViewById(R.id.remail);
        inputPassword = (EditText) view.findViewById(R.id.rpassword);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnLinkToLogin = (TextView) view.findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        //pDialog = new ProgressDialog(getActivity().getApplicationContext());
      //  pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getActivity().getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent i = new Intent(getActivity().getApplicationContext(),SearchActivity.class);
            startActivity(i);

        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  try {
                    OTPFragment otpf = new OTPFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, otpf);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                } catch (ClassCastException cce) {

                }
                */
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    registerUser(email, password);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    SigninFragment sf =new SigninFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container,sf);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                } catch (ClassCastException cce) {

                }
            }
        });



        return view;
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //pDialog.setMessage("Registering ...");
        //showDialog();

        JSONObject params =new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Register Response: " + response.toString());
               // hideDialog();

                try {
                   // boolean  success= false;
                    //boolean error = jObj.getBoolean("error");
                    String status = null;
                    try {
                       status= response.getString("status");
                        //success=response.getBoolean("success");
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(status == "success" ){
                        // User successfully stored in MySQL
                        // Now store the user in sqlite     change name
                        // String uid = jObj.getString("uid");

                        // String name = user.getString("name");
                      //  String token =response.getString("token");
                        // String created_at = user.getString("created_at");

                        // Inserting row in users table
                       // db.addUser(email,password,token);

                        // Launch login activity
                        try {
                            SigninFragment sf =new SigninFragment();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container,sf);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.commit();
                        } catch (ClassCastException cce) {

                        }

                        Toast.makeText(getActivity().getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                    }

                    else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = response.getString("message");
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               // hideDialog();
            }
        }) ;

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

