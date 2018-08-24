package com.bwei.MVPandXrecyclerview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.MVPandXrecyclerview.R;
import com.bwei.MVPandXrecyclerview.bean.NewsBean;

import java.util.List;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class XrecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NewsBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public XrecyclerAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_layout, parent, false);
        return new OneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OneViewHolder){
            ((OneViewHolder) holder).one_tv.setText(list.get(position).getTitle());
            Glide.with(context).load(list.get(position).getImages().split("\\|")[0]).into(((OneViewHolder) holder).on_iv);

            //点击子条目开始动画
            ((OneViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,position);
                }
            });
            //长按子条目删除
            ((OneViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.OnItemLongClick(v,position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OneViewHolder extends RecyclerView.ViewHolder{

        private TextView one_tv;
        private ImageView on_iv;

        public OneViewHolder(View itemView) {
            super(itemView);
            on_iv = itemView.findViewById(R.id.one_iv);
            one_tv = itemView.findViewById(R.id.one_tv);
        }
    }

    //点击事件
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //长按事件
    public interface OnItemLongClickListener{
        void OnItemLongClick(View view,int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
}


