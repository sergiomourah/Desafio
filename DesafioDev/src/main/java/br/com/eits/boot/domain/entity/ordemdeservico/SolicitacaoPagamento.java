package br.com.eits.boot.domain.entity.ordemdeservico;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class SolicitacaoPagamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7072496275412625721L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Id
	@Column(nullable = false)
	private LocalDate dataVencimento;
	
	@NotNull
	@Column(nullable = false, columnDefinition = "numeric(9,2)")
	private Float valorPagamento;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="ordemdeservico_id", nullable = false)
	private OrdemDeServico ordemdeservico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Float getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(Float valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public OrdemDeServico getOrdemdeservico() {
		return ordemdeservico;
	}

	public void setOrdemdeservico(OrdemDeServico ordemdeservico) {
		this.ordemdeservico = ordemdeservico;
	}
}
