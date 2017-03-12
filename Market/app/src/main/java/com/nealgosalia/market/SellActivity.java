package com.nealgosalia.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SellActivity extends AppCompatActivity{

    private Spinner spinnerCrop;
    private List<String> cropsList = new ArrayList<>();
    private NumberPicker npPrice, npQuantity;
    private Button btnSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        spinnerCrop = (Spinner) findViewById(R.id.spinnerCrop);
        npPrice = (NumberPicker) findViewById(R.id.npPrice);
        npQuantity = (NumberPicker) findViewById(R.id.npQty);
        btnSell = (Button) findViewById(R.id.btnSell);
        npPrice.setMinValue(1);
        npPrice.setMaxValue(500);
        npPrice.setWrapSelectorWheel(true);
        npQuantity.setMinValue(1);
        npQuantity.setMaxValue(500);
        npQuantity.setWrapSelectorWheel(true);
        cropsList= Arrays.asList(
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
        spinnerCrop.setAdapter(spinnerAdapter);
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = cropsList.get(spinnerCrop.getSelectedItemPosition());
                String price = ""+npPrice.getValue();
                String quantity = ""+npQuantity.getValue();
                String uid = QRScannerActivity.uid;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("sellerprofile");

                String sellerId = "";
                Seller seller = null;

                sellerId = myRef.push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String booktime = sdf.format(new Date());

                seller = new Seller(uid, product, price,quantity,booktime);
                myRef.child(sellerId).setValue(seller);

                Intent intent = new Intent(SellActivity.this, HomeActivity.class);
                startActivity(intent);
            }

        });
    }
}