package br.com.eits.boot.domain.entity.ordemdeservico;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Audited
@Table
@EqualsAndHashCode(callSuper=true)
@DataTransferObject
public class SolicitacaoPagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private LocalDate dataVencimento;
	
	@NotNull
	@Column(nullable = false, precision = 9, scale = 2)
	private Float valorPagamento;
}
