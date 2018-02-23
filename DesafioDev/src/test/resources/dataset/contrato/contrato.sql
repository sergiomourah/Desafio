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
values (10001, '59230', 1, 'CONTRATO PARA TESTE', '20180222', '20180222', 0);

INSERT INTO contrato (id, numero_contrato, cliente_id, descricao, data_contrato, data_previsao_encerramento, status)
values (10002, '59231', 2, 'CONTRATO PARA TESTE1', '20180222', '20180222', 0);

INSERT INTO contrato (id, numero_contrato, cliente_id, descricao, data_contrato, data_previsao_encerramento, status)
values (10003, '59232', 2, 'CONTRATO PARA TESTE2', '20180222', '20180222', 1);

INSERT INTO contrato (id, numero_contrato, cliente_id, descricao, data_contrato, data_previsao_encerramento, status)
values (10004, '59233', 1, 'CONTRATO PARA TESTE3', '20180222', CURRENT_DATE + INTERVAL '3 day' , 2);

insert into historico_contrato (id, user_id, contrato_id, data, acao) values (4999, 9999, 10004, '20180222', 0);

insert into historico_contrato (id, user_id, contrato_id, data, acao) values (5000, 9999, 10004, '20180225', 2);