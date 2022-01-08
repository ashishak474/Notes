package com.ak.notes.activity;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends com.google.firebase.messaging.FirebaseMessagingService {

    public FirebaseMessaging() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
