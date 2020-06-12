package com.example.googlenews;

public class Model {
    String title;
    String imageUrl;
    String description;
    String author;
    String content;

    public Model(String title, String imageUrl, String description, String author, String content) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.author = author;
        this.content = content;
    }
}
