package com.sj.mycore.net.rx;


import com.sj.mycore.net.RxPrinciple.HttpMethod;
import com.sj.mycore.net.RxPrinciple.RestCreator;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by arJun on 2018/6/6.
 *  调用初始
 */
public class RxRestClient {
    private final HashMap<String, Object> PARAMS;
    private final String URL;
    private final RequestBody BODY;
    //上传下载
    private final File FILE;


    public RxRestClient(HashMap<String, Object> params,
                        String url,
                        RequestBody body,
                        File file) {
        this.PARAMS = params;
        this.URL = url;
        this.BODY = body;
        this.FILE = file;

    }

    public static RxRestClientBuilder create() {
        return new RxRestClientBuilder();
    }


    /**
     * 开始实现真实的网络操作
     *
     * @param method 请求类型
     * @return
     */
    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MultipartBody.FORM, FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData(
                        "file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }
        return observable;
    }


    //各种请求
    /**
     * get 请求
     *
     * @return
     */
    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    /**
     * get 请求
     *
     * @return
     */
    public final Observable<String> post() {
        return request(HttpMethod.POST);
    }

    /**
     * put 请求
     *
     * @return
     */
    public final Observable<String> put() {
        return request(HttpMethod.PUT);
    }

    /**
     * delete 请求
     *
     * @return
     */
    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    /**
     * upload 请求
     *
     * @return
     */
    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    /**
     * download 文件下载
     *
     * @return
     */
    public final Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }

}







