package br.com.eits.boot.domain.entity.contrato;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;

import br.com.eits.boot.domain.entity.account.User;
import br.com.eits.boot.domain.entity.ordemdeservico.StatusOrdemDeServico;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class HistoricoContrato extends AbstractEntity implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -6852215807276622785L;

	private LocalDate data;
	
	private String observacao;
	
	@NotNull
	@Column(nullable = false, name = "acao")
	@Enumerated(EnumType.ORDINAL)
	private StatusContrato status;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="contrato_id", nullable = false)
	private Contrato contrato;
	
	public HistoricoContrato()
	{
		
	}
	
	public HistoricoContrato(LocalDate data, 
			                 String observacao, 
			                 StatusContrato status, 
			                 Contrato contrato)
	{
		this.data = data;
		this.observacao = observacao;
		this.status = status;
		this.contrato = contrato;
	}
}
