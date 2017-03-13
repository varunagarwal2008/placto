package com.placto.consumer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.placto.consumer.Customlist.fromJson;


/**
 * Created by User on 12/27/2016.
 */

public class Fragmentsearch extends Fragment{
    private SQLiteHandler db;
    EditText searchtext;
    GPSTracker gps;
    Button search;
    public static final String base_JSON_URL = "http://139.59.7.198/api/query";
    //public static final String JSON_URL=base_JSON_URL+"/user/v1/query";
    double latitude, longitude;
    ListView list;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private ArrayList<Customlist> al;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentsearch, container, false);
        searchtext = (EditText) rootView.findViewById(R.id.searchtext);
        search = (Button)rootView.findViewById(R.id.searchbutton);
        list = (ListView) rootView.findViewById(R.id.list);

        final SearchActivity mainActivity = (SearchActivity)getActivity();
        mainActivity.fab.hide();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shop_name = searchtext.getText().toString();

                gps = new GPSTracker(getActivity());
                if (gps.canGetLocation()) {

                    latitude = gps.latitude;
                    longitude = gps.longitude;

                }
                JSONObject params = new JSONObject();
                //JSONArray params = new JSONArray();
                try {
                    params.put("lat",latitude);
                    params.put("lon",longitude);
                    params.put("query",searchtext.getText().toString());
                    params.put("page",1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        base_JSON_URL, params,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Method for response Jsonobject i.e. change customlist
                                Log.d(TAG,"querry Response " + response.toString());

                                try {
                                    JSONArray jsonArray = response.getJSONArray("items");
                                    al = fromJson(jsonArray);
                                    CustomlistAdapter adapter = new CustomlistAdapter(getActivity(),al);
                                    list.setAdapter(adapter);
                                    mainActivity.fab.show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(TAG,"EXCEPTION");
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        //pDialog.hide();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        db = new SQLiteHandler(getActivity().getApplicationContext());
                        String token = db.getUserToken();
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("x-access-token", token);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(jsonObjReq);
            }
        });


        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        final SearchActivity mainActivity = (SearchActivity)getActivity();
        mainActivity.fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_black_24dp));
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(getActivity());
                if (gps.canGetLocation()) {
                    latitude = gps.latitude;
                    longitude = gps.longitude;
                }

                MapSearchFragment mapFragment =new MapSearchFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if(al != null) {
                    Bundle mapBundle = new Bundle();
                    mapBundle.putParcelableArrayList("items", al);
                    mapFragment.setArguments(mapBundle);
                }
                ft.replace(R.id.content_frame, mapFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                mainActivity.fab.hide();
            }
        });
        if(al != null && al.size() > 0)
        {
            mainActivity.fab.show();
        }
    }

    public static Fragmentsearch newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Fragmentsearch fragment = new Fragmentsearch();
        fragment.setArguments(args);
        return fragment;
    }


}
