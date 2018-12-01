package com.example.joshuansu.myapplication.components.note;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Note {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("updatedAt")
    @Expose
    private LocalDate updatedAt;

    public Note() {
    }

    public Note(int id, String title, String content, int userId, LocalDate updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
