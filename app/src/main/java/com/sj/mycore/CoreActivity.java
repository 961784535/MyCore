package com.sj.mycore;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sj.mycore.app.ConfigKeys;
import com.sj.mycore.app.ProjectInit;
import com.sj.mycore.net.RxPrinciple.RestClient;
import com.sj.mycore.net.callback.IError;
import com.sj.mycore.net.callback.IFailure;
import com.sj.mycore.net.callback.ISuccess;
import com.sj.mycore.net.rx.RxRestClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoreActivity extends AppCompatActivity {

    private HashMap<String, Object> params;
    private String TAG = "CoreActivity";
    private android.widget.Button btnGet;
    private android.widget.Button btnPost;
    private android.widget.Button btnDownload;
    private android.widget.Button btnUpLoad;
    private TextView tvUrl;
    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        findView();
    }

    private void findView() {
        this.tvResponse = (TextView) findViewById(R.id.tvResponse);
        this.tvUrl = (TextView) findViewById(R.id.tvUrl);
        this.btnUpLoad = (Button) findViewById(R.id.btnUpLoad);
        this.btnDownload = (Button) findViewById(R.id.btnDownload);
        this.btnPost = (Button) findViewById(R.id.btnPost);
        this.btnGet = (Button) findViewById(R.id.btnGet);
    }

    public void click(View v){
        switch (v.getId()) {
            case R.id.btnGet : //
//                testGet();
                testRxGet();
                break;
            case R.id.btnPost : //

                break;
            case R.id.btnDownload : //
                testDownload();
//                testRxDownload();
                break;
            case R.id.btnUpLoad : //
                testUpload();
                break;
            default:
                break;
        }
    }

    //get请求 (已测试OK)
    private void testGet() {
        params = new HashMap();
        params.put("kw","%E7%99%BD");
        params.put("site","qq.com");
        params.put("apikey","IhNUGOTbEhh9qn41mEbwNxF5vUBpHg9a6XPuJeOGPn7Uqy9o8ecEGgezo4g5BSLi");
        tvUrl.setText(""+ ProjectInit.getConfiguration(ConfigKeys.API_HOST).toString());
        RestClient.create() //创建了 RestClientBuilder对象
                .params(params)
                .url("news/qihoo")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responce) {
                        Log.i(TAG, "onSuccess: ");
                        Toast.makeText(CoreActivity.this, "Get成功", Toast.LENGTH_SHORT).show();
                        tvResponse.setText("" + responce.toString());
                    }
                })
                .build()  //创建了 RestClient对象
                .get();  //get请求
    }

    /**
     * 封装 rx  (已测试OK)
     */
    private void testRxGet() {
        params = new HashMap();
        params.put("kw","%E7%99%BD");
        params.put("site","qq.com");
        params.put("apikey","IhNUGOTbEhh9qn41mEbwNxF5vUBpHg9a6XPuJeOGPn7Uqy9o8ecEGgezo4g5BSLi");
        tvUrl.setText(""+ ProjectInit.getConfiguration(ConfigKeys.API_HOST).toString());
        RxRestClient.create()
                .url("news/qihoo")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        //响应结果
                        Toast.makeText(CoreActivity.this, "RxGet成功", Toast.LENGTH_SHORT).show();
                        tvResponse.setText("" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tvResponse.setText("" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 文件下载 （下载地址有问题）
     */
    private void testDownload() {
        //  测试下载  http://dengpaoedu.com:8080/examples/test.zip
        params=new HashMap<>();
        params.put("file","abcd.txt");
        RestClient.create()
                .params(params)
                .url("/examples/test.zip")
                .dir("/sdcard")
                .extension("zip")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responce) {
                        Toast.makeText(CoreActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .download();
    }

    /**
     * 文件下载 （下载地址有问题）
     */
    private void testRxDownload() {
        //  测试下载  http://dengpaoedu.com:8080/examples/test.zip
        params=new HashMap<>();
        params.put("file","abcd.txt");
        RxRestClient.create().params(params)
                .url("/examples/test.zip")
                .build()
                .download()
                .subscribeOn(Schedulers.io())//io线程处理
                .observeOn(AndroidSchedulers.mainThread())//主线程
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(CoreActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //上传
    private void testUpload() {
        RestClient.create()
                .params(params)
                .url("/fileuploadanddownload/uploadServlet")
                .file(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.txt")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String responce) {
                        Toast.makeText(CoreActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(CoreActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(CoreActivity.this, code + "", Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .upload();
    }


}
