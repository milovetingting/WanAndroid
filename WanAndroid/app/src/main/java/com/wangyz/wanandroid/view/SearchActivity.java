package com.wangyz.wanandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.model.KeyWord;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.presenter.SearchActivityPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 16:49
 * @description SearchActivity
 */
public class SearchActivity extends BaseActivity<Contract.SearchActivityView,
        SearchActivityPresenter> implements Contract.SearchActivityView {

    @BindView(R.id.tag_hot_key)
    TagFlowLayout mTag;

    @BindView(R.id.key_word)
    EditText mKeyWord;

    @BindView(R.id.loading)
    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private List<String> mHotKeyList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getApplicationContext();

        mPresenter.loadKeyWord();

    }

    @Override
    protected SearchActivityPresenter createPresenter() {
        return new SearchActivityPresenter();
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
        mWaveLoadingView.setVisibility(View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoadKeyWord(KeyWord result) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (result != null && result.getErrorCode() == 0) {
            mHotKeyList = result.getData().stream().map(k -> k.getName()).collect(Collectors
                    .toList());
            mTag.setAdapter(new TagAdapter<String>(mHotKeyList) {
                @Override
                public View getView(FlowLayout flowLayout, int i, String s) {
                    TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R
                            .layout.item_tree_tag, mTag, false);
                    tv.setText(s);
                    return tv;
                }
            });
            mTag.setOnTagClickListener((view, i, flowLayout) -> {
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                intent.putExtra(ConstantValue.KEY_KEYOWRD, mHotKeyList.get(i));
                startActivity(intent);
                return true;
            });
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.search)
    public void search() {
        if (TextUtils.isEmpty(mKeyWord.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.input_key));
            return;
        }
        Intent intent = new Intent(mContext, SearchResultActivity.class);
        intent.putExtra(ConstantValue.KEY_KEYOWRD, mKeyWord.getText().toString());
        startActivity(intent);
    }
}
