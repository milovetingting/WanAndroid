package com.wangyz.wanandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.bean.db.Article;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.util.LoginUtil;
import com.wangyz.wanandroid.view.ArticleActivity;
import com.wangyz.wanandroid.view.LoginActivity;

import org.apache.commons.lang3.StringEscapeUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyz
 * @time 2019/1/18 9:22
 * @description MainArticleAdapter
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private Context mContext;

    private List<Article> mList = new ArrayList<>();

    private boolean mNightMode;

    private static Pattern mPattern = Pattern.compile(ConstantValue.REGEX);

    public void setList(List<Article> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public SearchResultAdapter(Context context, List<Article> list) {
        mContext = context;
        mList.addAll(list);
        mNightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Article model = mList.get(i);
        String title = model.title;
        title = StringEscapeUtils.unescapeHtml4(model.title);
        viewHolder.title.setText(title);
        Matcher matcher = mPattern.matcher(title);
        if (matcher.find()) {
            title = title.replace("<em class='highlight'>", "").replace("</em>", "");
            setText(viewHolder.title, title, matcher.group(1), mContext.getColor(R.color.colorPrimary));
        }
        viewHolder.author.setText(mContext.getResources().getString(R.string.author) + StringEscapeUtils.unescapeHtml4(model.author));
        viewHolder.category.setText(mContext.getResources().getString(R.string.category) + StringEscapeUtils.unescapeHtml4(model.category));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(model.time);
        viewHolder.time.setText(sdf.format(date));
        if (!LoginUtil.isLogin()) {
            viewHolder.collect.setSelected(false);
        } else {
            viewHolder.collect.setSelected(model.collect);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ArticleActivity.class);
            intent.putExtra(ConstantValue.KEY_LINK, model.link);
            intent.putExtra(ConstantValue.KEY_TITLE, model.title.replace("<em class='highlight'>", "").replace("</em>", ""));
            mContext.startActivity(intent);
        });

        viewHolder.collect.setOnClickListener(v -> {
            if (!LoginUtil.isLogin()) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            } else {
                Event event = new Event();
                event.target = Event.TARGET_SEARCH_RESULT;
                event.type = model.collect ? Event.TYPE_UNCOLLECT : Event.TYPE_COLLECT;
                event.data = model.articleId + "";
                EventBus.getDefault().post(event);
            }

        });
        if (mNightMode) {
            viewHolder.cardView.setBackgroundColor(mContext.getResources().getColor(mNightMode ? R.color.card_night_bg : R.color.card_bg));
        }
    }

    private void setText(TextView tv, String text, String key, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        // 第一个出现的索引位置
        int index = text.indexOf(key);
        while (index != -1) {
            builder.setSpan(new ForegroundColorSpan(color), index, index + key.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 从这个索引往后开始第一个出现的位置
            index = text.indexOf(key, index + 1);
        }
        tv.setText(builder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.item_main_list_title)
        TextView title;
        @BindView(R.id.item_main_list_author)
        TextView author;
        @BindView(R.id.item_main_list_category)
        TextView category;
        @BindView(R.id.item_main_list_time)
        TextView time;
        @BindView(R.id.item_main_list_collect)
        ImageView collect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
