package com.bwei.MVPandXrecyclerview.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;


    //构造方法私有的？因为不能被调用者new的对象，只能给自己new
    private OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
    }
    /**
     * public 暴露给调用者，双重检验锁
     *
     * @return
     */
    public static OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }


    /**
     * get封装
     *
     * @param params
     * @param url
     * @param requestCallback
     */
    public void getData(String url, HashMap<String, String> params, final RequestCallback requestCallback) {

        StringBuilder urlsb = new StringBuilder();
        String allUrl = "";
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            urlsb.append("?").append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");
        }

        allUrl = url + urlsb.toString().substring(0, urlsb.length() - 1);
        System.out.println("url:" + allUrl);

        final Request request = new Request.Builder()
                .url(allUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback != null) {
                    requestCallback.failure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (requestCallback != null) {
                    requestCallback.onResponse(call, response);
                }
            }
        });
    }

    /**
     * post请求方式
     *
     * @param url
     * @param prams
     */
    public void postData(String url, HashMap<String, String> prams, final RequestCallback requestCallback) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        if (prams != null && prams.size() > 0) {
            for (Map.Entry<String, String> stringStringEntry : prams.entrySet()) {
                formBodyBuilder.add(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(url).post(formBodyBuilder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback != null) {
                    requestCallback.failure(call, e);
                }

            }

            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (requestCallback != null) {
                    requestCallback.onResponse(call, response);
                }
            }
        });
    }

}
