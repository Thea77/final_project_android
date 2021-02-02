package com.example.afinal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorResponse {

    @SerializedName("data")
    List<myAuthor> authorList;

    public AuthorResponse(List<myAuthor> authorList) {
        this.authorList = authorList;
    }

    public List<myAuthor> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<myAuthor> authorList) {
        this.authorList = authorList;
    }
}
