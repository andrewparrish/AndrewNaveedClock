package com.example.andrewparrish.andrewnaveedclock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class GoogleBitMapAsyncBackgroundTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "clock-activity";

    @Override
    protected Bitmap doInBackground(String... params) {
        //get params
        String url = params[0];
        Bitmap bMap = null;
        // Make Connection and get inputStream
        try {
            InputStream in = new java.net.URL(url).openStream();
            bMap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bMap;
    }

    @Override
    protected void onPostExecute(Bitmap bMap){

        // with the bitMap, go ahead and update the reference to the bitMap object
        // in CustomClockView
        CustomClockView.setBitMap(bMap);
    }
}
