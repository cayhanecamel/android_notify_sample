package jp.cayhanecamel.notifysample

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarActivity
import android.text.TextUtils
import android.view.*
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
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

            val dto = NotificationDto()

            if (!TextUtils.isEmpty(ticker.text)) {
                dto.ticker = ticker.text.toString()
            }

            if (!TextUtils.isEmpty(contentTitle.text)) {
                dto.contentTitle = contentTitle.text.toString()
            }

            if (!TextUtils.isEmpty(contentText.text)) {
                dto.contentText = contentText.text.toString()
            }

            if (!TextUtils.isEmpty(bigContentTitle.text)) {
                dto.bigContentTitle = bigContentTitle.text.toString()
            }

            if (!TextUtils.isEmpty(summaryText.text)) {
                dto.summaryText = summaryText.text.toString()
            }


            dto.intent = newIntent
            dto.id = 100

            val r = App.get().resources
            if (bigPicture.isChecked) {
                dto.bigPicture = BitmapFactory.decodeResource(r, R.drawable.big_picture)
            }

            if (wearableBackgroundImage.isChecked) {
                dto.wearableBackgroundImage = BitmapFactory.decodeResource(r, R.drawable.wearable_background)
            }


            NotificationUtil.show(dto)
        }
    }
}
