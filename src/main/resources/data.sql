INSERT INTO admins (name, password_hash, verified, role)
    SELECT 'admin', '$2a$12$L3rtqgrcilI5rzeeGdjtTe9LSCTrpotSPsoEQ/p.M9./QoSuOZWu6', 1, 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM admins); -- The password is 'hashed', hashed with BCrypt with strength = 12
