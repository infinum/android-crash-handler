package com.zplesac.sampleapp;

import com.crashlytics.android.Crashlytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private Button logButton;

    private Button crashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logButton = (Button) findViewById(R.id.button_test_logging);
        crashButton = (Button) findViewById(R.id.button_test_crash);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Test log!");
            }
        });

        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.getInstance().crash();
            }
        });
    }
}
