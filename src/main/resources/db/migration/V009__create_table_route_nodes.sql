CREATE TABLE tr.route_nodes
(
  id BIGSERIAL NOT NULL,
  event_id BIGINT NOT NULL,
  trail_id INTEGER NOT NULL,
  CONSTRAINT route_nodes_pkey PRIMARY KEY (id),
  CONSTRAINT route_nodes_event_id_fkey FOREIGN KEY (event_id)
  REFERENCES tr.events (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);