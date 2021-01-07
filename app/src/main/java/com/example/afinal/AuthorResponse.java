package com.example.afinal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorResponse {

    @SerializedName("data")
    List<Author> authorList;

    public AuthorResponse(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
