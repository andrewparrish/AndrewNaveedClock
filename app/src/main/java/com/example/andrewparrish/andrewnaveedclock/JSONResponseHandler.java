package com.example.andrewparrish.andrewnaveedclock;

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

    private static final String LOCATION_TAG = "location";
    private static final String LATITUDE_TAG = "lat";
    private static final String LONGITUDE_TAG = "lng";


    @Override
    public Coords handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException {
        Coords result = null;
        String JSONResponse = new BasicResponseHandler()
                .handleResponse(response);
        try {

            // Get top-level JSON Object - a "results" Array
            JSONArray resultsArray = (JSONArray) new JSONTokener(
                    JSONResponse).nextValue();

            // Extract value of "geometry" key -- a JSON object
            JSONObject geometry = (JSONObject) resultsArray.get(0);

            // Extract the nested "location" key -- a JSON Object
            JSONObject location = (JSONObject) geometry.get(LOCATION_TAG);

            // Extract Coordinates
            String latitude = (String) geometry.get(LATITUDE_TAG);
            String longitude = (String) geometry.get(LONGITUDE_TAG);

            // Summarize data as a string and add it to result
            result = new Coords(latitude,longitude);
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
