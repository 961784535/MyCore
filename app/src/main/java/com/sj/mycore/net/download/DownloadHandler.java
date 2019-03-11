package com.sj.mycore.net.download;

import android.os.AsyncTask;

import com.sj.mycore.net.RxPrinciple.RestCreator;
import com.sj.mycore.net.callback.IError;
import com.sj.mycore.net.callback.IFailure;
import com.sj.mycore.net.callback.IRequest;
import com.sj.mycore.net.callback.ISuccess;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description: 文件下载
 */
public class DownloadHandler {

    private final HashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FILENAME;

    /**
     * 文件下载
     * @param params  参数map
     * @param url     url
     * @param request   请求状态
     * @param success  请求成功监听
     * @param failure  请求失败监听
     * @param error   请求错误监听
     * @param downloadDir  文件夹
     * @param extension   扩展名
     * @param filename  文件名
     */
    public DownloadHandler(HashMap<String, Object> params,
                           String url,
                           IRequest request,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           String downloadDir,
                           String extension,
                           String filename) {
        this.PARAMS = params;
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.FILENAME = filename;
    }

    public final void handleDownload() {
        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            //开始保存文件,开一个异步任务来做
                            SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR, EXTENSION, response.body(), FILENAME);
                            //如果下载完成
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }


}
