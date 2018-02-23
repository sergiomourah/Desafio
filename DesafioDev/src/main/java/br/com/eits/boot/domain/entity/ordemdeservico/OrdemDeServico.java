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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class OrdemDeServico extends AbstractEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3557400488032567864L;
	
	@ManyToOne
	@JoinColumn(name="contrato_id")
	private Contrato contrato;
	
	@NotEmpty
	@Column(nullable = false, length = 10,  unique = true)
	private String numeroOrdemDeServico;
	
	@NotNull
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

	
	/**
     * Verifica se status da ordem de serviço está como 0 "ABERTO"
     */
	public boolean validarAlterarOrdemDeServico(StatusOrdemDeServico status) {
		if (status == StatusOrdemDeServico.ABERTA)
			return true;
		else return false;
	}
	
	/**
     * Verifica se status da ordem de serviço está como 0 "ABERTA"
     */
	public boolean validarAprovarOrdemDeServico(StatusOrdemDeServico status) {
		if (status == StatusOrdemDeServico.ABERTA)
			return true;
		else return false;
	}
	
	/**
     * Verifica se status da ordem de serviço está como 1 "APROVADA"
     */
	public boolean validarHomologacaoOrdemDeServico(StatusOrdemDeServico status) {
		if (status == StatusOrdemDeServico.APROVADA)
			return true;
		else return false;
	}
	
	/**
     * Verifica se status da ordem de serviço está como 3 "HOMOLOGADA"
     */
	public boolean validarSolicitacaoDePagamento(StatusOrdemDeServico status) {
		if (status == StatusOrdemDeServico.HOMOLOGADA)
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
	
	/**
     * Verifica se status da ordem de serviço não está como 4 "CONCLUÍDA".
     */
	public boolean validarCancelarOrdemDeServico(StatusOrdemDeServico status) {
		if (status != StatusOrdemDeServico.CONCLUIDA)
			return true;
		else return false;
	}
}
