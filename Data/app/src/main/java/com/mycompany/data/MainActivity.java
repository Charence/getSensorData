package com.mycompany.data;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    private Spinner spinner;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        Typeface scribble = Typeface.createFromAsset(getAssets(), "fonts/cool.ttf");
        textView.setTypeface(scribble, Typeface.BOLD);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_sensors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(25);
                ((TextView) parent.getChildAt(0)).setTypeface(null, Typeface.BOLD);

                String sensor = parent.getItemAtPosition(pos).toString();

                Toast.makeText(parent.getContext(), "Getting Data of " + sensor + ", Swipe to Go or click to select another one", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void sendMessage(View view) {
        String sensor = spinner.getSelectedItem().toString();
        Intent intentS = null;
        switch(sensor) {
            case "Accelerometer" : intentS = new Intent(this,Accelerometer.class);
                break;
            case "Gyroscope" : intentS = new Intent(this,Gyroscope.class);
                break;
            case "Magnetometer" : intentS = new Intent(this,Magnetometer.class);
                break;
            case "All Sensors" : intentS = new Intent(this,AllSensors.class);
        }
        startActivity(intentS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPlaying = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isPlaying) {
            MusicManager.start(this);
        }
    }


}
