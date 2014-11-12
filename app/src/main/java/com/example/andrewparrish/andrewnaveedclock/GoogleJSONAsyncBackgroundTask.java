package com.example.andrewparrish.andrewnaveedclock;

import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class GoogleJSONAsyncBackgroundTask extends AsyncTask< Void,  Void , Coords> {


    //TODO: assume URL works -> this is a test
    String URL = "https://maps.googleapis.com/maps/api/geocode/json?address="
            + "14th+Ave,+25th+St,+New+York+City"
            + "&sensor=false&key=AIzaSyAPHqBGz8pBILSMkjScxOuy_IS7xAHPiHo";


    AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

    @Override
    protected Coords doInBackground(Void... params) {
//        //TODO: set time and parse
//        Time time = new Time(Time.getCurrentTimezone());
//        time.setToNow();
        HttpGet request = new HttpGet(URL);
        JSONResponseHandler responseHandler = new JSONResponseHandler();

        try {
            return mClient.execute(request, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Coords result) {
        //URL for the bitMap
        String baseURL = "https://maps.googleapis.com/maps/api/staticmap?center=";
        String sizing = "&zoom=16&size=400x400";

        String imageURL = baseURL + result.toString() + sizing;

        // Launch second async task
        try {
            GoogleBitMapAsyncBackgroundTask performBackgroundTask = new GoogleBitMapAsyncBackgroundTask();
            performBackgroundTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
