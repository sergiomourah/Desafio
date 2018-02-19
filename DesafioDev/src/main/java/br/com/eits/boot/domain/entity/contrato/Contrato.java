package br.com.eits.boot.domain.entity.contrato;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
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
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="cliente_id", nullable = false)
	private Cliente cliente;
	
	@OneToMany(mappedBy = "contrato", targetEntity = OrdemDeServico.class, 
		    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
			private List<OrdemDeServico> ordensDeServico;
	
	@OneToMany(mappedBy = "contrato", targetEntity = HistoricoContrato.class, 
		    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
			private List<HistoricoContrato> historicosContrato;
			
}
