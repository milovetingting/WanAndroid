package com.wangyz.common.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * @author wangyz
 * @time 2019/1/18 14:36
 * @description Article
 */
public class Article extends LitePalSupport {

    /**
     * 首页文章
     */
    public static final int TYPE_HOME = 1;

    /**
     * 体系
     */
    public static final int TYPE_TREE = 2;

    /**
     * 项目
     */
    public static final int TYPE_PROJECT = 3;

    /**
     * 公众号
     */
    public static final int TYPE_WX = 4;

    public int id;

    public int type;

    public int articleId;

    public String title;

    public String des;

    public int authorId;

    public String author;

    public String category;

    public long time;

    public String link;

    public String pic;

    public int projectType;

    public int treeType;

    public boolean collect;

}
