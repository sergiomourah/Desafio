SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE cliente CASCADE;

insert into cliente (id, nome) values (1, 'Sergio Moura');

insert into cliente (id, nome) values (2, 'Tiago Lopes')

