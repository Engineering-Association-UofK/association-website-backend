CREATE TABLE IF NOT EXISTS admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    password_hash VARCHAR(255) NOT NULL,
    verified tinyint NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'active',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS old_admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
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

CREATE TABLE IF NOT EXISTS generics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    keyword VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS generic_translations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    gen_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    lang VARCHAR(2) NOT NULL DEFAULT 'en',

    Foreign Key (gen_id) REFERENCES generics(id)
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

-- Events and Participation Tables

CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name_ar VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    max_participants INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS event_components (
    id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    max_score DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);

-- Relations

CREATE TABLE IF NOT EXISTS event_participation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    event_id INT NOT NULL,
    student_id INT NOT NULL,

    UNIQUE (event_id, student_id),

    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS student_component_scores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    participant_id INT NOT NULL,
    component_id INT NOT NULL,
    score DECIMAL(10, 2),

    UNIQUE (participant_id, component_id),
    FOREIGN KEY (participant_id) REFERENCES event_participation(id) ON DELETE CASCADE,
    FOREIGN KEY (component_id) REFERENCES event_components(id) ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS contact_list (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS activity_events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id INT NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    meta_data JSON,

    INDEX (admin_id),
    INDEX (created_at),
    INDEX (admin_id, created_at),

    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE IF NOT EXISTS daily_activity (
    admin_id INT NOT NULL,
    date DATE NOT NULL,
    activity_count INT DEFAULT 0,
    PRIMARY KEY (admin_id, date),
    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE IF NOT EXISTS storage (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,
    public_id VARCHAR(255) NOT NULL UNIQUE,
    url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    reference_num INT NOT NULL
);

CREATE TABLE IF NOT EXISTS news (
    id INT PRIMARY KEY AUTO_INCREMENT,
    storage_id INT NOT NULL UNIQUE,
    alt TEXT,

    FOREIGN KEY (storage_id) REFERENCES storage(id)
);

CREATE TABLE IF NOT EXISTS storage_references (
    id INT PRIMARY KEY AUTO_INCREMENT,
    storage_id INT NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (storage_id) REFERENCES storage(id) ON DELETE CASCADE
);