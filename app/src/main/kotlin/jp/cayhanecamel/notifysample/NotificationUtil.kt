package jp.cayhanecamel.notifysample

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat


object NotificationUtil {

    fun show(data: NotificationData) {

        // Intent の作成
        if (data.intent == null) {
            data.intent = Intent(App.get(), MainActivity::class.java)
        }
        val contentIntent = PendingIntent.getActivity(
                App.get(), data.id, data.intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // LargeIcon の Bitmap を生成
        val largeIcon = BitmapFactory.decodeResource(App.get().resources, R.drawable.ic_launcher)

        // NotificationBuilderを作成
        val builder = NotificationCompat.Builder(App.get())
        builder.setContentIntent(contentIntent)

        builder.setSmallIcon(R.drawable.ic_launcher)
        builder.setLargeIcon(largeIcon)
        if (data.contentTitle != null) {
            builder.setContentTitle(data.contentTitle)
        }

        if (data.contentText != null) {
            builder.setContentText(data.contentText)
        }

        if (data.ticker != null) {
            builder.setTicker(data.ticker)
        }

        // 写真設定
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        if (data.bigPicture != null) {
            bigPictureStyle.bigPicture(data.bigPicture)
            if (data.bigContentTitle != null) {
                bigPictureStyle.setBigContentTitle(data.bigContentTitle)
                builder.setStyle(NotificationCompat.BigTextStyle())
            }
            if (data.summaryText != null) {
                bigPictureStyle.setSummaryText(data.summaryText)
            }
            //            builder.setStyle(bigPictureStyle);

        } else {
            if (data.wearableBackgroundImage != null) {
                val wearableExtender = NotificationCompat.WearableExtender()
                wearableExtender.background = data.wearableBackgroundImage
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
        notificationManager.notify(data.id, builder.build())

    }
}
