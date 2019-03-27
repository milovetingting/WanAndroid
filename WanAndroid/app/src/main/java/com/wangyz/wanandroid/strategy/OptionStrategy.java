package com.wangyz.wanandroid.strategy;

import com.wangyz.wanandroid.bean.model.Option;

/**
 * @author wangyz
 * @time 2019/3/27 14:21
 * @description OptionStrategy
 */
public interface OptionStrategy {

    /**
     * 处理Option
     *
     * @param option
     */
    void handleOption(Option option);

}
