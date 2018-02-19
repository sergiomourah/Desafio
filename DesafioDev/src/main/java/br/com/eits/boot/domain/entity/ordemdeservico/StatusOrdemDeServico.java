package br.com.eits.boot.domain.entity.ordemdeservico;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum StatusOrdemDeServico {

	//ENUMS
	ABERTA, // 0
	APROVADA, // 1
	CANCELADA,// 2
	HOMOLOGADA,// 3
	CONCLUIDA;// 4
	
	public static final String ABERTA_VALUE     = "ABERTA";
	public static final String APROVADA_VALUE   = "APROVADA";
	public static final String CANCELADA_VALUE  = "CANCELADA";
	public static final String HOMOLOGADA_VALUE = "HOMOLOGADA";
	public static final String CONCLUIDA_VALUE  = "CONCLUIDA";
}
