package com.example.andrewparrish.andrewnaveedclock;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Created by andrewparrish on 11/11/14.
 */
public class LocationGetter extends AsyncTask<Date, Void, String> {

    private String image = "https://maps.googleapis.com/maps/api/staticmap?center=";
    private String sizing = "&zoom=16&size=400x400";
    private String geocode = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private String apikey = "&sensor=false&key=AIzaSyAPHqBGz8pBILSMkjScxOuy_IS7xAHPiHo";
    private final String nyc = ",+New+York+City";

    private String numbernamessuck(int num){
        if (num < 10){
            if (num >= 4){
                return num + "th";
            }else{
                switch (num){
                    case 1:
                        return "1st";
                    case 2:
                        return "2nd";
                    case 3:
                        return "3rd";
                }
                return null;

            }
        }else if (num >= 11 && num < 21){
            return num+"th";
        }else{
            int lastdigit = Integer.valueOf(String.valueOf(num).charAt(1));
            switch (lastdigit){
                case 1:
                    return num+"st";
                case 2:
                    return num+"nd";
                case 3:
                    return num+"rd";
            }
            return num+"th";
        }
    }

    private String makedaturl(boolean nyc, int minutes, int hours){
        String url = null;

        if (nyc){
            url = numbernamessuck(hours)+"+Ave,+"+numbernamessuck(minutes)+"+St"+nyc;
        }else{
            url = numbernamessuck(hours)+"St,+"+numbernamessuck(minutes)+"+St,+USA";
        }

        return url;
    }

    @Override
    protected String doInBackground(Date... params) {
        Date time = params[0];
        JSONObject json = null;
        String url = geocode;
        String imageurl = image;
        //Upper NYC
        if (time.getMinutes() >= 24){
            url+=makedaturl(true, time.getMinutes(), time.getHours());
        //Mostly upper NYC
        }else if (time.getMinutes() >= 15){
            if (time.getHours() <= 11){
                //NYC will work
                url+=makedaturl(true, time.getMinutes(), time.getHours());
            }else{
                //Do other
                url+=makedaturl(false, time.getMinutes(), time.getHours());
            }
        //No Good Streets-Do Other\
        }else{
            url+=makedaturl(false, time.getMinutes(), time.getHours());
        }

        url+=apikey;

        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost(url);
        String str = "";

        try{
            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        String longitude = "";
        String latitude = "";

        try{
            JSONArray array = new JSONArray(str);
            json = array.getJSONObject(0);
            JSONObject geocode = json.getJSONObject("geometry").getJSONObject("location");

            latitude = (String) geocode.get("lat");
            longitude = (String) geocode.get("lng");
        }catch (JSONException e){
            e.printStackTrace();
        }


        imageurl+=latitude+","+longitude+sizing;

        return imageurl;
    }
}
