CREATE TABLE cliente (
  id                              bigserial                        NOT NULL PRIMARY KEY,
  nome                            varchar(144)                  NOT NULL
);

CREATE TABLE contrato (
  id                              bigserial                   NOT NULL PRIMARY KEY,
  numero_contrato                 varchar                  NOT NULL,
  cliente_id                      bigserial                   NOT NULL
);