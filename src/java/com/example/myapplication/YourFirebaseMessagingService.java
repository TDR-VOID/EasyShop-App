package com.example.myapplication;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class YourFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("FCM Token", token);
        // Handle token here
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Handle received messages
    }
}