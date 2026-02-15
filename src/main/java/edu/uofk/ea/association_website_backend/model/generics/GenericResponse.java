package edu.uofk.ea.association_website_backend.model.generics;


public class GenericResponse {

    private String keyword;
    private String title;
    private String body;

    public GenericResponse() {}

    public GenericResponse(String keyword,String title, String body) {
        this.keyword = keyword;
        this.title = title;
        this.body = body;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
