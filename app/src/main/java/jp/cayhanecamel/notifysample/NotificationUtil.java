package jp.cayhanecamel.notifysample;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class NotificationUtil {

    @SuppressWarnings("deprecation")
    public static void show(NotificationDto dto) {

        // Intent の作成
        if (dto.intent == null) {
            dto.intent = new Intent(App.get(), MainActivity.class);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(
                App.get(), dto.id, dto.intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // LargeIcon の Bitmap を生成
        Bitmap largeIcon = BitmapFactory.decodeResource(App.get().getResources(), R.drawable.ic_launcher);

        // NotificationBuilderを作成
        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.get());
        builder.setContentIntent(contentIntent);

        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(largeIcon);
        if (dto.contentTitle != null) {
            builder.setContentTitle(dto.contentTitle);
        }

        if (dto.contentText != null) {
            builder.setContentText(dto.contentText);
        }

        if (dto.ticker != null) {
            builder.setTicker(dto.ticker);
        }

        // 写真設定
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        if (dto.bigPicture != null) {
            bigPictureStyle.bigPicture(dto.bigPicture);
            if (dto.bigContentTitle != null) {
                bigPictureStyle.setBigContentTitle(dto.bigContentTitle);
                builder.setStyle(new NotificationCompat.BigTextStyle());
            }
            if (dto.summaryText != null) {
                bigPictureStyle.setSummaryText(dto.summaryText);
            }
//            builder.setStyle(bigPictureStyle);

        } else {
            if (dto.wearableBackgroundImage != null) {
                NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();
                wearableExtender.setBackground(dto.wearableBackgroundImage);
                builder.extend(wearableExtender);
            }
        }

        // 通知するタイミング
        builder.setWhen(System.currentTimeMillis());
        // 通知時の音・バイブ・ライト
        builder.setDefaults(Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS);
        builder.setAutoCancel(true);


        // NotificationManagerを取得
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(App.get());
        // Notificationを作成して通知
        notificationManager.notify(dto.id, builder.build());

    }
}
