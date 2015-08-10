package com.contentws.cws.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.contentws.cws.Config;
import com.contentws.cws.Data.Content;
import com.contentws.cws.R;

public class WebBrowserActivity extends Activity
        implements SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter simpleGestureFilter;
    private Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        simpleGestureFilter = new SimpleGestureFilter(this, this);

        Intent intent = getIntent();
        content = new Content(intent);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl(intent.getStringExtra(Config.CONTENT_URL));
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        this.simpleGestureFilter.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    public void onSwipe(int direction) {
        String str = "";

        switch(direction) {
            case SimpleGestureFilter.SWIPE_LEFT:
                str = "Swipe Left";
                moveLeft();
                break;

            case SimpleGestureFilter.SWIPE_RIGHT:
                str = "Swipe Right";
                moveRight();
                break;

            case SimpleGestureFilter.SWIPE_UP:
                str = "Swipe Up";
                break;

            case SimpleGestureFilter.SWIPE_DOWN:
                str = "Swipe Down";
                break;
        }

        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }

    public void moveLeft() {
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra(Config.CONTENT_ID, content.getConetntId());
        intent.putExtra(Config.CONTENT_URL, content.getContentURL());
        intent.putExtra(Config.TITLE, content.getTitle());
        intent.putExtra(Config.DESCRIPTION, content.getDescription());
        intent.putExtra(Config.OG_SITE_NAME, content.getOgSiteName());
        //intent.putExtra(Config.CATEGORY, content.getCategory());
        intent.putExtra(Config.IMAGE_URL, Config.SERVER_URL + "/" + content.getImageURL());
        intent.putExtra(Config.NEW_ACTIVITY, true);
        startActivity(intent);
    }

    public void moveRight() {
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra(Config.CONTENT_ID, content.getConetntId());
        intent.putExtra(Config.CONTENT_URL, content.getContentURL());
        intent.putExtra(Config.TITLE, content.getTitle());
        intent.putExtra(Config.DESCRIPTION, content.getDescription());
        intent.putExtra(Config.OG_SITE_NAME, content.getOgSiteName());
        //intent.putExtra(Config.CATEGORY, content.getCategory());
        intent.putExtra(Config.IMAGE_URL, Config.SERVER_URL + "/" + content.getImageURL());
        intent.putExtra(Config.NEW_ACTIVITY, true);
        startActivity(intent);
    }
}
