package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private TextView textLatLong;
    private TextView textDescription;
    private TextView textTemp;
    private TextView textFeels;
    private TextView textCity;
    private TextView textCurrentDetails;
    private String fIcon = "";
    private ProgressBar progressBar;
    private ImageView imageIcon;
    private SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sPref;


    private void saveText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("CITY", textCity.getText().toString());
        ed.putString("LATLONG", textLatLong.getText().toString());
        ed.putString("DESC", textDescription.getText().toString());
        ed.putString("TEMP", textTemp.getText().toString());
        ed.putString("FEELS", textFeels.getText().toString());
        ed.putString("DET", textCurrentDetails.getText().toString());
        ed.putString("ICON", fIcon);

        ed.apply();
    }

    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString("CITY", "");
        textCity.setText(savedText);
        savedText = sPref.getString("LATLONG", "");
        textLatLong.setText(savedText);
        savedText = sPref.getString("DESC", "");
        textDescription.setText(savedText);
        savedText = sPref.getString("TEMP", "");
        textTemp.setText(savedText);
        savedText = sPref.getString("FEELS", "");
        textFeels.setText(savedText);
        savedText = sPref.getString("DET", "");
        textCurrentDetails.setText(savedText);
        savedText = sPref.getString("ICON", "");
        Log.i("ICON", fIcon);
        fIcon = savedText;
        switch (fIcon) {
            case ("01d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l01d));
                break;
            case ("01n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l01n));
                break;
            case ("02d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l02d));
                break;
            case ("02n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l02n));
                break;
            case ("03d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l03d));
                break;
            case ("03n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l03n));
                break;
            case ("04d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l04d));
                break;
            case ("04n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l04n));
                break;
            case ("09d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l09d));
                break;
            case ("10n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l10n));
                break;
            case ("10d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l10d));
                break;
            case ("11n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l11n));
                break;
            case ("11d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l11d));
                break;
            case ("13n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l13n));
                break;
            case ("13d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l13d));
                break;
            case ("50n"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l50n));
                break;
            case ("50d"):
                imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l50d));
                break;


        }
    }

    class Weather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... address) {

            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                Log.i("Content", content);
                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {

        progressBar.setVisibility(View.VISIBLE);
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);
                        if (locationRequest != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            textLatLong.setText(
                                    String.format(
                                            "Latitude: %s\nLongitude: %s",
                                            latitude,
                                            longitude
                                    )
                            );

                            Weather weather = new Weather();
                            try {
                                String content;
                                content = weather.execute("https://openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=439d4b804bc8187953eb36d2a8c26a02").get();

                                Log.i("contentData", content);

                                JSONObject jsonObject = new JSONObject(content);
                                String weatherData = jsonObject.getString("weather");
                                String mainTemperature = jsonObject.getString("main");
                                String wind = jsonObject.getString("wind");
                                String cityName = jsonObject.getString("name");


                                Log.i("weatherData", weatherData);
                                JSONArray array = new JSONArray(weatherData);


                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();

                                String main = "";
                                String description = "";
                                String icon = "";
                                String temperature = "";
                                String feelsLike = "";
                                String pressure = "";
                                String humidity = "";
                                String windSpeed = "";

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject weatherPart = array.getJSONObject(i);
                                    main = weatherPart.getString("main");
                                    description = weatherPart.getString("description");
                                    icon = weatherPart.getString("icon");
                                }
                                JSONObject mainPart = new JSONObject(mainTemperature);
                                temperature = mainPart.getString("temp");
                                feelsLike = mainPart.getString("feels_like");
                                pressure = mainPart.getString("pressure");
                                humidity = mainPart.getString("humidity");

                                JSONObject windPart = new JSONObject(wind);
                                windSpeed = windPart.getString("speed");


                                Log.i("main", main);
                                Log.i("description", description);
                                Log.i("icon", icon);
                                Log.i("temp", temperature);
                                Log.i("city", cityName);


                                fIcon = icon;
                                switch (icon) {
                                    case ("01d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l01d));
                                        break;
                                    case ("01n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l01n));
                                        break;
                                    case ("02d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l02d));
                                        break;
                                    case ("02n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l02n));
                                        break;
                                    case ("03d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l03d));
                                        break;
                                    case ("03n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l03n));
                                        break;
                                    case ("04d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l04d));
                                        break;
                                    case ("04n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l04n));
                                        break;
                                    case ("09d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l09d));
                                        break;
                                    case ("10n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l10n));
                                        break;
                                    case ("10d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l10d));
                                        break;
                                    case ("11n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l11n));
                                        break;
                                    case ("11d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l11d));
                                        break;
                                    case ("13n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l13n));
                                        break;
                                    case ("13d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l13d));
                                        break;
                                    case ("50n"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l50n));
                                        break;
                                    case ("50d"):
                                        imageIcon.setImageDrawable(getResources().getDrawable(R.drawable.l50d));
                                        break;


                                }
                                description = description.substring(0, 1).toUpperCase() + description.substring(1);
                                textDescription.setText(description);
                                textTemp.setText(temperature + "℃");
                                textFeels.setText("Feels like: " + feelsLike + "℃");
                                textCity.setText(cityName + ", " + today.monthDay + "." + (today.month + 1) + "." + today.year + "     " + today.hour + " h " + today.minute + " m");
                                textCurrentDetails.setText("Current details\n\n" +
                                        "Pressure\t\t\t\t\t\t" + pressure + " mBar\n" +
                                        "Humidity\t\t\t\t\t\t" + humidity + "%\n" +
                                        "Wind speed\t\t\t\t" + windSpeed + " m/s");

                                saveText();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "No internet connection", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }, Looper.getMainLooper());
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textTemp = findViewById(R.id.textMain);
        textLatLong = findViewById(R.id.textLatLong);
        textDescription = findViewById(R.id.textDescription);
        textFeels = findViewById(R.id.textFeels);
        textCity = findViewById(R.id.textCity);
        textCurrentDetails = findViewById(R.id.textCurrentDetails);
        progressBar = findViewById(R.id.progressBar);
        imageIcon = findViewById(R.id.icon);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        loadText();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION

                    );
                    swipeRefreshLayout.setRefreshing(false);
                } else {

                    getCurrentLocation();
                    swipeRefreshLayout.setRefreshing(false);

                }
            }
        });

    }

}
