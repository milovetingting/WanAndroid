package com.wangyz.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.bean.db.Tree;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.bean.model.TreeInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyz
 * @time 2019/1/23 10:16
 * @description TreeAdapter
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {

    private Context mContext;

    private List<TreeInfo> mList = new ArrayList<>();

    private boolean mNightMode;

    public void setList(List<TreeInfo> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public TreeAdapter(Context context, List<TreeInfo> list) {
        mContext = context;
        mList.addAll(list);
        mNightMode = SPUtils.getInstance(ConstantValue.CONFIG_SETTINGS).getBoolean(ConstantValue.KEY_NIGHT_MODE, false);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tree_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TreeInfo model = mList.get(i);
        viewHolder.title.setText(model.name);
        viewHolder.tags.setAdapter(new TagAdapter<Tree>(model.child) {
            @Override
            public View getView(FlowLayout flowLayout, int i, Tree tree) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tree_tag, viewHolder.tags, false);
                tv.setText(tree.name);
                return tv;
            }
        });
        viewHolder.tags.setOnTagClickListener((view, i1, flowLayout) -> {
            Event event = new Event();
            event.target = Event.TARGET_MAIN;
            event.type = Event.TYPE_TREE_ARTICLE_FRAGMENT;
            event.data = model.child.get(i1).treeId + "";
            EventBus.getDefault().post(event);
            return true;
        });
        if (mNightMode) {
            viewHolder.cardView.setBackgroundColor(mContext.getResources().getColor(mNightMode ? R.color.card_night_bg : R.color.card_bg));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.item_tree_title)
        TextView title;
        @BindView(R.id.item_tree_tags)
        TagFlowLayout tags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
