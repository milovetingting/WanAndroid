package com.wangyz.project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.db.Article;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.util.LoginUtil;
import com.wangyz.project.R;

import org.apache.commons.lang3.StringEscapeUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangyz
 * @time 2019/1/18 9:22
 * @description WxArticleAdapter
 */
public class ProjectArticleAdapter extends RecyclerView.Adapter<ProjectArticleAdapter.ViewHolder> {

    private Context mContext;

    private List<Article> mList = new ArrayList<>();

    private boolean mNightMode;

    public void setList(List<Article> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public ProjectArticleAdapter(Context context, List<Article> list) {
        mContext = context;
        mList.addAll(list);
        mNightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_item_project_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Article model = mList.get(i);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.load_failed);
        Glide.with(mContext).load(model.pic).apply(requestOptions).into(viewHolder.pic);
        viewHolder.title.setText(StringEscapeUtils.unescapeHtml4(model.title));
        viewHolder.des.setText(model.des);
        viewHolder.author.setText(model.author);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(model.time);
        viewHolder.time.setText(sdf.format(date));
        if (!LoginUtil.isLogin()) {
            viewHolder.collect.setSelected(false);
        } else {
            viewHolder.collect.setSelected(model.collect);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            ARouter.getInstance().build(ConstantValue.ROUTE_ARTICLE).withString(ConstantValue.KEY_LINK, model.link).withString(ConstantValue.KEY_TITLE, model.title).navigation();
        });

        viewHolder.collect.setOnClickListener(v -> {
            if (!LoginUtil.isLogin()) {
                ARouter.getInstance().build(ConstantValue.ROUTE_LOGIN).navigation();
            } else {
                Event event = new Event();
                event.target = Event.TARGET_PROJECT;
                event.type = model.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                event.data = model.articleId + ";" + model.projectType;
                EventBus.getDefault().post(event);
            }

        });

        if (mNightMode) {
            viewHolder.cardView.setBackgroundColor(mContext.getResources().getColor(mNightMode ? R.color.common_card_night_bg : R.color.common_card_bg));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView pic;
        TextView title;
        TextView des;
        TextView time;
        TextView author;
        ImageView collect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            pic = itemView.findViewById(R.id.item_project_list_pic);
            title = itemView.findViewById(R.id.item_project_list_title);
            des = itemView.findViewById(R.id.item_project_list_des);
            time = itemView.findViewById(R.id.item_project_list_time);
            author = itemView.findViewById(R.id.item_project_list_author);
            collect = itemView.findViewById(R.id.item_project_list_collect);
        }
    }

}
