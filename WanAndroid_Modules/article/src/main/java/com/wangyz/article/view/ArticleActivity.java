package com.wangyz.article.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;
import com.wangyz.article.R;
import com.wangyz.article.contract.Contract;
import com.wangyz.article.presenter.ArticleActivityPresenter;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseActivity;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author wangyz
 * @time 2019/1/18 17:34
 * @description ArticleActivity
 */
@Route(path = ConstantValue.ROUTE_ARTICLE)
public class ArticleActivity extends BaseActivity<Contract.ArticleActivityView, ArticleActivityPresenter> implements Contract.ArticleActivityView, View.OnClickListener {

    ImageView mBack;

    TextView mTitle;

    ImageView mShare;

    private String mLink;

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
        return R.layout.article_activity_article;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mBack = findViewById(R.id.back);
        mTitle = findViewById(R.id.title);
        mShare = findViewById(R.id.share);
        mLinearLayout = findViewById(R.id.activity_article_container);

        mBack.setOnClickListener(this);
        mShare.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mTitle.getText() + "\n" + mLink);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "Share"));
        }
    }
}
