package com.bwei.MVPandXrecyclerview.presenter;

import com.bwei.MVPandXrecyclerview.bean.NewsBean;
import com.bwei.MVPandXrecyclerview.model.NewsModel;
import com.bwei.MVPandXrecyclerview.view.NewView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class NewsPresent {

    private NewsModel newsModel;
    private NewView newView;

    public NewsPresent(NewView newView) {
        this.newsModel = new NewsModel();
        this.newView = newView;
    }

    public void initNews(HashMap<String,String> prams){
        newsModel.initNewa(prams, new NewsModel.NewsCallBack() {
            //请求失败
            @Override
            public void erray(String erray) {
                newView.erray(erray);
            }
            //请求成功
            @Override
            public void success(List<NewsBean.DataBean> data) {
                newView.success(data);
            }
        });
    }
}
