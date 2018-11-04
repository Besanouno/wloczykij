-- Table: tr.user_relations

-- DROP TABLE tr.user_relations;

CREATE TABLE tr.user_relations
(
  user_id         BIGINT    NOT NULL,
  related_user_id BIGINT    NOT NULL,
  CONSTRAINT user_relations_pkey PRIMARY KEY (user_id, related_user_id),
  CONSTRAINT user_relations_user_id_fkey FOREIGN KEY (user_id)
  REFERENCES tr.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_relations_related_user_id_fkey FOREIGN KEY (related_user_id)
  REFERENCES tr.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
