package com.wangyz.wanandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.ArticleActivityPresenter;

import org.apache.commons.lang3.StringEscapeUtils;

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

    @BindView(R.id.share)
    ImageView mShare;

    private String mLink;

    @BindView(R.id.activity_article_container)
    LinearLayout mLinearLayout;

    private AgentWeb mAgentWeb;

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
        mLink = getIntent().getStringExtra(ConstantValue.KEY_LINK);
        if (TextUtils.isEmpty(mLink)) {
            return;
        }
        String title = getIntent().getStringExtra(ConstantValue.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(StringEscapeUtils.unescapeHtml4(title));
        }

        mAgentWeb = AgentWeb.with(this).setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1)).useDefaultIndicator().createAgentWeb().ready().go(mLink);
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

    @OnClick(R.id.share)
    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mTitle.getText() + "\n" + mLink);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "Share"));
    }
}
