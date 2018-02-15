create table gestor (
	id                        bigserial                NOT NULL PRIMARY KEY,
	nome                      varchar(144)             NULL
);


create table ordem_de_servico (
	id                        bigint                   NOT NULL PRIMARY KEY,
	contrato_id               bigint                   NOT NULL,
	gestor_id                 bigint                   NOT NULL,
  	numero_ordem_de_servico   varchar(10)              NOT NULL UNIQUE,
  	prioridade                integer                  NOT NULL,
  	data_abertura             date                     NULL,
  	data_previsao_conclusao   date                     NULL,
  	descricao_problema        varchar(144)             NOT NULL,
  	descricao_solucao         varchar(144)             NULL,
  	observacao                text                     NULL,
  	valor_ordem_de_servico    numeric(9,2)             NULL,
  	status                    integer                  NULL,
    CONSTRAINT fk_id_contrato FOREIGN KEY (contrato_id) REFERENCES contrato(id),
    CONSTRAINT fk_id_gestor FOREIGN KEY (gestor_id) REFERENCES gestor(id)
);


create table solicitacao_pagamento (
	id                        bigint                   NOT NULL PRIMARY KEY,
	ordemdeservico_id         bigint                   NOT NULL,
	data_vencimento           date                     NOT NULL,
	valor_pagamento           numeric(9,2)             NOT NULL,
	CONSTRAINT fk_id_ordem_de_servico FOREIGN KEY (ordemdeservico_id) REFERENCES ordem_de_servico(id)
);