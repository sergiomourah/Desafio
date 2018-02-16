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
	@Size(min = 8, max = 30)
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(LocalDate dataContrato) {
		this.dataContrato = dataContrato;
	}

	public LocalDate getDataPrevisaoEncerramento() {
		return dataPrevisaoEncerramento;
	}

	public void setDataPrevisaoEncerramento(LocalDate dataPrevisaoEncerramento) {
		this.dataPrevisaoEncerramento = dataPrevisaoEncerramento;
	}

	public StatusContrato getStatus() {
		return status;
	}

	public void setStatus(StatusContrato status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<OrdemDeServico> getOrdensDeServico() {
		return ordensDeServico;
	}

	public void setOrdensDeServico(List<OrdemDeServico> ordensDeServico) {
		this.ordensDeServico = ordensDeServico;
	}

	public List<HistoricoContrato> getHistoricosContrato() {
		return historicosContrato;
	}

	public void setHistoricosContrato(List<HistoricoContrato> historicosContrato) {
		this.historicosContrato = historicosContrato;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}		
}
