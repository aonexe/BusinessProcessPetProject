CREATE TABLE action_result
(
    action_result_id   INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    action_result_name VARCHAR(30) UNIQUE NOT NULL,
    created_at         TIMESTAMP          NOT NULL,
    created_who        INT                NOT NULL,
    updated_at         TIMESTAMP,
    updated_who        INT DEFAULT NULL,
    FOREIGN KEY (updated_who) REFERENCES bp_user (user_id)
);

INSERT INTO action_result (action_result_name, created_at, created_who)
VALUES ('APPROVE', '2023-02-07 00:00:00-00', 1),
       ('REJECT', '2023-02-07 00:00:00-00', 1);

ALTER TABLE action
    ADD COLUMN action_result_id INT REFERENCES action_result (action_result_id);

UPDATE action
SET action_result_id = action_result.action_result_id
FROM action_result
WHERE action_result.action_result_name = action.action_result;

ALTER TABLE action
    DROP COLUMN action_result;

