package com.placto.consumer;

import android.app.Activity;
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


public class SigninFragment extends Fragment {

    private static final String TAG = "SigninFragment";
    private static final int REQUEST_SIGNUP = 0;

    private Button btnLogin;
    private TextView btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        inputEmail=(EditText)view.findViewById(R.id.email);
        inputPassword =(EditText)view.findViewById(R.id.password);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLinkToRegister = (TextView) view.findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        //trial code please check
      // pDialog = new ProgressDialog();
       // pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getActivity().getApplicationContext());

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent i = new Intent(getActivity().getApplicationContext(),SearchActivity.class);
            startActivity(i);
        }

        // Login button Click Event
        //made change
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });


        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            RegisterFragment rf = new RegisterFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, rf);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
            }
        });



        return view;
    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

       // pDialog.setMessage("Logging in ...");
       // showDialog();

        JSONObject params =new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, params, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Login Response: " + response.toString());
               // hideDialog();
                try {

                   // boolean  success= false;
                   // JSONObject success = null;
                    String status = null;
                    try {
                        //success = response.getJSONObject("success");
                       // success=response.getBoolean("success");
                        status= response.getString("status");

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(status != "success") {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);
                       // HashMap<String, String> user = db.getUserToken();
                        //String tok = user.get("token");
                        // Now store the user in SQLite     made change

                        String token = response.getString("token");
                        // Inserting row in users table
                        db.addUser(token);

                        // Launch main activity
                        //write code here ...
                      /*  HomeFragment hf =new HomeFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container,hf);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();*/
                        db.getUserToken();
                        Intent i = new Intent(getActivity().getApplicationContext(),SearchActivity.class);
                        startActivity(i);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = "user not logged in";
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                   // HashMap<String, String> user = db.getUserToken();
                   // String tok = user.get("token");
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               // hideDialog();
            }
        });

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


