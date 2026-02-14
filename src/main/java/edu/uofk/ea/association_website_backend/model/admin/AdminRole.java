package edu.uofk.ea.association_website_backend.model.admin;

public enum AdminRole{
    ROLE_CONTENT_EDITOR, // Allows editing of side wide static content.
    ROLE_PAPER_CERTIFIER, // Specific to the workflow of reviewing and certifying papers.
    ROLE_PAPER_VIEWER, // See and download any document.
    ROLE_USER_MANAGER, // See and manage students and other users.
    ROLE_ADMIN_MANAGER, // See and manage admins.
    ROLE_CERTIFICATE_ISSUER, // Allows issuing certificates.
    ROLE_BLOG_MANAGER, // Allows creating and managing blog posts.
    ROLE_TECHNICAL_SUPPORT, // Allows managing technical feedback and system logs.
    ROLE_SUPER_ADMIN // The one who can break everything with a single click.
}
