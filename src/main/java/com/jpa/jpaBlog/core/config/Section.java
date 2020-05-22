package com.jpa.jpaBlog.core.config;

public enum Section {
    HOME("Home"),
    POST("Post"),
    CATEGORY("Category");

    private String value;

    Section(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
