package com.wangyz.wanandroid.bean.model;

/**
 * @author wangyz
 * @time 2019/3/22 8:39
 * @description Update
 */
public class Update {

    /**
     * versionCode : 2
     * versionName : 1.0.1
     * MD5 : BF7F195CB5F406561C7970EA594D3045
     * size : 3.5M
     * detail : 修复BUG
     */

    private int versionCode;
    private String versionName;
    private String MD5;
    private String size;
    private String detail;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Update{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", MD5='" + MD5 + '\'' +
                ", size='" + size + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
