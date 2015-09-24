package com.freeman.example.apitest.model;

/**
 * Created by freeman on 9/23/15.
 */
public class NewsInfo {

    private String desc;
    private long time;

    public NewsInfo() {
    }

    public NewsInfo(String desc, long time) {
        this.desc = desc;
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "desc='" + desc + '\'' +
                ", time=" + time +
                '}';
    }
}
