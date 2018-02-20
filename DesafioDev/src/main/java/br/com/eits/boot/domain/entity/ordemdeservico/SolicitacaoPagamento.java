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

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class SolicitacaoPagamento extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7072496275412625721L;

	@Column(nullable = false)
	private LocalDate dataVencimento;
	
	@NotNull
	@Column(nullable = false, columnDefinition = "numeric(9,2)")
	private Float valorPagamento;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="ordemdeservico_id", nullable = false)
	private OrdemDeServico ordemdeservico;
}
