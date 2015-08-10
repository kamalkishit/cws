package com.contentws.cws.Data;

import com.contentws.cws.Config;
import com.contentws.cws.JSONObjectParser;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kamal on 6/30/15.
 */
public class User {
    private static User user = null;
    private String userId;
    private String username;
    private String emailId;
    private ArrayList<String> likes;
    private ArrayList<String> bookmarks;
    private ArrayList<String> contentsCreated;

    private User() {
        this.username = null;
        this.userId = null;
        this.emailId = null;
        this.likes = null;
        this.bookmarks = null;
        this.contentsCreated = null;
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    public boolean isLiked(String contentId) {
        for (String like: likes) {
            if (like.equals(contentId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBookmarked(String contentId) {
        for (String bookmark: bookmarks) {
            if (bookmark.equals(contentId)) {
                return true;
            }
        }
        return false;
    }

    public void updateUser(JSONObject jsonObject) {
        this.setUserId(JSONObjectParser.getString(jsonObject, Config.USER_ID));
        this.setLikes(JSONObjectParser.getStringArray(jsonObject, Config.LIKES));
        this.setBookmarks(JSONObjectParser.getStringArray(jsonObject, Config.BOOKMARKS));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(ArrayList<String> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public ArrayList<String> getContentsCreated() {
        return contentsCreated;
    }

    public void setContentsCreated(ArrayList<String> contentsCreated) {
        this.contentsCreated = contentsCreated;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }
}
