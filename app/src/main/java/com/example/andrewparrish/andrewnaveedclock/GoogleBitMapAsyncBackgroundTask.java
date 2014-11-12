package com.example.andrewparrish.andrewnaveedclock;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class GoogleBitMapAsyncBackgroundTask extends AsyncTask<String, Void, Bitmap> {


    @Override
    protected Bitmap doInBackground(String... params) {
        //todo: everything
        // Make Connection

        // Open inputStream

        // Pass inputStream into a new class called BitMapResponseHandler
        // which decodes the byte stream and returns a BitMap IMage

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bMap){

        // with the bitMap, go ahead and update the reference to the bitMap object
        // in CustomClockView

        return;
    }
}
