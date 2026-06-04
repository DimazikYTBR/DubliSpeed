package com.example.dublispeed;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "DubliSpeed";
    private TextView speedValue;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedValue = findViewById(R.id.speedValue);
        rootLayout = findViewById(R.id.rootLayout);
        Button btnSpeedTest = findViewById(R.id.btnSpeedTest);
        Button btnFixInternet = findViewById(R.id.btnFixInternet);

        btnSpeedTest.setOnClickListener(v -> {
            speedValue.setText("...");
            startSpeedTest();
        });

        btnFixInternet.setOnClickListener(v -> {
            speedValue.setText("Fixing...");
            updateBackground("GOOD");
            Log.d(TAG, "Сеть оптимизирована");
        });
    }

    private void startSpeedTest() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                final String result = "120 Mbps"; 
            
                runOnUiThread(() -> {
                    TextView speedValue = findViewById(R.id.speedValue);
                    speedValue.setText(result);
                    updateBackground("GOOD"); 
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateBackground(String status) {
        int colorFrom = Color.WHITE;
        if (rootLayout.getBackground() instanceof ColorDrawable) {
            colorFrom = ((ColorDrawable) rootLayout.getBackground()).getColor();
        }

        int colorTo;
        switch (status) {
            case "GOOD": colorTo = Color.WHITE; break;
            case "MEDIUM": colorTo = Color.parseColor("#797979"); break;
            case "LOST": colorTo = Color.parseColor("#8B0000"); break;
            default: colorTo = Color.WHITE;
        }

        animateBackgroundColor(colorFrom, colorTo);
    }

    public void animateBackgroundColor(int fromColor, int toColor) {
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(
                rootLayout, 
                "backgroundColor", 
                new ArgbEvaluator(), 
                fromColor, 
                toColor
        );
        colorAnimator.setDuration(500);
        colorAnimator.start();
    }
}