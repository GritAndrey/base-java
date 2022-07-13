DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS section;
DROP TABLE IF EXISTS resume;
DROP SEQUENCE IF EXISTS contact_id_seq;
DROP SEQUENCE IF EXISTS section_id_seq;
CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

CREATE TABLE section
(
    id          SERIAL PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    content     jsonb    NOT NULL
);
CREATE UNIQUE INDEX section_idx
    ON section (resume_uuid, type);
