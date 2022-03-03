package com.example.notificationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.os.BuildCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mNotifButton;
    private Button mUpdateButton;
    private Button mCancelButton;

    private static final String CHANNEL_ID = BuildConfig.APPLICATION_ID + "notification-channel";
    private NotificationManager mNotificationManager;
    private static final int NOTIF_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotifButton = findViewById(R.id.notif_btn);
        mUpdateButton = findViewById(R.id.update_btn);
        mCancelButton = findViewById(R.id.cancel_btn);

        mNotifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });

        mNotifButton.setEnabled(true);
        mCancelButton.setEnabled(false);
        mUpdateButton.setEnabled(false);
    }

    private void sendNotification(){
        Intent contentIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(getApplicationContext(),NOTIF_ID, contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder built = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        built.setContentTitle("You've been notified");
        built.setContentText("This is notification text");
        built.setSmallIcon(R.drawable.ic_notification);
        built.setContentIntent(pendingContentIntent);

        Notification notif = built.build();
        mNotificationManager.notify(NOTIF_ID, notif);

        mNotifButton.setEnabled(false);
        mCancelButton.setEnabled(true);
        mUpdateButton.setEnabled(true);
    }
    private void cancelNotification(){
        mNotificationManager.cancel(NOTIF_ID);

        mNotifButton.setEnabled(true);
        mCancelButton.setEnabled(false);
        mUpdateButton.setEnabled(false);
    }

    private void updateNotification(){
        Intent contentIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(getApplicationContext(),NOTIF_ID, contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder built = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        built.setContentTitle("You've been notified");
        built.setContentText("This is notification text");
        built.setSmallIcon(R.drawable.ic_notification);
        built.setContentIntent(pendingContentIntent);
        built.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Bitmap mascotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        built.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(mascotBitmap).setBigContentTitle("The Notification has been updated"));

        Notification notif = built.build();
        mNotificationManager.notify(NOTIF_ID, notif);
    }
}