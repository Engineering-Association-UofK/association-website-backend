INSERT INTO admins (name, password_hash, verified, role)
    SELECT 'admin', '{noop}hashed', 1, 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM admins);