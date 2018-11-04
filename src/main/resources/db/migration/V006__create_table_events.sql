-- Table: tr.events

-- DROP TABLE tr.events;

CREATE TABLE tr.events
(
  id                BIGSERIAL                   NOT NULL,
  name              CHARACTER VARYING(16)       NOT NULL,
  description       CHARACTER VARYING(1000),
  participants_limit INTEGER,
  place_of_meeting  CHARACTER VARYING(255)      NOT NULL,
  start_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  end_date          TIMESTAMP WITHOUT TIME ZONE,
  public_access     BOOLEAN                     NOT NULL DEFAULT TRUE,
  initiator_id      BIGINT                      NOT NULL,
  guid              CHARACTER VARYING(36)       NOT NULL UNIQUE,
  CONSTRAINT events_pkey PRIMARY KEY (id),
  CONSTRAINT events_initiator_id_fkey FOREIGN KEY (initiator_id)
  REFERENCES tr.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS = FALSE
);
