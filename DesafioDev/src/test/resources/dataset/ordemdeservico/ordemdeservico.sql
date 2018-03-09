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
valor_ordem_de_servico, status) values (5001, 10002, 1, '5684', 0, '20180222', 'ar condicionado quebrado', 500, 0);

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5002, 10003, 1, '5685', 1, '20180222', 'ventilador quebrado', 500, 1);

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5003, 10003, 1, '5686', 2, '20180222', 'Carro quebrado', 500, 3);

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5004, 10002, 1, '5687', 0, '20180221', 'Pneu furado', 500, 2);

insert into ordem_de_servico (id, contrato_id, gestor_id, numero_ordem_de_servico, prioridade, data_abertura, descricao_problema,
valor_ordem_de_servico, status) values (5005, 10002, 1, '5688', 0, '20180221', 'Pneu furado', 500, 4);

insert into historico_ordem_de_servico (id, user_id, ordemdeservico_id, data, acao) values (499, 9999, 5005, '20180221', 0);

insert into historico_ordem_de_servico (id, user_id, ordemdeservico_id, data, acao) values (500, 9999, 5005, '20180225', 4);

insert into solicitacao_pagamento (id, ordemdeservico_id, data_vencimento, valor_pagamento) values (900, 5003, '20180323', 1500);

