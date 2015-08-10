package com.contentws.cws;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Kamal on 7/20/15.
 */
public class SerializedJsonObject implements Serializable {
    private JSONObject jsonObject;

    public SerializedJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
