package jp.cayhanecamel.notifysample

import android.content.Intent
import android.graphics.Bitmap


data class NotificationData(
    var contentTitle: String? = null,
    var contentText: String? = null,
    var ticker: String? = null,
    var intent: Intent? = null,
    var sysNotificationId: Int = 0,

    var wearableBackgroundImage: Bitmap? = null,
    var bigPicture: Bitmap? = null,
    var bigContentTitle: String? = null,
    var summaryText: String? = null,
    var id: Int = 0
)
