CREATE TABLE bp_user
(
    user_id       INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    first_name    VARCHAR(30)                                                                             NOT NULL,
    last_name     VARCHAR(30)                                                                             NOT NULL,
    date_of_birth DATE                                                                                    NOT NULL,
    email         VARCHAR(100) UNIQUE                                                                     NOT NULL,
    username      VARCHAR(30) UNIQUE                                                                      NOT NULL,
    password      VARCHAR(50)                                                                             NOT NULL,
    user_role     VARCHAR(30) CHECK ( user_role in ('USER', 'ADMIN', 'EMPLOYEE', 'MANAGER', 'DIRECTOR') ) NOT NULL,
    created_at    TIMESTAMP                                                                               NOT NULL,
    created_who   INT                                                                                     NOT NULL,
    updated_at    TIMESTAMP,
    updated_who   INT DEFAULT NULL,
    FOREIGN KEY (updated_who) REFERENCES bp_user (user_id)
);

CREATE TABLE business_process
(
    process_id  INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title       VARCHAR(30) UNIQUE NOT NULL,
    created_at  TIMESTAMP          NOT NULL,
    created_who INT                NOT NULL,
    updated_at  TIMESTAMP,
    updated_who INT DEFAULT NULL,
    FOREIGN KEY (updated_who) REFERENCES bp_user (user_id)
);

CREATE TABLE process_stage
(
    stage_id     INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    title        VARCHAR(30)                                                                              NOT NULL,
    stage_result VARCHAR(30) CHECK ( stage_result in ('NOT_STARTED', 'IN_PROGRESS', 'APPROVE', 'REJECT')) NOT NULL,
    created_at   TIMESTAMP                                                                                NOT NULL,
    created_who  INT                                                                                      NOT NULL,
    updated_at   TIMESTAMP,
    updated_who  INT DEFAULT NULL,
    FOREIGN KEY (updated_who) REFERENCES bp_user (user_id),
    process_id   INT REFERENCES business_process (process_id) ON DELETE CASCADE
);

CREATE TABLE process_action
(
    action_id     INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    action_result VARCHAR(30) CHECK ( action_result in ('APPROVE', 'REJECT')) DEFAULT NULL,
    created_at    TIMESTAMP NOT NULL,
    created_who   INT       NOT NULL,
    updated_at    TIMESTAMP,
    updated_who   INT                                                         DEFAULT NULL,
    FOREIGN KEY (updated_who) REFERENCES bp_user (user_id),
    stage_id      INT REFERENCES process_stage (stage_id) ON DELETE CASCADE,
    task_owner_id INT REFERENCES bp_user (user_id) ON DELETE RESTRICT
);