package com.example.andrewparrish.andrewnaveedclock;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;


public class SettingsActivity extends Activity {
    private static final String MILITARY_TIME_BOOLEAN = "military_time_boolean";
    private static final String TIME_COLOR_INTEGER = "time_color_integer";
    private static final String TAG = "clock-activity";

    SharedPreferences preferences;

    private boolean timeSettingChanged;
    private boolean militaryTime;
    public int colorTime;
    private RadioGroup rg_time;
    private RadioGroup rg_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        militaryTime = preferences.getBoolean(MILITARY_TIME_BOOLEAN, false);
        colorTime = preferences.getInt(TIME_COLOR_INTEGER, Color.BLACK);

        rg_time = (RadioGroup) findViewById(R.id.radioTime);
        rg_color = (RadioGroup) findViewById(R.id.radioColor);

        if (militaryTime) {
            rg_time.check(R.id.rb_militaryTime);
        }
        else{
            rg_time.check(R.id.rb_normalTime);
        }

        switch (colorTime){
            case Color.RED:
                rg_color.check(R.id.rb_red);
                break;

            case Color.YELLOW:
                rg_color.check(R.id.rb_yellow);
                break;

            case Color.GREEN:
                rg_color.check(R.id.rb_green);
                break;

            case Color.BLUE:
                rg_color.check(R.id.rb_blue);
                break;

            case Color.GRAY:
                rg_color.check(R.id.rb_gray);
                break;

            case Color.BLACK:
                rg_color.check(R.id.rb_black);
                break;
        }

        rg_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_militaryTime:
                        militaryTime = true;
                        timeSettingChanged = true;
                        break;

                    case R.id.rb_normalTime:
                        militaryTime = false;
                        timeSettingChanged = true;
                        break;
                }
            }
        });

        rg_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_red:
                        colorTime = Color.RED;
                        break;

                    case R.id.rb_yellow:
                        colorTime = Color.YELLOW;
                        break;

                    case R.id.rb_green:
                        colorTime = Color.GREEN;
                        break;

                    case R.id.rb_blue:
                        colorTime = Color.BLUE;
                        break;

                    case R.id.rb_gray:
                        colorTime = Color.GRAY;
                        break;

                    case R.id.rb_black:
                        colorTime = Color.BLACK;
                        break;

                    default:
                        colorTime  = Color.BLACK;
                }
            }
        });

        Button okButton = (Button) findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                // Increment the counter
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(TIME_COLOR_INTEGER, colorTime);
                editor.putBoolean(MILITARY_TIME_BOOLEAN, militaryTime);
                editor.commit(); // Very important

                Intent data = getIntent();
                setResult(RESULT_OK, data);
                finish();
            }
        });

        Button closeButton = (Button) findViewById(R.id.button_cancel);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // This function closes Activity Two
                // Hint: use Context's finish() method
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public void onResume(Bundle bundle){
        if (militaryTime == true){
            rg_time.check(R.id.rb_militaryTime);
        }
        else{
            rg_time.check(R.id.rb_normalTime);
        }

        switch (colorTime){
            case Color.RED:
                rg_color.check(R.id.rb_red);
                break;

            case Color.YELLOW:
                rg_color.check(R.id.rb_yellow);
                break;

            case Color.GREEN:
                rg_color.check(R.id.rb_green);
                break;

            case Color.BLUE:
                rg_color.check(R.id.rb_blue);
                break;

            case Color.GRAY:
                rg_color.check(R.id.rb_gray);
                break;

            case Color.BLACK:
                rg_color.check(R.id.rb_black);
                break;
        }
    }

    public void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.clock_view_1) {
            Intent basicClockIntent = new Intent(this, BasicClockActivity.class);
            startActivity(basicClockIntent);
            return true;
        }
        else if (id == R.id.clock_view_2){
            Intent customClockIntent = new Intent(this, CustomClockActivity.class);
            startActivity(customClockIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
