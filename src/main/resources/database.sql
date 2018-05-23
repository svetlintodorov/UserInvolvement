-- Database: mydb

-- DROP DATABASE mydb;

CREATE DATABASE mydb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- Table: public.roles

-- DROP TABLE public.roles;

CREATE TABLE public.roles
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.roles
    OWNER to postgres;
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    user_id bigint NOT NULL,
    password character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

-- Table: public.users_roles

-- DROP TABLE public.users_roles;

CREATE TABLE public.users_roles
(
    user_user_id bigint NOT NULL,
    roles_id bigint NOT NULL,
    CONSTRAINT uk_60loxav507l5mreo05v0im1lq UNIQUE (roles_id),
    CONSTRAINT fk27iuqlfirca39l6y61p4p4qo2 FOREIGN KEY (user_user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fka62j07k5mhgifpp955h37ponj FOREIGN KEY (roles_id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users_roles
    OWNER to postgres;
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------


-- Table: public.announcements

-- DROP TABLE public.announcements;

CREATE TABLE public.announcements
(
    announcement_id bigint NOT NULL,
    content character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    author_id bigint,
    CONSTRAINT announcements_pkey PRIMARY KEY (announcement_id),
    CONSTRAINT fk1pgl63iwbqvmngumhvr3xopg3 FOREIGN KEY (author_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.announcements
    OWNER to postgres;
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- Table: public.announcement_users

-- DROP TABLE public.announcement_users;

CREATE TABLE public.announcement_users
(
    announcement_user_id bigint NOT NULL,
    feedback integer,
    announcement_id bigint,
    user_id bigint,
    CONSTRAINT announcement_users_pkey PRIMARY KEY (announcement_user_id),
    CONSTRAINT fkbpicamk5a3kmdboqm1rsmo80d FOREIGN KEY (announcement_id)
        REFERENCES public.announcements (announcement_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkeno9fdav8l2tehb3yjdejcdp9 FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.announcement_users
    OWNER to postgres;