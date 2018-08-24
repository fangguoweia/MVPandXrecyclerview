package com.bwei.MVPandXrecyclerview.view;

import com.bwei.MVPandXrecyclerview.bean.NewsBean;

import java.util.List;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public interface NewView {

    void erray(String erray);
    void success(List<NewsBean.DataBean> data);
}
