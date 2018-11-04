-- Table: tr.event_users

-- DROP TABLE tr.event_users;

CREATE TABLE tr.event_users
(
  event_id BIGINT    NOT NULL,
  user_id BIGINT    NOT NULL,
  status VARCHAR   NOT NULL,
  CONSTRAINT event_users_pkey PRIMARY KEY (event_id, user_id),
  CONSTRAINT event_users_user_id_fkey FOREIGN KEY (user_id)
  REFERENCES tr.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT event_users_event_id_fkey FOREIGN KEY (event_id)
  REFERENCES tr.events (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
