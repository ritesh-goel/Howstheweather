package com.rg.howstheweather;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String forcastUrl = "https://api.darksky.net/forecast/";
    private final String apiKey = "e833e57923fb9fc9479e15ba50e4843f";
    private CurrentWeather mCurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(getForecastUrl(37.8267, -122.4233))
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    showErrorMessage();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        try {
                            mCurrentWeather = getCurrentWeather(jsonData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showErrorMessage();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Network unavailable!", Toast.LENGTH_SHORT).show();
        }
    }

    private CurrentWeather getCurrentWeather(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather weather = new CurrentWeather();
        weather.setHumidity(currently.getDouble("humidity"));
        weather.setTime(currently.getLong("time"));
        weather.setTemperature(currently.getDouble("temperature"));
        weather.setIcon(currently.getString("icon"));
        weather.setPrecipChance(currently.getDouble("precipProbability"));
        weather.setSummary(currently.getString("summary"));
        weather.setTimezone(timezone);

        Log.d(TAG,weather.getFormattedTime());

        return weather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    public void showErrorMessage() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "errorDialog");
    }

    public String getForecastUrl(double lat, double longi) {
        return forcastUrl + apiKey + "/" + lat + "," + longi;
    }
}
