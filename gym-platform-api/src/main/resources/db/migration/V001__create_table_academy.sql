CREATE TABLE academy
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    cnpj       VARCHAR(18),
    phone      VARCHAR(20),
    email      VARCHAR(150),
    address    VARCHAR(255),
    active     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP  NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP  NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX ux_academy_cnpj
    ON academy (cnpj) WHERE cnpj IS NOT NULL;

CREATE UNIQUE INDEX ux_academy_phone
    ON academy (phone) WHERE phone IS NOT NULL;

CREATE INDEX idx_academy_name
    ON academy (name);
