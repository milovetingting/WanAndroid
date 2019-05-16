package com.wangyz.wanandroid.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wangyz.wanandroid.ConstantValue;
import com.wangyz.wanandroid.R;
import com.wangyz.wanandroid.adapter.CollectArticleAdapter;
import com.wangyz.wanandroid.base.BaseActivity;
import com.wangyz.wanandroid.bean.db.Collect;
import com.wangyz.wanandroid.bean.event.Event;
import com.wangyz.wanandroid.bean.model.AddCollect;
import com.wangyz.wanandroid.contract.Contract;
import com.wangyz.wanandroid.custom.SpaceItemDecoration;
import com.wangyz.wanandroid.presenter.CollectActivityPresenter;
import com.wangyz.wanandroid.util.LoginUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * @author wangyz
 * @time 2019/1/28 14:19
 * @description CollectActivity
 */
public class CollectActivity extends BaseActivity<Contract.CollectActivityView, CollectActivityPresenter> implements Contract.CollectActivityView {

    @BindView(R.id.back)
    ImageView mBack;

    @BindView(R.id.activity_collect_article_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.activity_collect_article_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading)
    WaveLoadingView mWaveLoadingView;

    @BindView(R.id.tv_empty)
    TextView mEmpty;

    @BindView(R.id.load)
    ImageView mLoading;

    @BindView(R.id.add)
    ImageView mAdd;

    private Dialog mAddCollectDialog;

    private Context mContext;

    private CollectArticleAdapter mAdapter;

    private int mPage;

    private List<Collect> mList = new ArrayList<>();

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();

        if (!LoginUtil.isLogin()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra(ConstantValue.EXTRA_KEY_REFERRER, ConstantValue.EXTRA_VALUE_COLLECT);
            startActivity(intent);
            finish();
            return;
        }

        EventBus.getDefault().register(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(mContext.getResources().getDimensionPixelSize(R.dimen.main_list_item_margin)));

        mAdapter = new CollectArticleAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.load(mPage);

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(0);
            mEmpty.setVisibility(View.GONE);
        });

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            mPresenter.load(mPage);
            mEmpty.setVisibility(View.GONE);
        });
    }

    @Override
    protected CollectActivityPresenter createPresenter() {
        return new CollectActivityPresenter();
    }

    @Override
    public void onLoading() {
        LogUtils.i();
    }

    @Override
    public void onLoadSuccess() {
        LogUtils.i();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadFailed() {
        LogUtils.e();
        ToastUtils.showShort(mContext.getString(R.string.load_failed));
        stopAnim();
        if (mAddCollectDialog != null && mAddCollectDialog.isShowing()) {
            mAddCollectDialog.dismiss();
        }
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onLoad(List<Collect> list) {
        if (mWaveLoadingView.getVisibility() == View.VISIBLE) {
            mWaveLoadingView.setVisibility(View.GONE);
        }
        if (list != null && list.size() > 0) {
            mEmpty.setVisibility(View.GONE);
            List<Collect> tempList = new ArrayList<>();
            list.stream().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.articleId).count();
                if (count <= 0) {
                    tempList.add(m);
                }
            });
            mList.addAll(tempList);
            mAdapter.setList(mList);
        } else {
            if (mList.size() > 0) {
                mEmpty.setVisibility(View.GONE);
            } else {
                mEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onRefresh(List<Collect> list) {
        if (list != null && list.size() > 0) {
            mEmpty.setVisibility(View.GONE);
            List<Collect> tempList = new ArrayList<>();
            list.stream().forEach(m -> {
                long count = mList.stream().filter(n -> n.articleId == m.articleId).count();
                if (count <= 0) {
                    tempList.add(m);
                }
            });
            mList.addAll(0, tempList);
            mAdapter.setList(mList);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onUnCollect(com.wangyz.wanandroid.bean.model.Collect result, int articleId) {
        stopAnim();
        if (result != null) {
            if (result.getErrorCode() == 0) {
                List<Collect> tempList = mList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
                mList.clear();
                mList.addAll(tempList);
                mAdapter.setList(mList);
                if (tempList != null && tempList.size() > 0) {
                    mEmpty.setVisibility(View.GONE);
                } else {
                    mEmpty.setVisibility(View.VISIBLE);
                }
            } else {
                ToastUtils.showShort(mContext.getString(R.string.uncollect_failed));
            }
        }
    }

    @Override
    public void onAddCollect(AddCollect result) {
        stopAnim();
        mAddCollectDialog.dismiss();
        if (result != null && result.getErrorCode() == 0) {
            AddCollect.DataBean bean = result.getData();
            Collect collect = new Collect();
            collect.articleId = bean.getId();
            collect.author = bean.getAuthor();
            collect.link = bean.getLink();
            collect.time = bean.getPublishTime();
            collect.title = bean.getTitle();
            mList.add(0, collect);
            mAdapter.setList(mList);
            mEmpty.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_COLLECT) {
            if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data.split(";")[0]);
                int originId = Integer.valueOf(event.data.split(";")[1]);
                mPresenter.unCollect(articleId, originId);
                startAnim();
            }
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.add)
    public void add() {
        mAddCollectDialog = new Dialog(this);
        mAddCollectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mAddCollectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAddCollectDialog.show();

        Window window = mAddCollectDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(20, 0, 20, 20);
        window.setAttributes(layoutParams);

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_collect, null, false);
        window.setContentView(view);

        EditText title = view.findViewById(R.id.dialog_add_collect_et_title);
        EditText author = view.findViewById(R.id.dialog_add_collect_et_author);
        EditText link = view.findViewById(R.id.dialog_add_collect_et_link);

        Button cancel = view.findViewById(R.id.dialog_add_collect_btn_cancel);
        Button ok = view.findViewById(R.id.dialog_add_collect_btn_commit);
        cancel.setOnClickListener(v -> {
            mAddCollectDialog.dismiss();
        });
        ok.setOnClickListener(v -> {
            if (TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(link.getText().toString())) {
                ToastUtils.showShort(mContext.getString(R.string.author_link_empty));
                return;
            }
            mPresenter.addCollect(title.getText().toString(), author.getText().toString(), link.getText().toString());
            startAnim();
        });
    }

    private void startAnim() {
        mLoading.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.loading);
        LinearInterpolator li = new LinearInterpolator();
        animation.setInterpolator(li);
        mLoading.startAnimation(animation);
    }

    private void stopAnim() {
        mLoading.setVisibility(View.GONE);
        mLoading.clearAnimation();
    }
}
