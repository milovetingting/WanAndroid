package com.wangyz.user.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.wangyz.common.ConstantValue;
import com.wangyz.common.bean.db.Collect;
import com.wangyz.common.bean.event.Event;
import com.wangyz.common.util.LoginUtil;
import com.wangyz.user.R;
import com.wangyz.user.view.LoginActivity;

import org.apache.commons.lang3.StringEscapeUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author wangyz
 * @time 2019/1/18 9:22
 * @description MainArticleAdapter
 */
public class CollectArticleAdapter extends RecyclerView.Adapter<CollectArticleAdapter.ViewHolder> {

    private Context mContext;

    private List<Collect> mList = new ArrayList<>();

    private boolean mNightMode;

    public void setList(List<Collect> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public CollectArticleAdapter(Context context, List<Collect> list) {
        mContext = context;
        mList.addAll(list);
        mNightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item_collect_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Collect model = mList.get(i);
        viewHolder.title.setText(StringEscapeUtils.unescapeHtml4(model.title));
        viewHolder.author.setText(mContext.getResources().getString(R.string.common_author) + model.author);
        viewHolder.category.setText(mContext.getResources().getString(R.string.common_category) + model.category);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(model.time);
        viewHolder.time.setText(sdf.format(date));
        viewHolder.collect.setSelected(true);

        viewHolder.itemView.setOnClickListener(v -> {
            ARouter.getInstance().build(ConstantValue.ROUTE_ARTICLE).withString(ConstantValue.KEY_LINK, model.link).withString(ConstantValue.KEY_TITLE, model.title).navigation();
        });

        viewHolder.collect.setOnClickListener(v -> {
            if (!LoginUtil.isLogin()) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            } else {
                Event event = new Event();
                event.target = Event.TARGET_COLLECT;
                event.type = Event.TYPE_UNCOLLECT;
                event.data = model.articleId + "";
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
        TextView title;
        TextView author;
        TextView category;
        TextView time;
        ImageView collect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            title = itemView.findViewById(R.id.item_main_list_title);
            author = itemView.findViewById(R.id.item_main_list_author);
            category = itemView.findViewById(R.id.item_main_list_category);
            time = itemView.findViewById(R.id.item_main_list_time);
            collect = itemView.findViewById(R.id.item_main_list_collect);
        }
    }

}
