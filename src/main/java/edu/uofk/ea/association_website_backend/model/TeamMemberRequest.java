package edu.uofk.ea.association_website_backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TeamMemberRequest {

    private int id;

    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 255, message = "Name cannot exceed 255 characters.")
    private String name;

    @NotBlank(message = "Position cannot be blank.")
    @Size(max = 255, message = "Position cannot exceed 255 characters.")
    private String position;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotBlank(message = "Image link cannot be blank.")
    private String imageLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}