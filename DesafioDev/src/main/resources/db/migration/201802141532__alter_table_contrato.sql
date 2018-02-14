alter table contrato 
add column data_contrato date,
add column data_previsao_encerramento date,
add column status integer,
add constraint numero_contrato_UNIQUE UNIQUE(numero_contrato)