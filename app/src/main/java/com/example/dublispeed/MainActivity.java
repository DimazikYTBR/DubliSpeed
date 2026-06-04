package com.example.dublispeed;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "DubliSpeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Приложение запущено и готово к работе!");

        checkNetworkSpeed();
    }

    private void checkNetworkSpeed() {
        Log.d(TAG, "Начинаем замер скорости...");
    }

    public void updateBackground(String status) {
    RelativeLayout rootLayout = findViewById(R.id.rootLayout);
    
    switch (status) {
        case "GOOD":
            rootLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            break;
        case "MEDIUM":
            rootLayout.setBackgroundColor(Color.parseColor("#797979"));
            break;
        case "LOST":
            rootLayout.setBackgroundColor(Color.parseColor("#8B0000"));
            break;
        }
    }

    public void animateBackgroundColor(int fromColor, int toColor) {
    RelativeLayout rootLayout = findViewById(R.id.rootLayout);

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