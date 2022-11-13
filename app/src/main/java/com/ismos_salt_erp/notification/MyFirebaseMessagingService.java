package com.ismos_salt_erp.notification;

import android.app.PendingIntent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ismos_salt_erp.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.ismos_salt_erp.notification.NotificationUtil.IS_LOGGED_IN;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String TAG_ID = "MyFirebaseIIDService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data payload : " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }


    /**
     * This method will display the notification
     * We are passing the JSONObject that is received from
     * firebase cloud messaging
     */
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            //JSONObject data = json.getJSONObject("data");

            //parsing json data
            String id = null, title = null, message = null, is_sms = null, image = null, status = null, notificationType = null, userType = null;

            if (json.has("id")) {
                id = json.getString("id").trim();
            }

            if (json.has("title")) {
                title = json.getString("title").trim();
            }

            if (json.has("message")) {
                message = json.getString("message").trim();
            }

            if (json.has("image")) {
                image = json.getString("image");
            }

            if (json.has("status")) {
                status = json.getString("status");
            }

            if (json.has("notification_type")) {
                notificationType = json.getString("notification_type");
            }

            if (json.has("user_type")) {
                userType = json.getString("user_type");
            }
            /**
             * Here is sms for detect this notification is for notification list or inbox
             */


            Notification notification = new Notification(id, title, message, image, status, notificationType, userType);

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());


            if (json.has("is_sms")) {
                is_sms = json.getString("is_sms");
            }
            PendingIntent pendingIntent = null;
            if (is_sms.equals("1")) {
                Bundle bundle = new Bundle();
                bundle.putString("pageName", "Inbox");
                bundle.putString("Item", "Inbox");
                pendingIntent = new NavDeepLinkBuilder(this)
                        .setGraph(R.navigation.nav_graph)
                        .setDestination(R.id.managementFragment)
                        .setArguments(bundle)
                        .createPendingIntent();
            }

            if (is_sms.equals("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("pageName", "Notification");
                pendingIntent = new NavDeepLinkBuilder(this)
                        .setGraph(R.navigation.nav_graph)
                        .setDestination(R.id.notificationFragment)
                        .setArguments(bundle)
                        .createPendingIntent();
            }


            // if user logged in the app
            if (IS_LOGGED_IN) {
                mNotificationManager.showSmallNotification(title, message, pendingIntent);
            }
            // user doesn't logged in the app
            else {
                mNotificationManager.showSmallNotification(title, message, pendingIntent);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
