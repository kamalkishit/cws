package com.contentws.cws.Data;

import android.content.Intent;
import android.widget.ImageView;

import com.contentws.cws.Config;
import com.contentws.cws.HttpResponseCallback;
import com.contentws.cws.HttpUtil;
import com.contentws.cws.JSONObjectParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Content {
    private String conetntId;
    private String userId;
    private String contentType;
    private String contentURL;
    private String ogContentURL;
    private String title;
    private String ogTitle;
    private String description;
    private String ogDescription;
    private String ogSiteName;
    private String imageURL;
    private String ogImageURL;
    private String category;
    private ArrayList<String> subCategory;
    private Date originalDate;
    private int likesCount;
    private boolean isLiked;
    private boolean isBookmarked;
    private int viewCount;
    private String createdAt;
    private Date lastModifiedDate;
    private ImageView imageView;

    public Content() {
        this.conetntId = null;
        this.userId = null;
        this.contentType = null;
        this.ogContentURL = null;
        this.contentURL = null;
        this.ogTitle = null;
        this.title = null;
        this.ogDescription = null;
        this.description = null;
        this.ogSiteName = null;
        this.ogImageURL = null;
        this.imageURL = null;
        this.category = null;
        this.subCategory = null;
        this.ogImageURL = null;
        this.originalDate = new Date();
        this.createdAt = null;
        this.lastModifiedDate = new Date();
        this.likesCount = 0;
        this.viewCount = 0;
        this.isLiked = false;
        this.isBookmarked = false;
    }

    public void reset() {
        this.conetntId = null;
        this.userId = null;
        this.contentType = null;
        this.ogContentURL = null;
        this.contentURL = null;
        this.ogTitle = null;
        this.title = null;
        this.ogDescription = null;
        this.description = null;
        this.ogSiteName = null;
        this.ogImageURL = null;
        this.imageURL = null;
        this.category = null;
        this.subCategory = null;
        this.ogImageURL = null;
        this.originalDate = new Date();
        this.createdAt = null;
        this.lastModifiedDate = new Date();
        this.likesCount = 0;
        this.viewCount = 0;
        this.isLiked = false;
        this.isBookmarked = false;
    }

    public Content(Content content) {
        this.conetntId = content.conetntId;
        this.userId = content.userId;
        this.contentURL = content.contentURL;
        this.ogContentURL = content.ogContentURL;
        this.contentType = content.contentType;
        this.ogTitle = content.ogTitle;
        this.title = content.title;
        this.ogDescription = content.ogDescription;
        this.description = content.description;
        this.ogSiteName = content.ogSiteName;
        this.ogImageURL = content.ogImageURL;
        this.imageURL = content.imageURL;
        this.category = content.category;
        this.subCategory = content.subCategory;
        this.originalDate = content.originalDate;
        this.createdAt = content.createdAt;
        this.lastModifiedDate = content.lastModifiedDate;
        this.viewCount = content.viewCount;
        this.likesCount = content.likesCount;
        this.isLiked = content.isLiked;
        this.isBookmarked = content.isBookmarked;
    }

    public Content(Intent intent) {
        this.conetntId = intent.getStringExtra(Config.CONTENT_ID);
        this.userId = intent.getStringExtra(Config.USER_ID);
        this.contentType = intent.getStringExtra(Config.CONTENT_TYPE);
        this.title = intent.getStringExtra(Config.TITLE);
        this.description = intent.getStringExtra(Config.DESCRIPTION);
        this.ogSiteName = intent.getStringExtra(Config.OG_SITE_NAME);
        this.ogImageURL = intent.getStringExtra(Config.IMAGE_URL);
        this.contentURL = intent.getStringExtra(Config.CONTENT_URL);
        this.category = intent.getStringExtra(Config.CATEGORY);
        this.subCategory = new ArrayList<String> (Arrays.asList(intent.getStringArrayExtra(Config.SUB_CATEGORY)));
        this.viewCount = intent.getIntExtra(Config.VIEW_COUNT, 0);
        this.likesCount = intent.getIntExtra(Config.LIKES_COUNT, 0);
        this.isLiked = intent.getBooleanExtra(Config.IS_LIKED, false);
        this.isBookmarked = intent.getBooleanExtra(Config.IS_BOOKMARKED, false);
        this.createdAt = intent.getStringExtra(Config.CREATED_AT);
    }

    public void updateContent(JSONObject jsonObject) {
        this.setConetntId(JSONObjectParser.getString(jsonObject, Config.CONTENT_ID));
        this.setUserId(JSONObjectParser.getString(jsonObject, Config.USER_ID));
        this.setContentType(JSONObjectParser.getString(jsonObject, Config.CONTENT_TYPE));
        this.setContentURL(JSONObjectParser.getString(jsonObject, Config.CONTENT_URL));
        this.setTitle(JSONObjectParser.getString(jsonObject, Config.OG_TITLE));
        this.setDescription(JSONObjectParser.getString(jsonObject, Config.OG_DESCRIPTION));
        this.setOgImageURL(JSONObjectParser.getString(jsonObject, Config.OG_IMAGE_URL));
        this.setImageURL(JSONObjectParser.getString(jsonObject, Config.IMAGE_URL));
        this.setOgSiteName(JSONObjectParser.getString(jsonObject, Config.OG_SITE_NAME));
        this.setCategory(JSONObjectParser.getString(jsonObject, Config.CATEGORY));
        this.setSubCategory(JSONObjectParser.getStringArray(jsonObject, Config.SUB_CATEGORY));
        this.setViewCount(JSONObjectParser.getInt(jsonObject, Config.VIEW_COUNT));
        this.setLikesCount(JSONObjectParser.getInt(jsonObject, Config.LIKES_COUNT));
        this.setIsLiked(User.getInstance().isLiked(this.conetntId));
        this.setIsBookmarked(User.getInstance().isBookmarked(this.conetntId));
        this.setCreatedAt(JSONObjectParser.getString(jsonObject, Config.CREATED_AT));
        //Picasso.with(GlobalState.getInstance().getApplication()).load(content.getOgImageURL())
            //.resize(Config.IMAGE_WIDTH, Config.IMAGE_HEIGHT).into(content.imageView);
    }

    public void like() {
        String url = Config.SERVER_URL + "/like";
        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.like(url, createParams(), new HttpResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    public void bookmark() {
        String url = Config.SERVER_URL + "/bookmark";
        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.like(url, createParams(), new HttpResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    public void incrementViewCount() {
        String url = Config.SERVER_URL + "/like";
        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.like(url, createParams(), new HttpResponseCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private Map<String, String> createParams() {
        Map<String, String> params = new HashMap<String, String> ();
        params.put(Config.USER_ID, User.getInstance().getUserId());
        params.put(Config.CONTENT_ID, this.conetntId);
        return params;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getOgImageURL() {
        return ogImageURL;
    }

    public void setOgImageURL(String ogImageURL) {
        this.ogImageURL = ogImageURL;
    }

    public String getOgTitle() {
        return ogTitle;
    }

    public String getOgDescription() {
        return ogDescription;
    }

    public void setOgDescription(String ogDescription) {
        this.ogDescription = ogDescription;
    }

    public void setOgTitle(String ogTitle) {
        this.ogTitle = ogTitle;
    }

    public Date getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(Date originalDate) {
        this.originalDate = originalDate;
    }

    public String getConetntId() {
        return conetntId;
    }

    public void setConetntId(String conetntId) {
        this.conetntId = conetntId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ImageView getImage() {
        return imageView;
    }

    public void setImage(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getOgSiteName() {
        return ogSiteName;
    }

    public void setOgSiteName(String ogSiteName) {
        this.ogSiteName = ogSiteName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getOgContentURL() {
        return ogContentURL;
    }

    public void setOgContentURL(String ogContentURL) {
        this.ogContentURL = ogContentURL;
    }

    public ArrayList<String> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<String> subCategory) {
        this.subCategory = subCategory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
