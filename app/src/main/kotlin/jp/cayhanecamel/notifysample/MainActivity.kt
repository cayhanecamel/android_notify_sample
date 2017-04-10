package jp.cayhanecamel.notifysample

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.ActionBarActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*


class MainActivity : ActionBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(jp.cayhanecamel.notifysample.R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, PlaceholderFragment())
                    .commit()
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {


        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View {
            val rootView = inflater!!.inflate(R.layout.fragment_main, container, false)

            rootView.findViewById(R.id.showNotification).setOnClickListener { showNotification() }

            return rootView
        }

        private fun showNotification() {

            // Intent の作成
            val contentIntent = PendingIntent.getActivity(
                    App.get(),
                    1000,
                    Intent(App.get(), NextActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT)

            // LargeIcon の Bitmap を生成
            val largeIcon = BitmapFactory.decodeResource(App.get().resources, R.drawable.ic_launcher)

            // NotificationBuilderを作成
            val builder = NotificationCompat.Builder(App.get())
            builder.setContentIntent(contentIntent)

            builder.setSmallIcon(R.drawable.ic_launcher)
            builder.setLargeIcon(largeIcon)

            if (contentTitle.text.isNotEmpty()) {
                builder.setContentTitle(contentTitle.text)
            }

            if (contentText.text.isNotEmpty()) {
                builder.setContentText(contentText.text)
            }

            if (ticker.text.isNotEmpty()) {
                builder.setTicker(ticker.text)
            }

            if (bigPictureRadio.isChecked) {
                val r = App.get().resources
                val style = NotificationCompat.BigPictureStyle()
                style.bigPicture(BitmapFactory.decodeResource(r, R.drawable.big_picture))
                if (summaryText.text.isNotEmpty()) {
                    style.setSummaryText(summaryText.text)
                }
                builder.setStyle(style)

            } else if (bigTextRadio.isChecked) {
                val style = NotificationCompat.BigTextStyle()
                if (bigContentTitle.text.isNotEmpty()) {
                    style.setBigContentTitle(bigContentTitle.text)
                }
                if (summaryText.text.isNotEmpty()) {
                    style.setSummaryText(summaryText.text)
                }
                if(bigText.text.isNotEmpty()){
                    style.bigText(bigText.text)
                }
                builder.setStyle(style)

            } else if (inboxRadio.isChecked) {

            }


            // 通知するタイミング
            builder.setWhen(System.currentTimeMillis())
            // 通知時の音・バイブ・ライト
            builder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS)
            builder.setAutoCancel(true)


            // NotificationManagerを取得
            val notificationManager = NotificationManagerCompat.from(App.get())
            // Notificationを作成して通知
            notificationManager.notify(1000, builder.build())

        }

    }
}
