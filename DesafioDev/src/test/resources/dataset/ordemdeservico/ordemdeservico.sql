SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE ordem_de_servico CASCADE;

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5001, 10002, 1, '5684', 0, now(), 'ar condicionado quebrado', 500, 0);

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5002, 10003, 1, '5685', 0, now(), 'ventilador quebrado', 500, 1);

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5003, 10003, 1, '5686', 0, now(), 'Carro quebrado', 500, 3);