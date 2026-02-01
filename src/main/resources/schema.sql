CREATE TABLE IF NOT EXISTS admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    password_hash VARCHAR(255) NOT NULL,
    verified tinyint NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'active',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS admin_roles (
    admin_id INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE IF NOT EXISTS verification_codes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT NOT NULL,
    code VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE IF NOT EXISTS faqs (
    id INT PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE IF NOT EXISTS faqs_translations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    faq_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    lang VARCHAR(2) NOT NULL DEFAULT 'en',

    Foreign Key (faq_id) REFERENCES faqs(id)
);

CREATE TABLE IF NOT EXISTS blog_posts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    image_link TEXT,
    author_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL,

    FOREIGN KEY (author_id) REFERENCES admins(id)
);

CREATE TABLE IF NOT EXISTS team (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    description TEXT,
    image_link TEXT
);

CREATE TABLE IF NOT EXISTS visitors_messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gallery (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    image_link TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Student & Events Placeholders
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Certificate and decision tables
CREATE TABLE IF NOT EXISTS certificates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cert_hash VARCHAR(255) NOT NULL,
    student_id INT NOT NULL,
    event_id INT NOT NULL,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_path VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,

    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS documents (
    id INT PRIMARY KEY AUTO_INCREMENT,
    document_hash VARCHAR(255) NOT NULL,
    certifying_authority VARCHAR(255) NOT NULL,
    document_type VARCHAR(255) NOT NULL,
    document_reason VARCHAR(255) NOT NULL,
    document_author VARCHAR(255) NOT NULL,
    admin_id INT NOT NULL,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_path VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,

    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE IF NOT EXISTS bot_commands (
    id INT PRIMARY KEY AUTO_INCREMENT,
    keyword VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS bot_command_translations (
    command_id INT NOT NULL,
    text TEXT NOT NULL,
    language VARCHAR(10) NOT NULL,
    FOREIGN KEY (command_id) REFERENCES bot_commands(id)
);

CREATE TABLE IF NOT EXISTS bot_command_triggers (
    command_id INT NOT NULL,
    trigger_text VARCHAR(50) NOT NULL,
    language VARCHAR(10) NOT NULL,
    FOREIGN KEY (command_id) REFERENCES bot_commands(id)
);

CREATE TABLE IF NOT EXISTS bot_command_options (
    command_id INT NOT NULL,
    next_keyword VARCHAR(50) NOT NULL,
    FOREIGN KEY (command_id) REFERENCES bot_commands(id)
);

CREATE TABLE IF NOT EXISTS technical_feedback (
     id INT PRIMARY KEY AUTO_INCREMENT,
     message TEXT NOT NULL,
     sender_contact VARCHAR(255),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );

 CREATE TABLE IF NOT EXISTS contact_list (
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL,
     email VARCHAR(255) NOT NULL
 );