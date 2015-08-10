package com.contentws.cws;

import com.contentws.cws.Data.Content;

import java.util.ArrayList;

/**
 * Created by Kamal on 7/6/15.
 */
public class PaperNavigator {
    private ArrayList<Content> contents;
    private int currentIndex;
    private static PaperNavigator paperNavigator = null;

    private PaperNavigator() {
        contents = new ArrayList<>();
        currentIndex = 0;
        getPaper();
    }

    public static PaperNavigator getInstance() {
        if (paperNavigator == null) {
            paperNavigator = new PaperNavigator();
        }

        return paperNavigator;
    }

    private void getPaper() {
        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.getPaper();
    }

    public Content getFirstContent() {
        if (currentIndex < contents.size()) {
            return contents.get(currentIndex);
        } else {
            return null;
        }
    }

    public Content getForwardContent() {
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
}
