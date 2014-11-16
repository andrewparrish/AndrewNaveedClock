package com.example.andrewparrish.andrewnaveedclock;


import android.text.format.Time;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by andrewparrish on 11/12/14.
 */
public class Geocoder_Utility {

    private static String geocode = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static String apikey = "&sensor=false&key=AIzaSyAPHqBGz8pBILSMkjScxOuy_IS7xAHPiHo";
    private static String nycurl = ",+New+York+City";

    private static String numbernamessuck(int num){
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

    private static String makedaturl(boolean nyc, int minutes, int hours){
        String url = null;

        if (nyc){
            url = numbernamessuck(hours)+"+Ave,+"+numbernamessuck(minutes)+"+St"+nycurl;
        }else{
            url = numbernamessuck(hours)+"+St,+"+numbernamessuck(minutes)+"+St,+USA";
        }

        return url;
    }

    public static String get_url(Time time) {
        int hour = time.hour % 12;
        String url = geocode;

        //Upper NYC
        if (time.minute >= 24){
            url+=makedaturl(true, time.minute, hour);
            //Mostly upper NYC
        }else if (time.minute >= 15){
            if (hour <= 11){
                //NYC will work
                url+=makedaturl(true, time.minute, hour);
            }else{
                //Do other
                url+=makedaturl(false, time.minute, hour);
            }
            //No Good Streets-Do Other\
        }else{
            url+=makedaturl(false, time.minute, hour);
        }

        url+=apikey;
        return url;

    }

}
