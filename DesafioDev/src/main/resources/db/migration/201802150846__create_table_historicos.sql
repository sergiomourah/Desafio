create table historico_contrato (
 	id                              bigint                        NOT NULL PRIMARY KEY,
 	user_id                         bigint                        NOT NULL,
 	contrato_id                     bigint                        NOT NULL,
 	data                            date                          NOT NULL,
 	acao                            integer                       NOT NULL,
 	observacao                      text                          NULL,
 	CONSTRAINT fk_id_user FOREIGN KEY (user_id) REFERENCES "user"(id),
 	CONSTRAINT fk_id_contrato FOREIGN KEY (contrato_id) REFERENCES contrato(id)
);

create table historico_ordem_de_servico (
 	id                              bigint                        NOT NULL PRIMARY KEY,
 	user_id                         bigint                        NOT NULL,
 	ordemdeservico_id               bigint                        NOT NULL,
 	data                            date                          NOT NULL,
 	acao                            integer                       NOT NULL,
 	observacao                      text                          NULL,
 	CONSTRAINT fk_id_user FOREIGN KEY (user_id) REFERENCES "user"(id),
 	CONSTRAINT fk_id_ordemdeservico FOREIGN KEY (ordemdeservico_id) REFERENCES "user"(id)
);