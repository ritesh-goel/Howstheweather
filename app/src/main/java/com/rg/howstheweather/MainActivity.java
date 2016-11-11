package com.rg.howstheweather;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getForecastUrl(37.8267, -122.4233))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.body().string());
                if (response.isSuccessful()) {

                } else {
                    showErrorMessage();
                }
            }
        });
//        try {
//            Response response = call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void showErrorMessage() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "errorDialog");
    }

    public String getForecastUrl(double lat, double longi) {
        return forcastUrl + apiKey + "/" + lat + "," + longi;
    }
}
