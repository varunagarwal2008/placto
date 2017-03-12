package com.nealgosalia.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {

    private Spinner spinnerSearch;
    private List<String> cropsList = new ArrayList<>();
    private List<Item> itemsList = new ArrayList<>();
    private List<Item> listItems = new ArrayList<>();
    private RecyclerView recyclerLectures;
    private ItemsAdapter mItemsAdapter;
    private TextView placeholderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        spinnerSearch = (Spinner) findViewById(R.id.spinnerSearch);
        placeholderText = (TextView) findViewById(R.id.placeholderText);
        recyclerLectures = (RecyclerView) findViewById(R.id.listItems);
        cropsList= Arrays.asList(
                getString(R.string.all),
                getString(R.string.bajra),
                getString(R.string.barley),
                getString(R.string.corn),
                getString(R.string.garlic),
                getString(R.string.greenpeas),
                getString(R.string.oat),
                getString(R.string.onion),
                getString(R.string.rice),
                getString(R.string.tomato),
                getString(R.string.wheat)
        );
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cropsList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearch.setAdapter(spinnerAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("sellerprofile");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        getsellerdata((Map<String, Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        if(listItems.size()!=0) {
            recyclerLectures.setVisibility(View.GONE);
            placeholderText.setVisibility(View.VISIBLE);
        }
        else {
            placeholderText.setVisibility(View.GONE);
            recyclerLectures.setVisibility(View.VISIBLE);
            mItemsAdapter = new ItemsAdapter(listItems);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
            recyclerLectures.setLayoutManager(mLayoutManager);
            recyclerLectures.setItemAnimator(new DefaultItemAnimator());
            recyclerLectures.addItemDecoration(new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL));
            recyclerLectures.setAdapter(mItemsAdapter);
        }
        spinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                listItems.clear();
                if(cropsList.get(spinnerSearch.getSelectedItemPosition()).equals("All")) {
                    listItems = itemsList;
                } else {
                    for (Item item : itemsList) {
                        if (item.getProduct().equals(cropsList.get(spinnerSearch.getSelectedItemPosition()))) {
                            listItems.add(item);
                        }
                    }
                }
                mItemsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listItems=itemsList;
                mItemsAdapter.notifyDataSetChanged();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BuyActivity.this, MapSearchActivity.class);
                startActivity(i);
            }
        });
    }

    public void getsellerdata(Map<String,Object> sellers){

        for (Map.Entry<String, Object> entry : sellers.entrySet()){

            Item it =new Item();

            Map singleUser = (Map) entry.getValue();
            Log.d("buyactivity",singleUser.get("uid").toString());
            Log.d("buyactivity",singleUser.get("product").toString());
            Log.d("buyactivity",singleUser.get("booktime").toString());

            it.setAid(singleUser.get("uid").toString());
            it.setProduct(singleUser.get("product").toString());
            it.setBookTime(singleUser.get("booktime").toString());
            it.setPrice(singleUser.get("price").toString());
            it.setQuantity(singleUser.get("quantity").toString());
            it.setId(singleUser.get("uid").toString());
            itemsList.add(it);
        }
    }
}