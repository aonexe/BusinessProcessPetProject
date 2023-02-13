INSERT INTO bp_user (first_name, last_name, date_of_birth,
                     email, username, password, user_role,
                     created_at, created_who)
VALUES ('admin', 'admin', '1998-01-16',
        'admin@mail.com', 'admin', 'admin',
        'ADMIN', now(), 1);