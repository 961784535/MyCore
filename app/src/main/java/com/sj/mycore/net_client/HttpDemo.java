package com.sj.mycore.net_client;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/6/20 0020.
 * 网络请求操作类
 */
public class HttpDemo {

    String TAG = "HttpDemo";
    HttpUtils httpUtils;
    Handler handler;
    HttpCallBack callBack;

    private static HttpDemo inst;

    public static HttpDemo getInstance(HttpCallBack callBack) {
        if (inst == null) {
            inst = new HttpDemo(callBack);
        }
        return inst;
    }

    public HttpDemo() {
        httpUtils = new HttpUtils();
        handler = new Handler();
    }

    public HttpDemo(HttpCallBack callBack) {
        httpUtils = new HttpUtils();
        handler = new Handler();
        this.callBack = callBack;
    }


    /**
     * get请求
     *
     * @param url 完整接口
     * @param map 键值对集合
     */
    public void doHttpGet(String url, Map<Object, Object> map, final int requestCode) {
        Log.i(TAG, "进入doHttpGet: ");
        httpUtils.doGet(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "doHttpGet失败: requestCode=" + requestCode + " ,error " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String msg = response.body().string();
                Log.i(TAG, "doHttpGet成功: " + msg);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: " + requestCode + msg);
                        callBack.setMsg(msg, requestCode);
                    }
                });
            }
        });
    }


    /**
     * post请求
     *
     * @param url 完整接口
     * @param map 键值对集合
     */
    public void doHttpPost(String url, Map<Object, Object> map, final int requestCode) {
        Log.i(TAG, "doHttpPost ");
        httpUtils.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "doHttpPost失败: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String msg = response.body().string();
                Log.i("login", "doHttpPost" + msg);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.setMsg(msg, requestCode);
                    }
                });
            }
        });
    }

    public interface HttpCallBack {
        void setMsg(String msg, int requestCode);
    }

}
