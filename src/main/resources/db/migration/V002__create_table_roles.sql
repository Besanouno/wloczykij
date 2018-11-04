-- Table: tr.roles

-- DROP TABLE tr.roles;

CREATE TABLE tr.roles
(
  id   SERIAL                 NOT NULL,
  name CHARACTER VARYING(255) NOT NULL,
  CONSTRAINT roles_pkey PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
);