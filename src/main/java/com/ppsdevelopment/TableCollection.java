package com.ppsdevelopment;

public class TableCollection {
    private String caption;
    private int index;
    private String loc;
    private int count;

    public TableCollection(String caption, int index, String loc) {
        this.caption = caption;
        this.index = index;
        this.loc = loc;
    }

    public String getCaption() {
        return caption;
    }

    public int getIndex() {
        return index;
    }

    public String getLoc() {
        return loc;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setCount(int count) {
        this.count=count;
    }

    public int getCount() {
        return count;
    }
}
