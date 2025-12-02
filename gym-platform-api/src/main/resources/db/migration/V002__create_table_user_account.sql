CREATE TABLE IF NOT EXISTS user_account
(
    id         UUID PRIMARY KEY,
    full_name  VARCHAR(150)       NOT NULL,
    email      VARCHAR(150)       NOT NULL,
    phone      VARCHAR(20) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    active     BOOLEAN            NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP          NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP          NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_user_account_email
    ON user_account ((LOWER (email)));

CREATE TABLE IF NOT EXISTS user_account_roles
(
    user_account_id UUID        NOT NULL,
    role            VARCHAR(30) NOT NULL,
    CONSTRAINT fk_user_account_roles_user FOREIGN KEY (user_account_id)
        REFERENCES user_account (id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_user_account_roles_role
    ON user_account_roles (role);

