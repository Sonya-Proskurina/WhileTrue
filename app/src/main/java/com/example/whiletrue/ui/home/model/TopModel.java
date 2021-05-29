package com.example.whiletrue.ui.home.model;

public class TopModel {
    private String title;
    private String please;
    private int star;
    private int num;

    public TopModel(String title, String please, int star, int num) {
        this.title = title;
        this.please = please;
        this.star = star;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlease() {
        return please;
    }

    public void setPlease(String please) {
        this.please = please;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
