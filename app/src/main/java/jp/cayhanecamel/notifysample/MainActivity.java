package jp.cayhanecamel.notifysample;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jp.cayhanecamel.notifysample.R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        EditText ticker;
        EditText contentTitle;
        EditText contentText;
        EditText bigContentTitle;
        EditText summaryText;
        CheckBox bigPickture;
        CheckBox wearableBackgroundImage;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            rootView.findViewById(R.id.showNotification).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNotification();
                }
            });

            ticker = (EditText) rootView.findViewById(R.id.ticker);
            contentTitle = (EditText) rootView.findViewById(R.id.contentTitle);
            contentText = (EditText) rootView.findViewById(R.id.contentText);
            bigContentTitle = (EditText) rootView.findViewById(R.id.bigContentTitle);
            summaryText = (EditText) rootView.findViewById(R.id.summaryText);
            bigPickture = (CheckBox) rootView.findViewById(R.id.bigPickture);
            wearableBackgroundImage = (CheckBox) rootView.findViewById(R.id.wearableBackgroundImage);


            return rootView;
        }

        private void showNotification() {
            Intent newIntent = new Intent(App.get(), MainActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.putExtra("isLaunchByNotification", true);

            NotificationDto dto = new NotificationDto();

            if (!TextUtils.isEmpty(ticker.getText())) {
                dto.ticker = ticker.getText().toString();
            }

            if (!TextUtils.isEmpty(contentTitle.getText())) {
                dto.contentTitle = contentTitle.getText().toString();
            }

            if (!TextUtils.isEmpty(contentText.getText())) {
                dto.contentText = contentText.getText().toString();
            }

            if (!TextUtils.isEmpty(bigContentTitle.getText())) {
                dto.bigContentTitle = bigContentTitle.getText().toString();
            }

            if (!TextUtils.isEmpty(summaryText.getText())) {
                dto.summaryText = summaryText.getText().toString();
            }


            dto.intent = newIntent;
            dto.id = 100;

            Resources r = App.get().getResources();
            if (bigPickture.isChecked()) {
                dto.bigPicture = BitmapFactory.decodeResource(r, R.drawable.big_picture);
            }

            if (wearableBackgroundImage.isChecked()) {
                dto.wearableBackgroundImage = BitmapFactory.decodeResource(r, R.drawable.wearable_background);
            }


            NotificationUtil.show(dto);
        }
    }
}
