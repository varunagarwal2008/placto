
        package com.varun.user.codeshastra;

        import android.Manifest;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.renderscript.Double2;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.appindexing.Thing;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.api.translate.Language;
        import com.google.api.translate.Translate;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        public class MainActivity extends AppCompatActivity {

            private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?appid=7e12de5f3094beb8bb56166d7d2cf58d&q=";
            private TextView name, temp, rain;
            ImageView image;
            private EditText enterCity;
            private Button getWeather;
            String temperature = "";
            String pressure = "";
            String humidity = "";
            String mintemp = "";
            String maxtemp = "";
            String description = "";
            Button qrscanner;
            /**
             * ATTENTION: This was auto-generated to implement the App Indexing API.
             * See https://g.co/AppIndexing/AndroidStudio for more information.
             */
            private GoogleApiClient client;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                qrscanner = (Button) findViewById(R.id.qrscanner);
                enterCity = (EditText) findViewById(R.id.enterCity);
                getWeather = (Button) findViewById(R.id.getWeather);
                image = (ImageView) findViewById(R.id.weatherimage);
                getWeather.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchWeather(enterCity.getText().toString());
                    }
                });
                name = (TextView) findViewById(R.id.nameText);
                temp = (TextView) findViewById(R.id.tempText);
                rain = (TextView) findViewById(R.id.rain);
                qrscanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                        startActivity(intent);
                    }
                });

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        101);

                //pankil code
                int MAXN = 1000;
                int n = 0;
                double[] x = new double[]{
                        793.776,
                        856.639,
                        621.04,
                        885.522,
                        1024.111,
                        1047.714,
                        1371.298,
                        1068.793,
                        1048.05,
                        1059.306,
                        797.489,
                        908.708,
                        901.435,
                        808.908,
                        830.062,
                        826.909,
                        659.701,
                        823.96,
                        772.264,
                        996.033,
                        972.961,
                        775.242,
                        355.736,
                        852.651,
                        960.901,
                        1124.069,
                        780.424,
                        553.477,
                        643.457,
                        973.152,
                        706.21,
                        910.504,
                        606.513,
                        1083.939,
                        783.203,
                        754.318,
                        702.211,
                        730.327,
                        1098.572,
                        759.807,
                        991.83,
                        738.746,
                        748.837,
                        769.685,
                        1027.481,
                        787.055,
                        965.08,
                        953.641,
                        940.414,
                        825.431,
                        595.302,
                        596.341,
                        579.902,
                };
                double[] y = new double[]{
                        668,
                        714,
                        764.1,
                        901.6,
                        819.6,
                        874.4,
                        899.6,
                        790.4,
                        930.1,
                        936.7,
                        1013.2,
                        1028,
                        930.5,
                        1033.2,
                        1078.2,
                        862.4,
                        863,
                        1032.1,
                        1076,
                        1073,
                        1123.2,
                        1140.6,
                        1069.5,
                        1151,
                        1044.6,
                        1234.5,
                        1088.5,
                        1307.6,
                        1328.3,
                        1073.8,
                        1335.7,
                        1308,
                        1231,
                        1457.3,
                        1417.4,
                        1551.5,
                        1471,
                        1465.1,
                        1689.2,
                        1744.6,
                        1740.2,
                        1751,
                        1743.9,
                        1887.6,
                        1911,
                        1796.9,
                        1882.1,
                        1900,
                        1921.4,
                        1986,
                        1900.7,
                        2079,
                };

                // first pass: read in data, compute xbar and ybar
                double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        /*while(!StdIn.isEmpty()) {
            x[n] = StdIn.readDouble();
            y[n] = StdIn.readDouble();
            sumx  += x[n];
            sumx2 += x[n] * x[n];
            sumy  += y[n];
            n++;
        }*/

                for (n = 0; n < 50; n++) {
                    sumx += x[n];
                    sumx2 += x[n] * x[n];
                    sumy += y[n];
                }
                n = 50;

                double xbar = sumx / n;
                double ybar = sumy / n;

                // second pass: compute summary statistics
                double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
                for (int i = 0; i < n; i++) {
                    xxbar += (x[i] - xbar) * (x[i] - xbar);
                    yybar += (y[i] - ybar) * (y[i] - ybar);
                    xybar += (x[i] - xbar) * (y[i] - ybar);
                }
                double beta1 = xybar / xxbar;
                double beta0 = ybar - beta1 * xbar;

                // print results
                System.out.println("y   = " + beta1 + " * x + " + beta0);

                // analyze results
                int df = n - 2;
                double rss = 0.0;      // residual sum of squares
                double ssr = 0.0;      // regression sum of squares
                for (int i = 0; i < n; i++) {
                    double fit = beta1 * x[i] + beta0;
                    rss += (fit - y[i]) * (fit - y[i]);
                    ssr += (fit - ybar) * (fit - ybar);
                }
                double R2 = ssr / yybar;
                double svar = rss / df;
                double svar1 = svar / xxbar;
                double svar0 = svar / n + xbar * xbar * svar1;
                System.out.println("R^2                 = " + R2);
                System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
                System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
                svar0 = svar * sumx2 / (n * xxbar);
      /*  System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

        System.out.println("SSTO = " + yybar);
        System.out.println("SSE  = " + rss);
        System.out.println("SSR  = " + ssr);
        System.out.println("---------------");
        System.out.println(596.341*-0.11345593698181204+1358.3332593916405*1.5);
        */
                double result = 596.341 * -0.11345593698181204 + 1358.3332593916405 * 1.5;
                String display = "" + result;
                rain.setText(display);
                rain.setVisibility(View.GONE);
                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
            }


            private void fetchWeather(String city) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Fetching Weather");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(toString(city), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            name.setText(jsonObject.getString("name"));
                            JSONObject jo1 = jsonObject.getJSONObject("main");
                            temperature = "Temperature : " + convertToCelsius(jo1.getString("temp")).toString();
                            temperature = temperature.substring(0, 18);
                            pressure = "Pressure : " + jo1.getString("pressure");
                            humidity = "Humidity : " + jo1.getString("humidity");
                            Double min = Double.parseDouble(jo1.getString("temp_min")) - 273.15;
                            mintemp = "Min Temp : " + min.toString();
                            Double max = Double.parseDouble(jo1.getString("temp_max")) - 273.15;
                            maxtemp = "Max  : " + max.toString();
                            String x = temperature + "\n" + pressure + "\n" + humidity + "\n" + mintemp + "\n" + maxtemp;
                            temp.setText(x);
//                    JSONObject jo2 = jsonObject.getJSONObject("weather");
//
//                    JSONArray jo2array=jsonObject.getJSONArray("weather");
//                    for(int i=0;i<jo2array.length();i++)
//                    {
//
//                    }
                            Double tempera = Double.parseDouble(jo1.getString("temp"));
                            if (tempera >= 35) {
                                image.setImageResource(R.drawable.weatherclear);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }

            private String toString(String city) {
                return String.format("%s%s", BASE_URL, city);
            }

            public static Double convertToCelsius(String kelvin) throws NumberFormatException {
                double inKelvin;
                try {
                    inKelvin = Double.parseDouble(kelvin);
                } catch (NumberFormatException e) {
                    throw e;
                }
                return inKelvin - 273.15;
            }

            /**
             * ATTENTION: This was auto-generated to implement the App Indexing API.
             * See https://g.co/AppIndexing/AndroidStudio for more information.
             */
            public Action getIndexApiAction() {
                Thing object = new Thing.Builder()
                        .setName("Main Page") // TODO: Define a title for the content shown.
                        // TODO: Make sure this auto-generated URL is correct.
                        .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                        .build();
                return new Action.Builder(Action.TYPE_VIEW)
                        .setObject(object)
                        .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                        .build();
            }

            @Override
            public void onStart() {
                super.onStart();

                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                client.connect();
                AppIndex.AppIndexApi.start(client, getIndexApiAction());
            }

            @Override
            public void onStop() {
                super.onStop();

                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                AppIndex.AppIndexApi.end(client, getIndexApiAction());
                client.disconnect();
            }
        }