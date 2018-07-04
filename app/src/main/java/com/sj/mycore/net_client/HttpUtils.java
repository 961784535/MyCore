package com.sj.mycore.net_client;

import android.util.Log;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by Administrator on 2016/6/20 0020.
 * 网络请求 工具类
 */
public class HttpUtils {

    String TAG = "HttpUtils";
    OkHttpClient client;

    public HttpUtils() {
        client = new OkHttpClient();
    }

    /**
     * get请求
     *
     * @param url      完整的借口
     * @param map      键值对
     * @param callback 回调
     */
    public void doGet(String url, Map<Object, Object> map, Callback callback) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        if (map != null && map.size() != 0) {
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if (isFirst) {
                    sb.append("?");
                    isFirst = false;
                } else {
                    sb.append("&");
                }
                Log.i(TAG, "@doGet: " + entry.getKey().toString() + ":" + entry.getValue().toString());
                sb.append(entry.getKey().toString()).append("=").append(entry.getValue().toString());
            }
        }
        url = url + sb.toString();
        Log.i(TAG, "拼接url为: " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * post请求
     *
     * @param url      完整的借口
     * @param map      键值对
     * @param callback 回调
     */
    public void doPost(String url, Map<Object, Object> map, Callback callback) {
        Log.i(TAG, "doPost " + url);
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null && map.size() != 0) {
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                builder.add(entry.getKey().toString(), entry.getValue().toString());
                Log.i(TAG, "doPost: " + entry.getKey().toString() + "=" + entry.getValue().toString());
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
