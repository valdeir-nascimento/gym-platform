CREATE TABLE IF NOT EXISTS members
(
    id              UUID PRIMARY KEY,
    user_account_id UUID        NOT NULL,
    academy_id      UUID        NOT NULL,
    plan_id         UUID        NOT NULL,
    status          VARCHAR(20) NOT NULL,
    joined_at       TIMESTAMP(6) NOT NULL,
    created_at      TIMESTAMP(6) NOT NULL,
    updated_at      TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_members_user FOREIGN KEY (user_account_id) REFERENCES user_account (id),
    CONSTRAINT fk_members_academy FOREIGN KEY (academy_id) REFERENCES academy (id),
    CONSTRAINT fk_members_plan FOREIGN KEY (plan_id) REFERENCES plan (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_members_user_academy
    ON members (user_account_id, academy_id);

CREATE INDEX IF NOT EXISTS idx_members_user_account
    ON members (user_account_id);

CREATE INDEX IF NOT EXISTS idx_members_academy
    ON members (academy_id);

CREATE INDEX IF NOT EXISTS idx_members_plan
    ON members (plan_id);

