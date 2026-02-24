package edu.uofk.ea.association_website_backend.model.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequest {
    @NotBlank(message = "Old password is required")
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long, and must not exceed 40 characters.")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long, and must not exceed 40 characters.")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    @Size(min = 8, max = 40, message = "Password must be at least 8 characters long, and must not exceed 40 characters.")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
