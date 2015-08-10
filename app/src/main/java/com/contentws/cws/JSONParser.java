package com.contentws.cws;

import org.json.JSONObject;

/**
 * Created by Kamal on 7/29/15.
 */
public interface JSONParser<T> {
    public void parseJSON(JSONObject jsonObject);
}
