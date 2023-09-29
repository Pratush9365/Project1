package com.pratush.alramclock;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class alarmsetting extends Activity {

    private TimePicker timePicker;
    private Button selectToneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmsetting); // Set your XML layout file here

        // Initialize views
        timePicker = findViewById(R.id.timePicker);
        selectToneButton = findViewById(R.id.selectToneButton);

        // Set a click listener for the selectToneButton
        selectToneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your logic to select an alarm tone here
                // For example, you can open a dialog to choose a tone
                openToneSelectionDialog();
            }
        });

        // Optionally, you can set a time change listener for the TimePicker
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Handle time changes here
            }
        });
    }

    private void openToneSelectionDialog() {
        // Implement your logic to open a dialog for tone selection here
        // For example, you can use AlertDialog or create a custom dialog.
    }
}

