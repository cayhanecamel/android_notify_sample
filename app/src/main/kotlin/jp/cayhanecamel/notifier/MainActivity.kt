package jp.cayhanecamel.notifier

import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val PICK_UP_PHOTO = 1

    var imagePath = ""

    var currentStyle = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(jp.cayhanecamel.notifier.R.layout.activity_main)


        val sp = PreferenceManager.getDefaultSharedPreferences(App.get())

        contentTitle.setText(sp.getString("content_title", "content_title"))
        contentText.setText(sp.getString("content_text", "content_text"))
        ticker.setText(sp.getString("ticker", "ticker"))
        bigContentTitle.setText(sp.getString("big_content_title", "big_content_titleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"))
        bigText.setText(sp.getString("big_text", "big_textttttttttttttttttttttttttttttttttttttttttttttttt"))
        summaryText.setText(sp.getString("summary_text", "summary_texttttttttttttttttttttttttttttt"))
        imagePath = sp.getString("image_path", "")

        if (imagePath.isNotEmpty()) {
            Glide.with(App.get())
                    .load(imagePath)
                    .into(bigPicture)
        } else {
            Glide.with(App.get())
                    .load(R.drawable.big_picture)
                    .into(bigPicture)
        }


        imagePickbutton.setOnClickListener {
            ImagePicker
                    .create(this)
                    .single()
                    .imageTitle(getString(R.string.profile_choose_photo))
                    .start(PICK_UP_PHOTO)
        }

        showNotification.setOnClickListener {
            showNotification()
        }

        val spinnerItems = arrayOf("None", "Big Picture", "Big Text", "Inbox")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.adapter = adapter


        styleSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val spinner = parent as Spinner
                val item = spinner.selectedItem as String

                if (item == "None") {
                    currentStyle = 0
                } else if (item == "Big Picture") {
                    currentStyle = 1
                    bigPictureLayout.visibility = View.VISIBLE
                    summaryLayout.visibility = View.VISIBLE
                    bigContentLayout.visibility = View.GONE
                    bigTextLayout.visibility = View.GONE
                    addLineLayout.visibility = View.GONE


                } else if (item == "Big Text") {
                    currentStyle = 2
                    bigPictureLayout.visibility = View.GONE
                    summaryLayout.visibility = View.VISIBLE
                    bigContentLayout.visibility = View.VISIBLE
                    bigTextLayout.visibility = View.VISIBLE
                    addLineLayout.visibility = View.GONE

                } else if (item == "Inbox") {
                    currentStyle = 3
                    bigPictureLayout.visibility = View.GONE
                    summaryLayout.visibility = View.GONE
                    bigContentLayout.visibility = View.GONE
                    bigTextLayout.visibility = View.GONE
                    addLineLayout.visibility = View.VISIBLE

                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
        styleSpinner.setSelection(sp.getInt("style", 0))

    }


    private fun showNotification() {
        val sp = PreferenceManager.getDefaultSharedPreferences(App.get())

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

        if (currentStyle == 1) {
            val r = App.get().resources
            val style = NotificationCompat.BigPictureStyle()


            if (imagePath.isNotEmpty()) {
                style.bigPicture(BitmapFactory.decodeFile(imagePath))
            } else {
                style.bigPicture(BitmapFactory.decodeResource(r, R.drawable.big_picture))
            }

            if (summaryText.text.isNotEmpty()) {
                style.setSummaryText(summaryText.text)
            }
            builder.setStyle(style)



        } else if (currentStyle == 2) {
            val style = NotificationCompat.BigTextStyle()
            if (bigContentTitle.text.isNotEmpty()) {
                style.setBigContentTitle(bigContentTitle.text)
            }
            if (summaryText.text.isNotEmpty()) {
                style.setSummaryText(summaryText.text)
            }
            if (bigText.text.isNotEmpty()) {
                style.bigText(bigText.text)
            }
            builder.setStyle(style)




        } else if (currentStyle == 3) {
            val style = NotificationCompat.InboxStyle()
            addLine.text.split("\n").forEach { line ->
                style.addLine(line)
            }
            builder.setStyle(style)
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

    override fun onPause() {
        super.onPause()
        save()
    }

    private fun save() {
        val sp = PreferenceManager.getDefaultSharedPreferences(App.get())
        sp.edit().putString("content_title", contentTitle.text.toString()).commit()
        sp.edit().putString("content_text", contentText.text.toString()).commit()
        sp.edit().putString("ticker", ticker.text.toString()).commit()
        sp.edit().putString("big_content_title", bigContentTitle.text.toString()).commit()
        sp.edit().putString("big_text", bigText.text.toString()).commit()
        sp.edit().putString("summary_text", summaryText.text.toString()).commit()
        sp.edit().putString("image_path", imagePath).commit()
        sp.edit().putInt("style", currentStyle).commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        if (!(requestCode == PICK_UP_PHOTO && resultCode == Activity.RESULT_OK)) return

        val images = ImagePicker.getImages(intent) as ArrayList<Image>

        imagePath = images.first().path
        Glide.with(App.get())
                .load(imagePath)
                .into(bigPicture)

    }
}
