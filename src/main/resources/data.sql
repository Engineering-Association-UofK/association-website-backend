INSERT INTO admins (name, password_hash, verified)
    SELECT 'admin', '$2a$12$jYsFqXS.o6daj//dsz1OQedMdJ/5zQBWxJB3Qk/ICbDUKGtAVE7wi', 1
    WHERE NOT EXISTS (SELECT 1 FROM admins); -- The password is 'itshashed', hashed with BCrypt with strength = 12
INSERT INTO admin_roles (admin_id, role)
    SELECT 1, 'ROLE_SUPER_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM admin_roles);