SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE contrato CASCADE;

INSERT INTO contrato (id, numero_contrato, cliente_id, descricao, data_contrato, data_previsao_encerramento, status)
values (10001, '59230', 1, 'CONTRATO PARA TESTE', NOW(), NOW(), 0);

INSERT INTO contrato (id, numero_contrato, cliente_id, descricao, data_contrato, data_previsao_encerramento, status)
values (10002, '59231', 1, 'CONTRATO PARA TESTE1', NOW(), NOW(), 0);

INSERT INTO contrato (id, numero_contrato, cliente_id, descricao, data_contrato, data_previsao_encerramento, status)
values (10003, '59232', 1, 'CONTRATO PARA TESTE2', NOW(), NOW(), 1);