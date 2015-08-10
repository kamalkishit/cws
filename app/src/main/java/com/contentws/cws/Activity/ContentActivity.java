package com.contentws.cws.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.contentws.cws.Config;
import com.contentws.cws.ContentNavigator;
import com.contentws.cws.Data.Content;
import com.contentws.cws.FragmentDrawer;
import com.contentws.cws.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class ContentActivity extends AppCompatActivity
        implements SimpleGestureFilter.SimpleGestureListener {

    private final String FILENAME = "com.contentws.contentws.ContentActivity";

    private SimpleGestureFilter simpleGestureFilter;
    private TextView title;
    private TextView description;
    private ImageView imageView;
    private Content content;
    private ContentNavigator contentNavigator;
    private int imageWidth;
    private int imageHeight;
    private TextView source;
    private Button category;
    private ImageButton menuButton;
    private ImageButton moreButton;
    private ImageButton shareButton;
    private ImageButton bookmarkButton;
    private ImageButton likeButton;
    private ImageButton dislikeButton;
    private FragmentDrawer fragmentDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        boolean isNewActivity = false;

        initialize();

        // swipe-right, swipe-left
        if (intent.getBooleanExtra(Config.NEW_ACTIVITY, isNewActivity)) {
            title.setText(intent.getStringExtra(Config.TITLE));
            description.setText(intent.getStringExtra(Config.DESCRIPTION));
            source.setText(intent.getStringExtra(Config.OG_SITE_NAME));
            Picasso.with(this).load(intent.getStringExtra(Config.IMAGE_URL))
                    .resize(imageWidth, imageHeight).into(imageView);
            content = new Content(intent);
            update();
        } else {
            content = contentNavigator.getFirstContent();

            if (content != null) {
                title.setText(content.getTitle());
                description.setText(content.getDescription());
                source.setText(content.getOgSiteName());
                //category.setText(content.getCategory());
                imageView = (ImageView)findViewById(R.id.image);
                imageView.setImageResource(R.drawable.image);
                Picasso.with(this).load(Config.SERVER_URL + "/" + content.getImageURL())
                    .resize(imageWidth, imageHeight).into(imageView);
                update();
            }
        }
    }

    private void initialize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        imageWidth = metrics.widthPixels;
        imageHeight = (imageWidth*2)/3;

        Config.IMAGE_WIDTH = imageWidth;
        Config.IMAGE_HEIGHT = imageHeight;

        source = (TextView) findViewById( R.id.source );
        category = (Button) findViewById( R.id.category );
        menuButton = (ImageButton) findViewById( R.id.menu );
        moreButton = (ImageButton) findViewById( R.id.more );
        shareButton = (ImageButton) findViewById ( R.id.share );
       /* bookmarkButton = (ImageButton) findViewById( R.id.bookmark );
        likeButton = (ImageButton) findViewById( R.id.thumbs_up );
        dislikeButton = (ImageButton) findViewById( R.id.thumbs_down );*/

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.image);
        contentNavigator = ContentNavigator.getInstance();
        simpleGestureFilter = new SimpleGestureFilter(this, this);
    }

    private void update() {
        updateLikeButton();
        updateBookmarkButton();
    }

    public void share(View view) {
        String filename = "shareImage";
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        Uri imageUri = Uri.parse(path);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content.getContentURL());
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(shareIntent, "Share URL"));
    }

    public void openNavigationDrawer(View view) {

    }

    public void like(View view) {
        content.like();
        //updateLikeButton();
    }

    public void bookmark(View view) {
        content.bookmark();
        //updateBookmarkButton();
    }

    private void updateLikeButton() {

    }

    private void updateBookmarkButton() {

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
                moveUp();
                break;

            case SimpleGestureFilter.SWIPE_DOWN:
                str = "Swipe Down";
                moveDown();
                break;
        }
    }

    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }

    public void moveUp() {
        if (contentNavigator.canMoveForward()) {
            content = contentNavigator.getForwardContent();
            update();
            title.setText(content.getTitle());
            description.setText(content.getDescription());
            source.setText(content.getOgSiteName());
            //category.setText(content.getCategory());
            System.out.println(imageWidth);
            System.out.println(imageHeight);
            Picasso.with(this).load(Config.SERVER_URL + "/" + content.getImageURL())
                    .resize(imageWidth, imageHeight).into(imageView);
        }
    }

    public void moveDown() {
        System.out.println("i m inside movedown");
        if (contentNavigator.canMoveBackward()) {
            content = contentNavigator.getBackwardContent();
            update();
            title.setText(content.getTitle());
            description.setText(content.getDescription());
            source.setText(content.getOgSiteName());
            //category.setText(content.getCategory());
            Picasso.with(this).load(Config.SERVER_URL + "/" + content.getImageURL())
                    .resize(imageWidth, imageHeight).into(imageView);
        }
    }

    public void moveLeft() {
        Intent intent = new Intent(this, WebBrowserActivity.class);
        intent.putExtra(Config.CONTENT_ID, content.getConetntId());
        intent.putExtra(Config.CONTENT_URL, content.getContentURL());
        intent.putExtra(Config.TITLE, content.getTitle());
        intent.putExtra(Config.DESCRIPTION, content.getDescription());
        intent.putExtra(Config.OG_SITE_NAME, content.getOgSiteName());
        intent.putExtra(Config.IMAGE_URL, Config.SERVER_URL + "/" + content.getImageURL());
        //content.incrementViewCount();
        startActivity(intent);
    }

    public void moveRight() {
        Intent intent = new Intent(this, WebBrowserActivity.class);
        intent.putExtra(Config.CONTENT_ID, content.getConetntId());
        intent.putExtra(Config.CONTENT_URL, content.getContentURL());
        intent.putExtra(Config.TITLE, content.getTitle());
        intent.putExtra(Config.DESCRIPTION, content.getDescription());
        intent.putExtra(Config.OG_SITE_NAME, content.getOgSiteName());
        intent.putExtra(Config.IMAGE_URL, Config.SERVER_URL + "/" + content.getImageURL());
        //content.incrementViewCount();
        startActivity(intent);
    }
}
