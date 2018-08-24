package com.bwei.MVPandXrecyclerview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.MVPandXrecyclerview.bean.NewsBean;
import com.bwei.MVPandXrecyclerview.presenter.NewsPresent;
import com.bwei.MVPandXrecyclerview.view.NewView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

/**
 * 没有刷新加载
 */
public class MainActivity extends AppCompatActivity implements NewView {
    private ImageView ivIcon;
    private TextView tvNick;
    private Button btnLogin;
    private XRecyclerView xrecyclerView;
    private NewsPresent newsPresent;
    private int page =1;//默认显示第一页
    private XrecyclerAdapter xrecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //加载数据
        initLieBiao();
    }
    //点击头像开始缩放动画
    public void dianjitouxiang(View view){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivIcon, "scaleX", 1f, 5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivIcon, "scaleY", 1f, 5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX,scaleY);
        animatorSet.setDuration(3000);
        animatorSet.start();
    }

    private void initLieBiao() {
        newsPresent = new NewsPresent(MainActivity.this);
        HashMap<String, String> prams = new HashMap<>();
        prams.put("keywords","手机");
        prams.put("page",page+"");
        newsPresent.initNews(prams);//传到p层
    }

    private void initView() {
        ivIcon = findViewById(R.id.iv_icon);
        tvNick = findViewById(R.id.tv_nick);
        btnLogin = findViewById(R.id.btn_login);
        xrecyclerView = findViewById(R.id.recycler_view);
    }

    //請求失敗
    @Override
    public void erray(String erray) {
        Toast.makeText(MainActivity.this,erray,Toast.LENGTH_LONG).show();
    }

    //請求成功
    @Override
    public void success(final List<NewsBean.DataBean> data) {
        xrecyclerView.setLoadingMoreEnabled(true);
        xrecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        xrecyclerAdapter = new XrecyclerAdapter(MainActivity.this, data);
        xrecyclerView.setAdapter(xrecyclerAdapter);
        //点击子条目加载动画
        xrecyclerAdapter.setOnItemClickListener(new XrecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f,0.5f, 0f,0.5f, 1f);
                alpha.setDuration(4000);
                alpha.start();
            }
        });
        //长按子条目删除
        xrecyclerAdapter.setOnItemLongClickListener(new XrecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除");
                builder.setMessage("确定删除吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        xrecyclerAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
    }
}
