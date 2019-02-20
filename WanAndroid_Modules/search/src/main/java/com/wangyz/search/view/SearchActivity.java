package com.wangyz.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangyz.common.ConstantValue;
import com.wangyz.common.base.BaseActivity;
import com.wangyz.common.bean.model.KeyWord;
import com.wangyz.search.R;
import com.wangyz.search.contract.Contract;
import com.wangyz.search.presenter.SearchActivityPresenter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 16:49
 * @description SearchActivity
 */
@Route(path = ConstantValue.ROUTE_SEARCH)
public class SearchActivity extends BaseActivity<Contract.SearchActivityView, SearchActivityPresenter> implements Contract.SearchActivityView, View.OnClickListener {

    ImageView mBack;

    TagFlowLayout mTag;

    EditText mKeyWord;

    ImageView mSearch;

    WaveLoadingView mWaveLoadingView;

    private Context mContext;

    private List<String> mHotKeyList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.search_activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getApplicationContext();

        mBack = findViewById(R.id.back);
        mTag = findViewById(R.id.tag_hot_key);
        mKeyWord = findViewById(R.id.key_word);
        mSearch = findViewById(R.id.search);
        mWaveLoadingView = findViewById(R.id.loading);

        mBack.setOnClickListener(this);
        mSearch.setOnClickListener(this);

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
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoadKeyWord(KeyWord result) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (result != null && result.getErrorCode() == 0) {
            mHotKeyList = result.getData().stream().map(k -> k.getName()).collect(Collectors.toList());
            mTag.setAdapter(new TagAdapter<String>(mHotKeyList) {
                @Override
                public View getView(FlowLayout flowLayout, int i, String s) {
                    TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_item_tree_tag, mTag, false);
                    tv.setText(s);
                    return tv;
                }
            });
            mTag.setOnTagClickListener((view, i, flowLayout) -> {
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                intent.putExtra(ConstantValue.KEY_KEYOWRD, mHotKeyList.get(i));
                mContext.startActivity(intent);
                return true;
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            finish();
        } else if (id == R.id.search) {
            if (TextUtils.isEmpty(mKeyWord.getText())) {
                ToastUtils.showShort(mContext.getString(R.string.common_input_key));
                return;
            }
            Intent intent = new Intent(mContext, SearchResultActivity.class);
            intent.putExtra(ConstantValue.KEY_KEYOWRD, mKeyWord.getText().toString());
            mContext.startActivity(intent);
        }
    }
}
