package com.wangyz.wanandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.adapter.OptionAdapter;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.model.Option;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.custom.CustomGridView;
import com.wangyz.wanandroid.presenter.ArticleActivityPresenter;
import com.wangyz.wanandroid.strategy.OptionStrategy;
import com.wangyz.wanandroid.strategy.OptionStrategyFactory;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyz
 * @time 2019/1/18 17:34
 * @description ArticleActivity
 */
public class ArticleActivity extends BaseActivity<Contract.ArticleActivityView, ArticleActivityPresenter> implements Contract.ArticleActivityView {

    @BindView(R.id.back)
    ImageView mBack;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.more)
    ImageView mMore;

    private String mLink;

    @BindView(R.id.activity_article_container)
    LinearLayout mLinearLayout;

    private AgentWeb mAgentWeb;

    private Context mContext;

    private OptionAdapter mAdapter;

    private List<String> mTitles;

    private List<Integer> mDrawableIds;

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mLink = getIntent().getStringExtra(ConstantValue.KEY_LINK);
        if (TextUtils.isEmpty(mLink)) {
            return;
        }
        String title = getIntent().getStringExtra(ConstantValue.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(StringEscapeUtils.unescapeHtml4(title));
        }

        mAgentWeb = AgentWeb.with(this).setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1)).useDefaultIndicator().createAgentWeb().ready().go(mLink);
        mAgentWeb.getAgentWebSettings().getWebSettings().setUseWideViewPort(true);
        mAgentWeb.getAgentWebSettings().getWebSettings().setLoadWithOverviewMode(true);
    }

    @Override
    protected ArticleActivityPresenter createPresenter() {
        return new ArticleActivityPresenter();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.more)
    public void more() {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_more, null, false);
        window.setContentView(view);

        CustomGridView gridView = view.findViewById(R.id.dialog_gv_options);

        if (mAdapter == null) {
            mTitles = new ArrayList<>();
            mTitles.add(mContext.getString(R.string.share));
            mTitles.add(mContext.getString(R.string.copy_link));
            mTitles.add(mContext.getString(R.string.browser));

            mDrawableIds = new ArrayList<>();
            mDrawableIds.add(R.drawable.share);
            mDrawableIds.add(R.drawable.link);
            mDrawableIds.add(R.drawable.browser);

        }

        mAdapter = new OptionAdapter(mContext, mTitles, mDrawableIds);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Option option = new Option();
            option.title = mTitle.getText().toString();
            option.link = mLink;
            OptionStrategy strategy = OptionStrategyFactory.getStrategy(position);
            if (strategy != null) {
                strategy.handleOption(option);
            } else {
                LogUtils.e("strategy=null");
            }
            dialog.dismiss();
        });

    }
}
