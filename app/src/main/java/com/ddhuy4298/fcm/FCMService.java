package com.ddhuy4298.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;
import java.net.URLConnection;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "LAND1907E";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }
        String title = remoteMessage.getData().get("title");
        String text = remoteMessage.getData().get("body");

        NotificationCompat.Builder notification = new NotificationCompat.Builder(
                this, channelId
        );
        notification.setContentTitle(title);
        notification.setContentText(text);
        notification.setSmallIcon(R.mipmap.ic_launcher);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification.setContentIntent(pendingIntent);

        if (remoteMessage.getData().containsKey("image")) {
            String image = remoteMessage.getData().get("image");
            try {
                URL url = new URL(image);
                URLConnection connection = url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                notification.setLargeIcon(bitmap);

                notification.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(bitmap)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        manager.notify(4298, notification.build());
    }
}
