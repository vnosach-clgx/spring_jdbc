INSERT INTO user(name, surname, dob) VALUES ('John', 'One', '2020-11-11');
INSERT INTO user(name, surname, dob) VALUES ('Frank', 'One', '2020-11-11');

INSERT INTO post(user, text, timestamp) VALUES (1,'q','2020-11-11 11:11:11');
INSERT INTO post(user, text, timestamp) VALUES (1,'w','2020-11-11 11:11:11');
INSERT INTO post(user, text, timestamp) VALUES (2,'e','2020-11-11 11:11:11');

INSERT INTO friendship(user_1, user_2, timestamp) VALUES (1,2,'2020-11-11 11:11:11');
INSERT INTO friendship(user_1, user_2, timestamp) VALUES (2,1,'2020-11-11 11:11:11');

INSERT INTO social_like(post, user, timestamp) VALUES (1,1,'2025-03-11 11:11:11');
INSERT INTO social_like(post, user, timestamp) VALUES (2,1,'2025-03-11 11:11:11');
INSERT INTO social_like(post, user, timestamp) VALUES (3,2,'2025-03-11 11:11:11');
INSERT INTO social_like(post, user, timestamp) VALUES (3,2,'2024-11-11 11:11:11');
