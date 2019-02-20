package com.wangyz.common.bean.model;

import com.wangyz.common.bean.db.Tree;

import java.util.List;

/**
 * @author wangyz
 * @time 2019/1/23 10:47
 * @description TreeInfo
 */
public class TreeInfo {

    public int parentId;

    public int treeId;

    public String name;

    public List<Tree> child;

}
