CREATE TABLE ranking (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     userID VARCHAR(255) NOT NULL UNIQUE,
     try_count INT NOT NULL
);

INSERT INTO ranking (userID,try_count) VALUES ('userid_01',5);
INSERT INTO ranking (userID,try_count) VALUES ('userid_02',7);
INSERT INTO ranking (userID,try_count) VALUES ('userid_03',3);
INSERT INTO ranking (userID,try_count) VALUES ('userid_04',6);
