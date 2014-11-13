package com.example.andrewparrish.andrewnaveedclock;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tuxedo on 11/12/14.
 */
public class JSONResponseHandler implements ResponseHandler<Coords> {

    private final String RESULTS_TAG = "results";
    private final String LOCATION_TAG = "location";
    private final String LATITUDE_TAG = "lat";
    private final String LONGITUDE_TAG = "lng";


    @Override
    public Coords handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException {
        Coords result = null;
        String JSONResponse = new BasicResponseHandler()
                .handleResponse(response);
        try {
            JSONObject json = null;
            Log.d("JSON", JSONResponse);

            // Get top-level JSON Object - a "results" Array
            JSONObject map = new JSONObject(JSONResponse);
            Log.d("URL", map.toString());
            JSONArray array = map.getJSONArray("results");
            json = array.getJSONObject(0);
            JSONObject geocode = json.getJSONObject("geometry").getJSONObject("location");

            String latitude = String.valueOf(geocode.get("lat"));
            String longitude = String.valueOf(geocode.get("lng"));

            result = new Coords(latitude,longitude);
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
