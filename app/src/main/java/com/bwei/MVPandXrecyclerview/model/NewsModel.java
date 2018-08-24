package com.bwei.MVPandXrecyclerview.model;

import android.os.Handler;

import com.bwei.MVPandXrecyclerview.bean.NewsBean;
import com.bwei.MVPandXrecyclerview.common.Api;
import com.bwei.MVPandXrecyclerview.utils.OkHttpUtils;
import com.bwei.MVPandXrecyclerview.utils.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class NewsModel {
    Handler handler=new Handler();

    public void initNewa(HashMap<String,String> prams,final NewsCallBack newsCallBack){
        //传入接口
        OkHttpUtils.getInstance().postData(Api.PRODUCT_URL, prams, new RequestCallback() {

            private String string;

            @Override
            public void failure(Call call, IOException e) {
                if (newsCallBack!=null){
                    newsCallBack.erray("请求失败");
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (200==response.code()){
                    try {
                        string = response.body().string();
                        preajson(string,newsCallBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void preajson(String string, final NewsCallBack newsCallBack) {
        Gson gson = new Gson();
        final NewsBean newsBean=gson.fromJson(string,NewsBean.class);
        final List<NewsBean.DataBean> data = newsBean.getData();
        //切换线程
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (newsCallBack!=null){
                    newsCallBack.success(data);
                }
            }
        });
    }

    public interface NewsCallBack{
        void erray(String erray);
        void success(List<NewsBean.DataBean> data);
    }
}
