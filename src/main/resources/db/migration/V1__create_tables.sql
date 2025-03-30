CREATE TABLE sport_events (
                              id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                              description VARCHAR(255) NOT NULL,
                              home_team VARCHAR(255) NOT NULL,
                              away_team VARCHAR(255) NOT NULL,
                              start_time TIMESTAMP NOT NULL,
                              sport VARCHAR(255) NOT NULL,
                              country VARCHAR(255) NOT NULL,
                              competition VARCHAR(255) NOT NULL,
                              settled BOOLEAN NOT NULL,
                              created_date TIMESTAMP NOT NULL,
                              last_modified_date TIMESTAMP,
                              created_by VARCHAR(255),
                              last_modified_by VARCHAR(255),
                              version BIGINT DEFAULT 0
);

CREATE TABLE markets (
                         id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         description VARCHAR(255) NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         settled BOOLEAN NOT NULL,
                         sport_event_id BIGINT NOT NULL,
                         created_date TIMESTAMP NOT NULL,
                         last_modified_date TIMESTAMP,
                         created_by VARCHAR(255),
                         last_modified_by VARCHAR(255),
                         version BIGINT DEFAULT 0,
                         FOREIGN KEY (sport_event_id) REFERENCES sport_events(id) ON DELETE CASCADE
);

CREATE TABLE outcomes (
                          id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          description VARCHAR(255) NOT NULL,
                          settled BOOLEAN NOT NULL,
                          price DOUBLE PRECISION NOT NULL,
                          result VARCHAR(50),
                          market_id BIGINT NOT NULL,
                          created_date TIMESTAMP NOT NULL,
                          last_modified_date TIMESTAMP,
                          created_by VARCHAR(255),
                          last_modified_by VARCHAR(255),
                          version BIGINT DEFAULT 0,
                          FOREIGN KEY (market_id) REFERENCES markets(id) ON DELETE CASCADE
);


INSERT INTO sport_events (description, home_team, away_team, start_time, sport, country, competition, settled, created_date, created_by)
VALUES
    ('Premier League Match', 'Manchester United', 'Liverpool', '2025-04-01 15:00:00', 'Football', 'England', 'Premier League', FALSE, '2025-03-29 10:00:00', 'system'),
    ('NBA Finals Game 1', 'Lakers', 'Celtics', '2025-04-02 20:00:00', 'Basketball', 'USA', 'NBA', FALSE, '2025-03-29 11:00:00', 'system'),
    ('Wimbledon Semi-Final', 'Djokovic', 'Nadal', '2025-04-03 14:00:00', 'Tennis', 'UK', 'Wimbledon', FALSE, '2025-03-29 12:00:00', 'system'),
    ('La Liga Clash', 'Barcelona', 'Real Madrid', '2025-04-04 19:00:00', 'Football', 'Spain', 'La Liga', FALSE, '2025-03-29 13:00:00', 'system'),
    ('Stanley Cup Final', 'Maple Leafs', 'Penguins', '2025-04-05 18:00:00', 'Hockey', 'Canada', 'NHL', FALSE, '2025-03-29 14:00:00', 'system');

INSERT INTO markets (description, status, settled, sport_event_id, created_date, created_by)
VALUES
    ('Match Winner', 'OPEN', FALSE, 1, '2025-03-29 10:05:00', 'system'),
    ('Over/Under 2.5 Goals', 'OPEN', FALSE, 1, '2025-03-29 10:06:00', 'system'),
    ('Game Winner', 'OPEN', FALSE, 2, '2025-03-29 11:05:00', 'system'),
    ('Total Points Over 200', 'OPEN', FALSE, 2, '2025-03-29 11:06:00', 'system'),
    ('Set Winner', 'OPEN', FALSE, 3, '2025-03-29 12:05:00', 'system'),
    ('Total Games Over 20', 'OPEN', FALSE, 3, '2025-03-29 12:06:00', 'system'),
    ('Match Winner', 'OPEN', FALSE, 4, '2025-03-29 13:05:00', 'system'),
    ('Both Teams to Score', 'OPEN', FALSE, 4, '2025-03-29 13:06:00', 'system'),
    ('Series Winner', 'OPEN', FALSE, 5, '2025-03-29 14:05:00', 'system'),
    ('Total Goals Over 5', 'OPEN', FALSE, 5, '2025-03-29 14:06:00', 'system');

INSERT INTO outcomes (description, settled, price, result, market_id, created_date, created_by)
VALUES
    ('Manchester United Win', FALSE, 2.10, NULL, 1, '2025-03-29 10:07:00', 'system'),
    ('Liverpool Win', FALSE, 3.00, NULL, 1, '2025-03-29 10:08:00', 'system'),
    ('Over 2.5 Goals', FALSE, 1.85, NULL, 2, '2025-03-29 10:09:00', 'system'),
    ('Under 2.5 Goals', FALSE, 1.95, NULL, 2, '2025-03-29 10:10:00', 'system'),
    ('Lakers Win', FALSE, 1.75, NULL, 3, '2025-03-29 11:07:00', 'system'),
    ('Celtics Win', FALSE, 2.05, NULL, 3, '2025-03-29 11:08:00', 'system'),
    ('Over 200 Points', FALSE, 1.90, NULL, 4, '2025-03-29 11:09:00', 'system'),
    ('Under 200 Points', FALSE, 2.00, NULL, 4, '2025-03-29 11:10:00', 'system'),
    ('Djokovic Win', FALSE, 1.65, NULL, 5, '2025-03-29 12:07:00', 'system'),
    ('Nadal Win', FALSE, 2.20, NULL, 5, '2025-03-29 12:08:00', 'system'),
    ('Over 20 Games', FALSE, 1.80, NULL, 6, '2025-03-29 12:09:00', 'system'),
    ('Under 20 Games', FALSE, 1.90, NULL, 6, '2025-03-29 12:10:00', 'system'),
    ('Barcelona Win', FALSE, 2.50, NULL, 7, '2025-03-29 13:07:00', 'system'),
    ('Real Madrid Win', FALSE, 2.80, NULL, 7, '2025-03-29 13:08:00', 'system'),
    ('Yes', FALSE, 1.70, NULL, 8, '2025-03-29 13:09:00', 'system'),
    ('No', FALSE, 2.10, NULL, 8, '2025-03-29 13:10:00', 'system'),
    ('Maple Leafs Win', FALSE, 1.95, NULL, 9, '2025-03-29 14:07:00', 'system'),
    ('Penguins Win', FALSE, 1.85, NULL, 9, '2025-03-29 14:08:00', 'system'),
    ('Over 5 Goals', FALSE, 2.00, NULL, 10, '2025-03-29 14:09:00', 'system'),
    ('Under 5 Goals', FALSE, 1.80, NULL, 10, '2025-03-29 14:10:00', 'system');