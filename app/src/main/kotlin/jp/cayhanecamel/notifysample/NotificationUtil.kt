package jp.cayhanecamel.notifysample

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat


object NotificationUtil {

    fun show(dto: NotificationDto) {

        // Intent の作成
        if (dto.intent == null) {
            dto.intent = Intent(App.get(), MainActivity::class.java)
        }
        val contentIntent = PendingIntent.getActivity(
                App.get(), dto.id, dto.intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // LargeIcon の Bitmap を生成
        val largeIcon = BitmapFactory.decodeResource(App.get().resources, R.drawable.ic_launcher)

        // NotificationBuilderを作成
        val builder = NotificationCompat.Builder(App.get())
        builder.setContentIntent(contentIntent)

        builder.setSmallIcon(R.drawable.ic_launcher)
        builder.setLargeIcon(largeIcon)
        if (dto.contentTitle != null) {
            builder.setContentTitle(dto.contentTitle)
        }

        if (dto.contentText != null) {
            builder.setContentText(dto.contentText)
        }

        if (dto.ticker != null) {
            builder.setTicker(dto.ticker)
        }

        // 写真設定
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        if (dto.bigPicture != null) {
            bigPictureStyle.bigPicture(dto.bigPicture)
            if (dto.bigContentTitle != null) {
                bigPictureStyle.setBigContentTitle(dto.bigContentTitle)
                builder.setStyle(NotificationCompat.BigTextStyle())
            }
            if (dto.summaryText != null) {
                bigPictureStyle.setSummaryText(dto.summaryText)
            }
            //            builder.setStyle(bigPictureStyle);

        } else {
            if (dto.wearableBackgroundImage != null) {
                val wearableExtender = NotificationCompat.WearableExtender()
                wearableExtender.background = dto.wearableBackgroundImage
                builder.extend(wearableExtender)
            }
        }

        // 通知するタイミング
        builder.setWhen(System.currentTimeMillis())
        // 通知時の音・バイブ・ライト
        builder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
        builder.setAutoCancel(true)


        // NotificationManagerを取得
        val notificationManager = NotificationManagerCompat.from(App.get())
        // Notificationを作成して通知
        notificationManager.notify(dto.id, builder.build())

    }
}
