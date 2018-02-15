CREATE TABLE cliente (
  id                              bigint                        NOT NULL PRIMARY KEY,
  nome                            varchar(144)                  NOT NULL
);

CREATE TABLE contrato (
  id                              bigint                   NOT NULL PRIMARY KEY,
  numero_contrato                 varchar                  NOT NULL,
  cliente_id                      bigint                   NOT NULL
);