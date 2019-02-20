package com.wangyz.common.contract;

/**
 * @author wangyz
 * @time 2019/1/17 15:34
 * @description Contract
 */
public class Contract {

    /**
     * LoadingView
     */
    public interface BaseView {

        /**
         * 加载中
         */
        void onLoading();

        /**
         * 加载成功
         */
        void onLoadSuccess();

        /**
         * 加载失败
         */
        void onLoadFailed();

    }

}
