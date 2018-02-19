package br.com.eits.boot.domain.entity.ordemdeservico;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.HistoricoContrato;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class OrdemDeServico implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3557400488032567864L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="contrato_id")
	private Contrato contrato;
	
	@NotEmpty
	@Column(nullable = false, length = 10,  unique = true)
	private String numeroOrdemDeServico;
	
	@NotEmpty
	@Column(nullable = false)
	private LocalDate dataAbertura;
	
	private LocalDate dataPrevisaoConclusao;
	
	@NotEmpty
	@Column(nullable = false, length = 144)
	private String descricaoProblema;
	@Column(length = 144)
	private String descricaoSolucao;
	
	@Column(columnDefinition="TEXT")
	private String observacao;
	
	@Column(columnDefinition = "numeric(9,2)")
	private Float valorOrdemDeServico;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Prioridade prioridade;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusOrdemDeServico status;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="gestor_id", nullable = false)
	private Gestor gestor;
	
	@OneToMany(mappedBy = "ordemdeservico", targetEntity = SolicitacaoPagamento.class, 
		    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
			private List<SolicitacaoPagamento> solicitacoesPagamento;
	
	@OneToMany(mappedBy = "ordemdeservico", targetEntity = HistoricoOrdemDeServico.class, 
		    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
			private List<HistoricoOrdemDeServico> historicosOrdemDeServico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getNumeroOrdemDeServico() {
		return numeroOrdemDeServico;
	}

	public void setNumeroOrdemDeServico(String numeroOrdemDeServico) {
		this.numeroOrdemDeServico = numeroOrdemDeServico;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataPrevisaoConclusao() {
		return dataPrevisaoConclusao;
	}

	public void setDataPrevisaoConclusao(LocalDate dataPrevisaoConclusao) {
		this.dataPrevisaoConclusao = dataPrevisaoConclusao;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;// 0
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public String getDescricaoSolucao() {
		return descricaoSolucao;
	}

	public void setDescricaoSolucao(String descricaoSolucao) {
		this.descricaoSolucao = descricaoSolucao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Float getValorOrdemDeServico() {
		return valorOrdemDeServico;
	}

	public void setValorOrdemDeServico(Float valorOrdemDeServico) {
		this.valorOrdemDeServico = valorOrdemDeServico;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public StatusOrdemDeServico getStatus() {
		return status;
	}

	public void setStatus(StatusOrdemDeServico status) {
		this.status = status;
	}
	
	public Gestor getGestor() {
		return gestor;
	}

	public void setGestor(Gestor gestor) {
		this.gestor = gestor;
	}

	public List<SolicitacaoPagamento> getSolicitacoesPagamento(){
		return solicitacoesPagamento;
	}

	public void setSolicitacoesPagamento(List<SolicitacaoPagamento> solicitacoesPagamento) {
		this.solicitacoesPagamento = solicitacoesPagamento;
	}

	public List<HistoricoOrdemDeServico> getHistoricosOrdemDeServico() {
		return historicosOrdemDeServico;
	}

	public void setHistoricosOrdemDeServico(List<HistoricoOrdemDeServico> historicosOrdemDeServico) {
		this.historicosOrdemDeServico = historicosOrdemDeServico;
	}
	
	/**
     * Verifica se status da ordem de serviço está como 0 "ABERTO"
     */
	public boolean validarAlterarOrdemDeServico(int status) {
		if (status == 0)
			return true;
		else return false;
	}
	
	/**
     * Verifica se status da ordem de serviço está como 1 "APROVADA"
     */
	public boolean validarHomologacaoOrdemDeServico(int status) {
		if (status == 1)
			return true;
		else return false;
	}
	
	/**
     * Verifica se status da ordem de serviço está como 3 "HOMOLOGADA"
     */
	public boolean validarSolicitacaoDePagamento(int status) {
		if (status == 3)
			return true;
		else return false;
	}
	/**
     * Verifica se já existe solicitação de pagamento emitida.
     */
	public boolean validarConclusaoOrdemDeServico(List<SolicitacaoPagamento> listSolicitacao) {
		if (listSolicitacao.size() > 0 )
			return true;
		else return false;
	}
}
