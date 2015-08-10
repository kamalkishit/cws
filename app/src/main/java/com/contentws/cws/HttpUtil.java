package com.contentws.cws;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Kamal on 7/1/15.
 */
public class HttpUtil {
    private static HttpUtil httpUtil = null;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil();
        }

        return httpUtil;
    }

    public void getPaper() {
        String url = Config.SERVER_URL + "/paper";
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5000, TimeUnit.SECONDS.MILLISECONDS);
        Request request = new Request.Builder().url(url).build();
        //final JSONParser jsonParser = new ContentsJSONParser();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException ioException) {
                ioException.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                try {
                    JSONObject jsonObject = new JSONObject(new String(response.body().string()));
                    //jsonParser.parseJSON(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void get(String url, final HttpResponseCallback httpResponseCallback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(5000, TimeUnit.SECONDS.MILLISECONDS);
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException ioException) {
                ioException.printStackTrace();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("1");
                        httpResponseCallback.onFailure("");
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("2");
                            httpResponseCallback.onFailure("");
                        }
                    });
                }

                final String responseStr = response.body().string().toString();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            httpResponseCallback.onSuccess(responseStr);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("4");
                        }
                    }
                });
            }
        });
    }

    public void like(String url, Map<String, String> params, final HttpResponseCallback httpResponseCallback) {

    }

    public void bookmark(String url, Map<String, String> params, final HttpResponseCallback httpResponseCallback) {

    }

    public void incrementViewCount(String url, Map<String, String> params, final HttpResponseCallback httpResponseCallback) {

    }

    public void getContents(String startDate, final HttpResponseCallback httpResponseCallback) {
        String url = Config.SERVER_URL + "/contents";
        if (startDate != null) {
            url += "?startDate=" + startDate;
        }

        get(url, httpResponseCallback);
    }

    public void login(String url, Map<String, String> params, final HttpResponseCallback httpResponseCallback) {
        post(url, params, httpResponseCallback);
    }

    public void signup(String url, Map<String, String> params, final HttpResponseCallback httpResponseCallback) {
        post(url, params, httpResponseCallback);
    }

    private void post(String url, Map<String, String> params, final HttpResponseCallback httpResponseCallback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = getParams(params);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException ioException) {
                ioException.printStackTrace();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        httpResponseCallback.onFailure("");
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            httpResponseCallback.onSuccess(new String(response.body().string().toString()));
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });
    }

    private RequestBody getParams(Map<String, String> params) {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            formEncodingBuilder = formEncodingBuilder.add(key, value);
        }

        return formEncodingBuilder.build();
    }
}
