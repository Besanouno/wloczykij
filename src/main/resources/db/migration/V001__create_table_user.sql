-- Table: tr.users

-- DROP TABLE tr.users;

CREATE TABLE tr.users
(
  id            BIGSERIAL              NOT NULL,
  login         CHARACTER VARYING(16)  NOT NULL UNIQUE,
  email         CHARACTER VARYING(255) NOT NULL UNIQUE,
  first_name    CHARACTER VARYING(255) NOT NULL,
  last_name     CHARACTER VARYING(255) NOT NULL,
  active        BOOLEAN                NOT NULL DEFAULT TRUE,
  password      TEXT                   NOT NULL,
  year_of_birth INTEGER                NOT NULL,
  city          CHARACTER VARYING(255),
  creation_date DATE,
  CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);
