package com.wangyz.common.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * @author wangyz
 * @time 2019/1/23 9:35
 * @description System
 */
public class Tree extends LitePalSupport {

    public int id;

    public int parentId;

    public int treeId;

    public String name;

}
