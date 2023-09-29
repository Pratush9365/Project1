package com.pratush.alramclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.pratush.alramclock.databinding.ActivityMainBinding;

import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;
    private TextView dateTextView;
    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());


        createNotificationChannel();


        // Initialize the views
        timeTextView = findViewById(R.id.timeTextView);
        dateTextView = findViewById(R.id.dateTextView);
                    //setAlarmButton = findViewById(R.id.setAlarmButton);
         binding.setAlarmButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showtimepicker();

             }
         });
         binding.cancelButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
               SetAlarm();
             }
         });
         binding.cabutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 CancelAlarm();

             }
         });
        // Set an OnClickListener for the "Set Alarm" button
        //  setAlarmButton.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View view) {
        // Implement the logic to navigate to the alarm setting screen
        // For example, you can start a new activity or fragment here
        //  }
        //});

        // Update the current time and date
        updateDateTime();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void CancelAlarm() {
        Intent intent =new Intent(this,onrecevier.class);
        pendingIntent=PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmManager==null){
            alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm is Cancel", Toast.LENGTH_SHORT).show();
    }

    private void SetAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(this,onrecevier.class);
        pendingIntent=PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this,"Alarm Set Successfully",Toast.LENGTH_SHORT).show();




    }

    private void showtimepicker() {
        picker =new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(),"pratush");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if (picker.getHour()>12){
                    binding.setAlarmButton.setText(
                             String.format("%02d",(picker.getHour()-12))+":"+String.format("%02d",picker.getMinute())+"PM"
                    );
                }else {
                    binding.setAlarmButton.setText(picker.getHour()+" : "+picker.getMinute()+" AM");

                }
                calendar =Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

            }
        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="pratush channel";
             String description = "channel for Alarm Manger";
             int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("pratush",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);



        }
    }

    private void updateDateTime() {
        // Get the current time and date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

        // Update the TextViews with the current time and date
        String currentTime = timeFormat.format(calendar.getTime());
        String currentDate = dateFormat.format(calendar.getTime());

        timeTextView.setText(currentTime);
        dateTextView.setText(currentDate);
    }
}

