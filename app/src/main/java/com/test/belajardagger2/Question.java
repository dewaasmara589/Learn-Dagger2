package com.test.belajardagger2;

import com.google.gson.annotations.SerializedName;

public class Question {

    // We will make a retrofit call to receive the questions (TITLE & ID)
    @SerializedName("title")
    private final String title;

    @SerializedName("question_id")
    private final String id;

    public Question(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
