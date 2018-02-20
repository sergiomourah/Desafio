alter table ordem_de_servico
add column created timestamp,
add column updated timestamp;

alter table gestor
add column created timestamp,
add column updated timestamp