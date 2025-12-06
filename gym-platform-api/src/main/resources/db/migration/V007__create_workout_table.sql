CREATE TABLE workouts
(
    id           UUID         NOT NULL,
    member_id    UUID         NOT NULL,
    academy_id   UUID         NOT NULL,
    teacher_id   UUID         NOT NULL,
    name         VARCHAR(255) NOT NULL,
    objective    VARCHAR(500) NOT NULL,
    observations TEXT,
    status       VARCHAR(20)  NOT NULL,
    active       BOOLEAN      NOT NULL DEFAULT TRUE,
    start_at     TIMESTAMP    NOT NULL,
    end_at       TIMESTAMP,
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    NOT NULL,

    CONSTRAINT pk_workouts PRIMARY KEY (id),

    -- Status permitido (alinha com enum WorkoutStatus em código)
    CONSTRAINT ck_workouts_status CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELED')),

    -- Regra básica de integridade de período
    CONSTRAINT ck_workouts_period CHECK (end_at IS NULL OR end_at >= start_at),

    -- Relacionamentos
    CONSTRAINT fk_workouts_member
        FOREIGN KEY (member_id)
            REFERENCES members (id),

    CONSTRAINT fk_workouts_academy
        FOREIGN KEY (academy_id)
            REFERENCES academy (id),

    CONSTRAINT fk_workouts_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teachers (id)
);


CREATE INDEX idx_workouts_member
    ON workouts (member_id);

CREATE INDEX idx_workouts_academy
    ON workouts (academy_id);

CREATE INDEX idx_workouts_teacher
    ON workouts (teacher_id);

CREATE INDEX idx_workouts_status
    ON workouts (status);

CREATE INDEX idx_workouts_start_at
    ON workouts (start_at);