CREATE TABLE user_role
(
    user_id INTEGER REFERENCES bp_user (user_id),
    role_id INTEGER REFERENCES role (role_id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);
