package com.contentws.cws;

import com.contentws.cws.Data.Content;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kamal on 7/6/15.
 */
public class ContentNavigator {
    private ArrayList<Content> contents;
    private int currentIndex;
    private String lastCreatedTime;
    private static ContentNavigator contentNavigator = null;

    private ContentNavigator() {
        contents = new ArrayList<>();
        lastCreatedTime = null;
        currentIndex = 0;
        loadForward();
    }

    public static ContentNavigator getInstance() {
        if (contentNavigator == null) {
            contentNavigator = new ContentNavigator();
        }

        return contentNavigator;
    }

    private void loadForward() {
        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.getContents(lastCreatedTime, new HttpResponseCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("success");
                success(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                System.out.println("failure");
                failure();
            }
        });
    }

    private void success(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            ArrayList<Content> contents = parseContents(
                    JSONObjectParser.getJSONArray(jsonObject, Config.CONTENTS));
            contentNavigator.addForwardContent(contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Content> parseContents(JSONArray jsonArray) {
        ArrayList<Content> contents = new ArrayList<Content>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Content content = new Content();
            content.updateContent(JSONObjectParser.getJSONObject(jsonArray, i));
            contents.add(content);
            Picasso.with(ApplicationState.getAppContext()).load(Config.SERVER_URL + "/" + content.getImageURL())
                    .resize(Config.IMAGE_WIDTH, Config.IMAGE_HEIGHT).fetch();
        }

        return contents;
    }

    private void failure() {

    }

    public Content getFirstContent() {
        if (currentIndex < contents.size()) {
            return contents.get(currentIndex);
        } else {
            return null;
        }
    }

    public Content getForwardContent() {
        if (currentIndex + Config.LIMIT == contents.size()) {
            loadForward();
        }
        if (currentIndex + 1< contents.size()) {
            currentIndex++;
            return contents.get(currentIndex);
        } else {
            return null;
        }
    }

    public boolean canMoveForward() {
        if (currentIndex + 1< contents.size()) {
            return true;
        }

        return false;
    }

    public boolean canMoveBackward() {
        if (currentIndex == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Content getBackwardContent() {
        if (currentIndex == 0) {
            return null;
        } else {
            currentIndex--;
            return contents.get(currentIndex);
        }
    }

    public void addForwardContent(ArrayList<Content> contents) {
        for (int i = 0; i < contents.size(); i++) {
            this.contents.add(contents.get(i));
        }

        if (this.contents.size() > 0) {
            lastCreatedTime = this.contents.get(this.contents.size() -1).getCreatedAt();
        }
    }
}
