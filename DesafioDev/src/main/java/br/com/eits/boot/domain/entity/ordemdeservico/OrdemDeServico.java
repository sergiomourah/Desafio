/*package br.com.eits.boot.domain.entity.ordemdeservico;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Audited
@Table
@EqualsAndHashCode(callSuper=true)
@DataTransferObject
public class OrdemDeServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable = false, length = 10,  unique = true)
	private String numeroOrdemDeServico;
	
	@NotEmpty
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate dataAbertura;
	
	@Temporal(TemporalType.DATE)
	private LocalDate dataPrevisaoConclusao;
	
	@NotEmpty
	@Column(nullable = false, length = 144)
	private String descricaoProblema;
	
	@Column(length = 144)
	private String descricaoSolucao;
	
	@Column(columnDefinition="TEXT")
	private String observacao;
	
	@Column(precision = 9, scale = 2)
	private Float valorOrdemDeServico;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Prioridade prioridade;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusOrdemDeServico status;
	
	private Gestor gestor;
	
	private SolicitacaoPagamento solicitacaoPagamento;
	
	
}*/
