package edu.uofk.ea.association_website_backend.model.activity;

public enum ActivityType {
    LOGIN,
    LOGOUT,

    // Admin Management
    CREATE_ADMIN,
    UPDATE_ADMIN,
    DELETE_ADMIN,
    UPDATE_ADMIN_PROFILE,
    CHANGE_PASSWORD,
    UPDATE_EMAIL,

    // Blog
    CREATE_BLOG,
    UPDATE_BLOG,
    DELETE_BLOG,

    // Gallery
    CREATE_GALLERY,
    UPDATE_GALLERY,
    DELETE_GALLERY,

    // Generics
    CREATE_GENERIC,
    UPDATE_GENERIC,
    DELETE_GENERIC,

    // Documents & Certificates
    CREATE_DOCUMENT,
    UPDATE_DOCUMENT,
    DELETE_DOCUMENT,
    CREATE_CERTIFICATE,
    UPDATE_CERTIFICATE,
    DELETE_CERTIFICATE,
    DOWNLOAD_DOCUMENT,
    DOWNLOAD_CERTIFICATE,

    // Team
    CREATE_TEAM_MEMBER,
    UPDATE_TEAM_MEMBER,
    DELETE_TEAM_MEMBER,

    // Bot
    CREATE_BOT_COMMAND,
    UPDATE_BOT_COMMAND,
    DELETE_BOT_COMMAND,

    // Other
    SIGN_CLOUDINARY_REQUEST
}
