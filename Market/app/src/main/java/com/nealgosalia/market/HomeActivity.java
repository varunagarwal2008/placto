package com.nealgosalia.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnWeather, btnMarket, btnNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnWeather = (Button) findViewById(R.id.btnWeather);
        btnMarket = (Button) findViewById(R.id.btnMarket);
        btnNews = (Button) findViewById(R.id.btnNews);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(i);
            }
        });
        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, BuySellActivity.class);
                startActivity(i);
            }
        });
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, NewsActivity.class);
                startActivity(i);
            }
        });
    }
}
