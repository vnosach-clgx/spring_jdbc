DROP TABLE IF EXISTS social_like;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id bigint auto_increment primary key,
    name varchar(255),
    surname varchar(255),
    dob date
);

CREATE TABLE friendship (
    user_1 bigint not null,
    user_2 bigint not null,
    timestamp timestamp not null,
    FOREIGN KEY (user_1) REFERENCES user(id),
    FOREIGN KEY (user_2) REFERENCES user(id)
);

CREATE TABLE post (
    id bigint auto_increment primary key,
    user bigint,
    text text,
    timestamp timestamp,
    FOREIGN KEY (user) REFERENCES user(id)
);

CREATE TABLE social_like (
    post bigint,
    user bigint,
    timestamp timestamp,
    FOREIGN KEY (user) REFERENCES user(id),
    FOREIGN KEY (post) REFERENCES post(id)

);



-- DROP TABLE IF EXISTS social_like;
-- DROP TABLE IF EXISTS friendship;
-- DROP TABLE IF EXISTS post;
-- DROP TABLE IF EXISTS public.user;
--
-- DROP SEQUENCE IF EXISTS t1;
-- DROP SEQUENCE IF EXISTS t2;
-- create sequence t1;
-- create sequence t2;
--
-- CREATE TABLE public.user
-- (
--     id      bigint primary key DEFAULT nextval('t1'),
--     name    varchar(255),
--     surname varchar(255),
--     dob     date
-- );
--
-- CREATE TABLE friendship
-- (
--     user_1    bigint    not null,
--     user_2    bigint    not null,
--     timestamp timestamp not null,
--     FOREIGN KEY (user_1) REFERENCES public.user (id),
--     FOREIGN KEY (user_2) REFERENCES public.user (id)
-- );
--
-- CREATE TABLE post
-- (
--     id        bigint primary key default nextval('t2'),
--     "user"    bigint,
--     text      text,
--     timestamp timestamp,
--     FOREIGN KEY ("user") REFERENCES public.user (id)
-- );
--
-- CREATE TABLE social_like
-- (
--     post      bigint,
--     "user"    bigint,
--     timestamp timestamp,
--     FOREIGN KEY ("user") REFERENCES public.user (id),
--     FOREIGN KEY (post) REFERENCES post (id)
--
-- );
--
--
