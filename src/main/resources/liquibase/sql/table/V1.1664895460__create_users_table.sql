CREATE TABLE public.users_information
(
    id         BIGSERIAL PRIMARY KEY,
    last_name  VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    sex        VARCHAR(10) NOT NULL,
    age_value  INTEGER     NOT NULL
);

CREATE TABLE public.accounts
(
    id            BIGSERIAL PRIMARY KEY,
    user_name     VARCHAR(50) NOT NULL,
    user_pass     VARCHAR     NOT NULL,
    refresh_token VARCHAR,
    user_info_id  BIGINT REFERENCES public.users_information (id)
);

CREATE TABLE public.roles
(
    id        SERIAL      NOT NULL PRIMARY KEY,
    name_role VARCHAR(50) NOT NULL
);

INSERT INTO public.roles (name_role)
VALUES ('USER'),
       ('ADMIN'),
       ('COURIER');

CREATE TABLE user_roles (
    user_id BIGINT REFERENCES public.accounts(id),
    role_id BIGINT REFERENCES public.roles(id)
)