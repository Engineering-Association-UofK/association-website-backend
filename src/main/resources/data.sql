INSERT INTO admins (username, email, password_hash)
    SELECT 'admin', 'admin@test.com', 'hashed'
    WHERE NOT EXISTS (SELECT 1 FROM admins);