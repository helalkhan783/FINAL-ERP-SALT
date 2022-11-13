package com.ismos_salt_erp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.core.app.NotificationCompat;


import com.ismos_salt_erp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class MyNotificationManager {
    public static final int ID_BIG_NOTIFICATION = 234; // big notification id
    public static final int ID_SMALL_NOTIFICATION = 235; // small notification id

    public static final String NOTIFICATION_CHANNEL_1 = "mchannel1"; // notification channel

    private Context mCtx;

    /**
     * Constructor
     *
     * @param mCtx - activity context
     */
    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    //the method will show a big notification with an image
    //parameters are title for message title, message for message text, url of the big image and an intent that will open
    //when you will tap on the notification
    public void showBigNotification(String title, String message, String url, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_BIG_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationChannel mChannel = null;

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        android.app.Notification notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setContentText(HTML_TO_PLAIN_TEXT(message))
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setChannelId(NOTIFICATION_CHANNEL_1)
                .build();

        notification.flags |= android.app.Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_1, mCtx.getString(R.string.app_name), importance);
            // Configure the notification channel.
            mChannel.setDescription(message);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        } else {
            mBuilder.setContentTitle(title)
                    .setContentText(HTML_TO_PLAIN_TEXT(message))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
        }

        int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // random number generator (never repeat)

        assert notificationManager != null;
        notificationManager.notify(notificationId, notification);
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    public void showSmallNotification(String title, String message,PendingIntent intent) {
      /*  PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );*/
        //Pending/**/

        NotificationChannel mChannel = null;

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if (alarmSound == null) {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (alarmSound == null) {
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }


        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();


        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        mBuilder.setSmallIcon(R.drawable.notificationicon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setSound(Uri.parse("android.resource://"+mCtx.getPackageName()+"/raw/notification.mp3"))
                .setSmallIcon(R.drawable.notificationicon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.erp_exit1))
                .setContentTitle(title)
                .setVibrate(new long[]{0, 500, 1000})
                .setDefaults(Notification.DEFAULT_LIGHTS )
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setContentText(HTML_TO_PLAIN_TEXT(message))
                /*.setStyle(new NotificationCompat.BigTextStyle() // big text style
                        .bigText(ExtraUtils.HTML_TO_PLAIN_TEXT(message))
                        .setBigContentTitle(title)
                )*/
                .setChannelId(NOTIFICATION_CHANNEL_1);

        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_1, mCtx.getString(R.string.app_name), importance);
            // Configure the notification channel.
            mChannel.setDescription(message);
            mChannel.enableLights(true);
            mChannel.setSound(Uri.parse("android.resource://"+mCtx.getPackageName()+"/raw/notification.mp3"),att);
            mChannel.setLightColor(Color.RED);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        } else {
            mBuilder.setContentTitle(title)
                    .setWhen(Calendar.getInstance().getTimeInMillis())
                    .setSound(Uri.parse("android.resource://"+mCtx.getPackageName()+"/raw/notification.mp3"))
                    .setContentText(HTML_TO_PLAIN_TEXT(message))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
        }

        int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // random number generator (never repeat)

        assert notificationManager != null;
        notificationManager.notify(notificationId, mBuilder.build());
    }



//    //the method will show a small notification
//    //parameters are title for message title, message for message text and an intent that will open
//    //when you will tap on the notification
//    public void showSmallNotification(String title, String message, PendingIntent intent) {
//       /* PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        mCtx,
//                        ID_SMALL_NOTIFICATION,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );*/
//
//        NotificationChannel mChannel = null;
//
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//        mBuilder.setSmallIcon(R.drawable.notificationicon).setTicker(title).setWhen(0)
//                .setAutoCancel(true)
//                .setContentIntent(intent)
//                .setVibrate(new long[]{0, 500, 1000})
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(),R.mipmap.ic_launcher))
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                /*.setStyle(new NotificationCompat.BigTextStyle() // big text style
//                        .bigText(ExtraUtils.HTML_TO_PLAIN_TEXT(message))
//                        .setBigContentTitle(title)
//                )*/
//                .setChannelId(NOTIFICATION_CHANNEL_1);
//
//        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
//
//        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_1, mCtx.getString(R.string.app_name), importance);
//            // Configure the notification channel.
//            mChannel.setDescription(message);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            assert notificationManager != null;
//            notificationManager.createNotificationChannel(mChannel);
//        } else {
//            mBuilder.setContentTitle(title)
//                    .setContentText(message)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setVibrate(new long[]{0, 500, 1000})
//                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setAutoCancel(true);
//        }
//
//        int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); // random number generator (never repeat)
//
//        assert notificationManager != null;
//        notificationManager.notify(notificationId, mBuilder.build());
//    }




    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Spanned HTML_TO_PLAIN_TEXT(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }
}
