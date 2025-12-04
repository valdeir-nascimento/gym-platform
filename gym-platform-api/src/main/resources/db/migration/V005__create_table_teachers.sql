CREATE TABLE teachers
(
    id              UUID         NOT NULL,
    user_account_id UUID         NOT NULL,
    academy_id      UUID         NOT NULL,
    specialization  VARCHAR(255) NOT NULL,
    status          VARCHAR(20)  NOT NULL,
    hired_at        TIMESTAMP(6) NOT NULL,
    created_at      TIMESTAMP(6) NOT NULL,
    updated_at      TIMESTAMP(6) NOT NULL,

    CONSTRAINT pk_teachers PRIMARY KEY (id),

    -- Um mesmo usuário só pode ter um registro de professor por academia
    CONSTRAINT uk_teachers_user_academy UNIQUE (user_account_id, academy_id)
);

-- Índices auxiliares para consultas mais comuns
CREATE INDEX idx_teachers_user_account ON teachers (user_account_id);
CREATE INDEX idx_teachers_academy ON teachers (academy_id);
CREATE INDEX idx_teachers_status ON teachers (status);

ALTER TABLE teachers
    ADD CONSTRAINT fk_teachers_user_account
        FOREIGN KEY (user_account_id)
            REFERENCES user_account (id);

ALTER TABLE teachers
    ADD CONSTRAINT fk_teachers_academy
        FOREIGN KEY (academy_id)
            REFERENCES academy (id);
