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
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name ="contrato")
public class Contrato implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable = false, length = 10,  unique = true)
	private String numeroContrato;
	
	/*@Column(columnDefinition="TEXT")
	private String descricao;
	
	@NotEmpty
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private LocalDate dataContrato;
	
	@Temporal(TemporalType.DATE)
	private LocalDate dataPrevisaoEncerramento;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusContrato status;
	
	private Cliente cliente;
	
	@JoinTable(name="ordem_servico", joinColumns=@JoinColumn("id"))
	private OrdemDeServico ordemservico;*/

}
