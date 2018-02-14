package br.com.eits.boot.domain.entity.contrato;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@DataTransferObject
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
	
	@Column(columnDefinition="TEXT")
	private String descricao;
	
	@NotEmpty
	@Column(nullable = false)
	private LocalDate dataContrato;
	
	private LocalDate dataPrevisaoEncerramento;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusContrato status;
	
	private Cliente cliente;
	
	//@OneToMany
	//@JoinColumn()
	private OrdemDeServico ordemservico;

}
