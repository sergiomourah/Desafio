alter table historico_contrato
add column created timestamp,
add column updated timestamp;

alter table historico_ordem_de_servico
add column created timestamp,
add column updated timestamp;