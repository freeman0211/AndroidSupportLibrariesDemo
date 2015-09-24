package com.freeman.example.apitest.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.freeman.example.apitest.R;

/**
 * Created by freeman on 9/19/15.
 */
public class NotificationHelper {

    public static Notification createNotificationByCompat (Context context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.163.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder.setContentIntent(pendingIntent);

        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        builder.setContentTitle("NotificationCompat Title");
        builder.setContentText("NotificationCompat Text");
        builder.setSubText("NotificationCompat Sub Text");

        return builder.build();
    }

    public static Notification createNotification (Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder.setContentIntent(pendingIntent);

        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        builder.setContentTitle("Notification Title");
        builder.setContentText("Notification Text");
//        builder.setSubText("Notification Sub Text");
        return builder.build();
    }

    public static void sendNotification (NotificationManager manager, Notification notification) {
        manager.notify(1000, notification);
    }
}
