-- Table: tr.user_roles

-- DROP TABLE tr.user_roles;

CREATE TABLE tr.user_roles
(
  user_id BIGINT  NOT NULL,
  role_id INTEGER NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id)
  REFERENCES tr.roles (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id)
  REFERENCES tr.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);