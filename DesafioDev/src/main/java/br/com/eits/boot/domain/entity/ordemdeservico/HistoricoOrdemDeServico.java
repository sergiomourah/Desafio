package br.com.eits.boot.domain.entity.ordemdeservico;

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
import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class HistoricoOrdemDeServico extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6006653923782412401L;
	
	private LocalDate data;
	
	private String observacao;
	
	@NotNull
	@Column(nullable = false, name = "acao")
	@Enumerated(EnumType.ORDINAL)
	private StatusOrdemDeServico status;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="ordemdeservico_id", nullable = false)
	private OrdemDeServico ordemdeservico;
	
	public HistoricoOrdemDeServico()
	{
		
	}
	
	public HistoricoOrdemDeServico(LocalDate data, 
            String observacao, 
            StatusOrdemDeServico status, 
            OrdemDeServico ordemdeservico)
	{
		this.data = data;
		this.observacao = observacao;
		this.status = status;
		this.ordemdeservico = ordemdeservico;
	}
	
}
