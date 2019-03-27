package com.wangyz.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyz.wanandroid.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wangyz
 * @time 2019/3/27 13:35
 * @description OptionAdapter
 */
public class OptionAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> mTitles;

    private List<Integer> mDrawableIds;

    public OptionAdapter(Context context, List<String> titles, List<Integer> drawableIds) {
        this.mContext = context;
        this.mTitles = titles;
        this.mDrawableIds = drawableIds;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return mTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_option, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.icon.setImageDrawable(mContext.getDrawable(mDrawableIds.get(position)));
        viewHolder.title.setText(mTitles.get(position));
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.item_option_iv_icon)
        ImageView icon;

        @BindView(R.id.item_option_tv_title)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
