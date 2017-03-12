package com.nealgosalia.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kira on 29/1/17.
 */

public class BuySellActivity extends AppCompatActivity {

    Button btnBuy, btnSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buysell);
        btnBuy=(Button) findViewById(R.id.btnBuy);
        btnSell=(Button) findViewById(R.id.btnSell);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BuySellActivity.this, BuyActivity.class);
                startActivity(i);
            }
        });
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BuySellActivity.this, SellActivity.class);
                startActivity(i);
            }
        });
    }
}