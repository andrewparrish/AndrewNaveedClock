package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//TODO -> ADD CODE TO PERSIST SETTINGS IN ALL LIFECYCLE METHODS
public class BasicClockActivity extends Activity {

    public TextView textViewDate;
    public TextView textViewTime;
    public RelativeLayout parentFrame;
    private Timer timer;
    private TimerTask timerTask;
    private static final String TAG = "clock-activity";

    ArrayList<Integer> mSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clock);
        parentFrame = (RelativeLayout) findViewById(R.id.parentFrame);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        mSelectedItems = new ArrayList<Integer>();

        //instantiate the time object necessary for the clock
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Time today = new Time(Time.getCurrentTimezone());
                                today.setToNow();
                                // update TextView here!
                                setTime(today);
                                setDate(today);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.persistent_settings) {
            //TODO -> ABSTRACT THIS FUNCTIONALITY INTO A SEPERATE CLASS SO THE CUSTOM VIEW CAN USE IT
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set the dialog title
            builder.setTitle(R.string.dialog_settings_message)
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(R.array.settings, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
                                        mSelectedItems.add(which);
                                    } else if (mSelectedItems.contains(which)) {
                                        // Else, if the item is already in the array, remove it
                                        mSelectedItems.remove(Integer.valueOf(which));
                                    }
                                }
                            })
                            // Set the action buttons
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog
                            Log.i(TAG, "in positive button click callback ");
                    //TODO -> FILL IN THE SELECTED OPTIONS AND TIE THEM TO BUNDLE VARIABLES
                            if(mSelectedItems.contains(0)){
                                //fill in later -- if user selected "timezone"
                            }
                            if (mSelectedItems.contains(1)){
                                //fill in later -- if user selected "military time"
                            }
                            if (mSelectedItems.contains(2)){
                                //fill in later -- if user selected "Color"

                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Log.i(TAG, "in negative button click callback ");
                            mSelectedItems.clear();
                        }
                    });

            builder.show();
            return true;
        }
        else if (id == R.id.clock_view_2){
            Intent customClockIntent = new Intent(this, CustomClockActivity.class);
            startActivity(customClockIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTime(Time today){
        String time = today.format("%k:%M:%S");
        textViewTime.setText(time);  // Current time
       //Log.i(TAG, time);
    }

    private void setDate(Time today){
        String date = today.month + "/" + today.monthDay + "/" + today.year + "";
        textViewDate.setText(date);
        //Log.i(TAG, date);
    }
}
