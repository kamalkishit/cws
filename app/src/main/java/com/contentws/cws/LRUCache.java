package com.contentws.cws;

import android.util.LruCache;
import android.graphics.Bitmap;

/**
 * Created by Kamal on 7/14/15.
 */
public class LRUCache {
    private LruCache<String, Bitmap> lruImageCache;
    private static LRUCache lruCache = null;

    public static LRUCache getInstance() {
        if (lruCache == null) {
            lruCache = new LRUCache();
        }

        return lruCache;
    }
    private LRUCache() {
        lruImageCache = new LruCache<String, Bitmap>(30);
    }

    public Bitmap get(String key) {
        return lruImageCache.get(key);
    }

    public void put(String key, Bitmap value) {
        lruImageCache.put(key, value);
    }
}
