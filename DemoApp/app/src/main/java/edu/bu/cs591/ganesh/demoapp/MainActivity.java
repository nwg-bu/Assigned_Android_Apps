package edu.bu.cs591.ganesh.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private int zapIntensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button beepButton = (Button) findViewById(R.id.btnBeep);
        Button vibrateButton = (Button) findViewById(R.id.btnVibrate);
        Button zapButton = (Button) findViewById(R.id.btnZap);
        Button ledButton = (Button) findViewById(R.id.btnLED);

        SeekBar seekBarZap = (SeekBar)findViewById(R.id.sbZap);
        seekBarZap.setProgress(0);
        seekBarZap.incrementProgressBy(1);
        seekBarZap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setZapIntensity(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        beepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PavlokConnection connection = new PavlokConnection();
                connection.execute("beep",255);
            }
        });

        vibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PavlokConnection connection = new PavlokConnection();
                connection.execute("vibration",255);
            }
        });

        zapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PavlokConnection connection = new PavlokConnection();
                connection.execute("shock",zapIntensity);
            }
        });

        ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PavlokConnection connection = new PavlokConnection();
                connection.execute("led",4);
            }
        });


    }

    public void setZapIntensity(int zapIntensity) {
        this.zapIntensity = zapIntensity;
    }
}
