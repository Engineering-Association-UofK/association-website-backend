package edu.uofk.ea.association_website_backend.model.gallery;

public class GalleryKeywordResponse {
    private String keyword;
    private String title;
    private String imageLink;

    public GalleryKeywordResponse(String keyword, String title, String imageLink) {
        this.keyword = keyword;
        this.title = title;
        this.imageLink = imageLink;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
