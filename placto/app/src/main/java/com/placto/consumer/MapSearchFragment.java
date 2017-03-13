package com.placto.consumer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.placto.consumer.R.id.map;

/**
 * Created by dhavaldesai on 7/1/17.
 */

public class MapSearchFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap myMap;
    MapView mMapView;
    private View view;
    protected static Double latitude;
    protected static Double longitude;
    protected static String shop_name;
    protected static String rating;
  /*  private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);*/

    //private static final LatLng markerlocation=new LatLng(latitude,longitude);
    private Marker[] marker=new Marker[3];
    ArrayList<Customlist> search_items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.map_fragment, container, false);

            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) view.findViewById(map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
            // myMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }
        catch(InflateException e){
            Log.e(TAG, "Inflate exception");
        }

        try{
            search_items = getArguments().getParcelableArrayList("items");
        }catch (NullPointerException e)
        {

        }

        return view;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        myMap =map;

     //   myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.87365, 151.20689), 10));

        for(int i = 0; i < search_items.size();i++){
            shop_name= search_items.get(i).shop_name;
            rating=search_items.get(i).rating;
            latitude=Double.parseDouble(search_items.get(i).latitude);
            longitude=Double.parseDouble(search_items.get(i).longitude);
            LatLng markerlocation=new LatLng(latitude,longitude);

            CustomMarker cm= new CustomMarker();
            Bitmap bitmap = cm.GetBitmapMarker(getActivity().getApplicationContext(), R.drawable.tooltip, rating);
            marker[i]=myMap.addMarker(new MarkerOptions()
                    .position(markerlocation)
                    .title(shop_name)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap) ));

        }
  /*      mPerth = myMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth"));
        mPerth.setTag(0);

        mSydney = myMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        mSydney.setTag(0);

        mBrisbane = myMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        mBrisbane.setTag(0);*/
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
        SearchActivity mainActivity = (SearchActivity)getActivity();
        mainActivity.fab.show();

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
        mMapView.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }
        SearchActivity mainActivity = (SearchActivity)getActivity();
        mainActivity.fab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_list_icon));
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragmentsearch searchFragment =new Fragmentsearch();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle mapBundle = new Bundle();
                mapBundle.putInt(Fragmentsearch.ARG_PAGE, 1);
                searchFragment.setArguments(mapBundle);
                ft.replace(R.id.content_frame, searchFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
