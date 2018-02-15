package br.com.eits.boot.domain.entity.ordemdeservico;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum Prioridade {
	
	//ENUMS
	BAIXA,
	MEDIA,
	ALTA;
	
	public static final String BAIXA_VALUE = "BAIXA";
	public static final String MEDIA_VALUE = "MÉDIA";
	public static final String ALTA_VALUE = "ALTA";
}
