package com.wangyz.wanandroid.strategy;

/**
 * @author wangyz
 * @time 2019/3/27 14:23
 * @description OptionStrategyFactory
 */
public class OptionStrategyFactory {

    private static final Class[] STRATEGY_NAME = {ShareStrategy.class, LinkStrategy.class, BrowserStrategy.class};

    /**
     * 获取策略
     *
     * @param strategy
     * @return
     */
    public static OptionStrategy getStrategy(int strategy) {
        OptionStrategy optionStrategy = null;
        try {
            optionStrategy = (OptionStrategy) Class.forName(STRATEGY_NAME[strategy].getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return optionStrategy;
    }
}
