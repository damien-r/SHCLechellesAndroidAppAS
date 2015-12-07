/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rossier.shclechelles.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rossier.data.MatchNotification;
import com.rossier.shclechelles.MainActivity;
import com.rossier.shclechelles.R;
import com.rossier.shclechelles.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";


    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        try {
            message = URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Log.e(TAG, "Fail to convert to UTF-8");
        }

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(from, message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String from, String message) {
        Gson gson = new GsonBuilder().create();
        String messageUTF8 = "";
        try {
            messageUTF8 = new String(message.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("MyGCMListenerService", message);
        Log.i("MyGCMListenerService", messageUTF8);
        if (messageUTF8 == "") return;
        MatchNotification match = gson.fromJson(messageUTF8, MatchNotification.class);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String text = String.format("%s %d-%d %s (%s)", match.getTeam_home(), match.getResult_home(),
                match.getResult_away(), match.getTeam_away(), match.getLigue());
        int icon = R.drawable.ic_launcher_shc;
        long when = System.currentTimeMillis();
        // Notification notification = new Notification(icon, "Nouveaux résultats", when);
        Notification notification = new Notification.Builder(this.getBaseContext())
                .setContentText(text)
                .setSmallIcon(icon)
                .setWhen(when)
                .build();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.match_notif_layout);
        contentView.setTextViewText(R.id.notif_team_home, match.getTeam_home());
        contentView.setTextViewText(R.id.notif_team_away, match.getTeam_away());
        contentView.setTextViewText(R.id.notif_result_home, match.getResult_home() + "");
        contentView.setTextViewText(R.id.notif_result_away, match.getResult_away() + "");
        contentView.setTextViewText(R.id.notif_ligue, match.getLigue());
        contentView.setImageViewResource(R.id.notif_team_loc_logo, Utils.getLogo(match.getTeam_home()));
        contentView.setImageViewResource(R.id.notif_team_away_logo, Utils.getLogo(match.getTeam_away()));
        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        //notification.flags |= Notification.FLAG_ONGOING_EVENT; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        //get topics name
        String[] topic = from.split("/");

        mNotificationManager.notify(topic[2].hashCode(), notification);
    }
}