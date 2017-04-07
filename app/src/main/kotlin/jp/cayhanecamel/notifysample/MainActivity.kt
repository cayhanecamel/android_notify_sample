package jp.cayhanecamel.notifysample

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarActivity
import android.text.TextUtils
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
            val newIntent = Intent(App.get(), MainActivity::class.java)
            newIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            newIntent.putExtra("isLaunchByNotification", true)

            val data = NotificationData()

            if (!TextUtils.isEmpty(ticker.text)) {
                data.ticker = ticker.text.toString()
            }

            if (!TextUtils.isEmpty(contentTitle.text)) {
                data.contentTitle = contentTitle.text.toString()
            }

            if (!TextUtils.isEmpty(contentText.text)) {
                data.contentText = contentText.text.toString()
            }

            if (!TextUtils.isEmpty(bigContentTitle.text)) {
                data.bigContentTitle = bigContentTitle.text.toString()
            }

            if (!TextUtils.isEmpty(summaryText.text)) {
                data.summaryText = summaryText.text.toString()
            }


            data.intent = newIntent
            data.id = 100

            val r = App.get().resources
            if (bigPicture.isChecked) {
                data.bigPicture = BitmapFactory.decodeResource(r, R.drawable.big_picture)
            }

            if (wearableBackgroundImage.isChecked) {
                data.wearableBackgroundImage = BitmapFactory.decodeResource(r, R.drawable.wearable_background)
            }


            NotificationUtil.show(data)
        }
    }
}
