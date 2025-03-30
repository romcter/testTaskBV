CREATE TABLE sport_events_aud (
                                  id BIGINT NOT NULL,
                                  rev BIGINT NOT NULL,
                                  revtype SMALLINT NOT NULL,
                                  description VARCHAR(255),
                                  home_team VARCHAR(255),
                                  away_team VARCHAR(255),
                                  start_time TIMESTAMP,
                                  sport VARCHAR(255),
                                  country VARCHAR(255),
                                  competition VARCHAR(255),
                                  settled BOOLEAN,
                                  created_date TIMESTAMP,
                                  last_modified_date TIMESTAMP,
                                  created_by VARCHAR(255),
                                  last_modified_by VARCHAR(255),
                                  version BIGINT,
                                  PRIMARY KEY (id, rev)
);

CREATE TABLE markets_aud (
                             id BIGINT NOT NULL,
                             rev BIGINT NOT NULL,
                             revtype SMALLINT NOT NULL,
                             description VARCHAR(255),
                             status VARCHAR(50),
                             settled BOOLEAN,
                             sport_event_id BIGINT,
                             created_date TIMESTAMP,
                             last_modified_date TIMESTAMP,
                             created_by VARCHAR(255),
                             last_modified_by VARCHAR(255),
                             version BIGINT,
                             PRIMARY KEY (id, rev)
);

CREATE TABLE outcomes_aud (
                              id BIGINT NOT NULL,
                              rev BIGINT NOT NULL,
                              revtype SMALLINT NOT NULL,
                              description VARCHAR(255),
                              settled BOOLEAN,
                              price DOUBLE PRECISION,
                              result VARCHAR(50),
                              market_id BIGINT,
                              created_date TIMESTAMP,
                              last_modified_date TIMESTAMP,
                              created_by VARCHAR(255),
                              last_modified_by VARCHAR(255),
                              version BIGINT,
                              PRIMARY KEY (id, rev)
);

CREATE TABLE revinfo (
                         rev BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         revtstmp BIGINT
);

ALTER TABLE sport_events_aud ADD FOREIGN KEY (rev) REFERENCES revinfo(rev);
ALTER TABLE markets_aud ADD FOREIGN KEY (rev) REFERENCES revinfo(rev);
ALTER TABLE outcomes_aud ADD FOREIGN KEY (rev) REFERENCES revinfo(rev);

UPDATE sport_events SET settled = TRUE, last_modified_date = '2025-03-29 15:00:00', last_modified_by = 'system', version = 1 WHERE id = 1;
UPDATE markets SET status = 'CLOSED', last_modified_date = '2025-03-29 15:05:00', last_modified_by = 'system', version = 1 WHERE id = 1;
UPDATE outcomes SET result = 'WIN', settled = TRUE, last_modified_date = '2025-03-29 15:10:00', last_modified_by = 'system', version = 1 WHERE id = 1;

INSERT INTO revinfo (revtstmp) VALUES (EXTRACT(EPOCH FROM TIMESTAMP '2025-03-29 10:00:00') * 1000);
INSERT INTO sport_events_aud (id, rev, revtype, description, home_team, away_team, start_time, sport, country, competition, settled, created_date, created_by, version)
VALUES (1, 1, 0, 'Premier League Match', 'Manchester United', 'Liverpool', '2025-04-01 15:00:00', 'Football', 'England', 'Premier League', FALSE, '2025-03-29 10:00:00', 'system', 0);

INSERT INTO revinfo (revtstmp) VALUES (EXTRACT(EPOCH FROM TIMESTAMP '2025-03-29 15:00:00') * 1000);
INSERT INTO sport_events_aud (id, rev, revtype, description, home_team, away_team, start_time, sport, country, competition, settled, created_date, last_modified_date, created_by, last_modified_by, version)
VALUES (1, 2, 1, 'Premier League Match', 'Manchester United', 'Liverpool', '2025-04-01 15:00:00', 'Football', 'England', 'Premier League', TRUE, '2025-03-29 10:00:00', '2025-03-29 15:00:00', 'system', 'system', 1);