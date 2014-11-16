package com.example.andrewparrish.andrewnaveedclock;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import java.io.IOException;



/**
 * Created by Tuxedo on 11/12/14.
 */
public class GoogleJSONAsyncBackgroundTask extends AsyncTask< Void,  Void , Coords> {

    private static final String TAG = "clock-activity";
    AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

    @Override
    protected Coords doInBackground(Void... params) {

        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();

        //Get URL here
        String url = Geocoder_Utility.get_url(time);

        Log.d("URL", url);

        HttpGet request = new HttpGet(url);
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

        if (result != null) {

            //URL for the bitMap
            String baseURL = "https://maps.googleapis.com/maps/api/staticmap?center=";
            String sizing = "&zoom=16&size=400x400";

            Log.e(TAG, "Coord toString() is: " + result.toString());
            String imageURL = baseURL + "" + result.toString() + "" + sizing + "";
            Log.i(TAG, "imageURL is: " + imageURL);


            // Launch second async task
            try {
                GoogleBitMapAsyncBackgroundTask performBackgroundTask = new GoogleBitMapAsyncBackgroundTask();
                performBackgroundTask.execute(imageURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mClient != null) {
                try {
                    mClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
