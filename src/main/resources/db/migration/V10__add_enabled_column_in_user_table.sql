ALTER TABLE bp_user
    ADD COLUMN enabled BOOLEAN NOT NULL DEFAULT TRUE;

UPDATE bp_user
SET enabled = TRUE;
