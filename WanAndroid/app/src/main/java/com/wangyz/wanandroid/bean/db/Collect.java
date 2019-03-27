package com.wangyz.wanandroid.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * @author wangyz
 * @time 2019/1/24 18:24
 * @description Collect
 */
public class Collect extends LitePalSupport {

    public int id;

    public int articleId;

    public int originId;

    public String author;

    public String title;

    public String category;

    public long time;

    public String link;

}
