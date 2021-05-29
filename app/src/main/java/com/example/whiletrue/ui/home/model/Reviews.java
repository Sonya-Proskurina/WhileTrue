package com.example.whiletrue.ui.home.model;

public class Reviews {
    String name;
    String reviews;
    String image;
    int star;

    public Reviews(String name, String reviews, String image, int star) {
        this.name = name;
        this.reviews = reviews;
        this.image = image;
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
