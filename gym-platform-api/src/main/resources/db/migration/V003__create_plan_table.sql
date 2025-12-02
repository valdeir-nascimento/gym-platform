CREATE TABLE IF NOT EXISTS plan
(
    id                    UUID PRIMARY KEY,
    academy_id            UUID           NOT NULL,
    name                  VARCHAR(150)   NOT NULL,
    description           VARCHAR(255),
    price                 NUMERIC(10, 2) NOT NULL,
    billing_period_months INTEGER        NOT NULL,
    active                BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at            TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP    NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_plan_academy
        FOREIGN KEY (academy_id)
            REFERENCES academy (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_plan_academy_name
    ON plan (academy_id, name);

CREATE INDEX IF NOT EXISTS idx_plan_academy
    ON plan (academy_id);

