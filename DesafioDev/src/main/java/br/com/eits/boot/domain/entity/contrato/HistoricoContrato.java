package br.com.eits.boot.domain.entity.contrato;

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
import lombok.Data;

@Data
@Entity
@Table
@DataTransferObject
public class HistoricoContrato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public StatusContrato getStatus() {
		return status;
	}

	public void setStatus(StatusContrato status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
}
