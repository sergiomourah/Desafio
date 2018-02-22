package br.com.eits.boot.domain.repository.ordemdeservico;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.contrato.Cliente;
import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;
import br.com.eits.boot.domain.entity.ordemdeservico.Gestor;
import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.Prioridade;
import br.com.eits.boot.domain.entity.ordemdeservico.StatusOrdemDeServico;

/**
*
*/
public interface IOrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long>
{		
	@Query(value="FROM OrdemDeServico ordem " +
			   "where (FILTER(ordem.contrato.numeroContrato, :numeroContrato) = TRUE or cast(:numeroContrato as text) is null) " +
			   "and (FILTER(ordem.numeroOrdemDeServico, :numeroOrdem) = TRUE or cast(:numeroOrdem as text) is null) " +
			   "and (ordem.contrato.cliente.nome like :nomeCliente or :nomeCliente is null) " +
			   "and (ordem.status = :statusOrdem or :statusOrdem is null) " +
			   "and (ordem.valorOrdemDeServico >= :valorOrdemInicial or :valorOrdemInicial is null) " +
			   "and (ordem.valorOrdemDeServico <= :valorOrdemFinal or :valorOrdemFinal is null) " +			   
			   "and (ordem.dataAbertura >= :dataAberturaInicial or cast(:dataAberturaInicial as date) is null) " +
			   "and (ordem.dataAbertura <= :dataAberturaFinal or cast(:dataAberturaFinal as date) is null) " +
			   "and (ordem.prioridade = :prioridadeOrdem or :prioridadeOrdem is null) " +
			   "and (exists(from HistoricoOrdemDeServico historico " +
			   "           where historico.ordemdeservico = ordem " +
			   "           and historico.status = 4 " +//Filtrar somente historico com status 4 - CONCLUIDA
			   "           and (historico.data >= :dataConclusaoInicial) " +
			   "           and (historico.data <= :dataConclusaoFinal )) " +
			   "           or cast(:dataConclusaoInicial as date) is null or cast(:dataConclusaoFinal as date) is null) ")
		
	
		   
	public Page<OrdemDeServico> listByFilters(@Param("numeroContrato") String numeroContrato,
			                            @Param("numeroOrdem") String numeroOrdem,
				                         @Param("nomeCliente") String nomeCliente,
										 @Param("statusOrdem") StatusOrdemDeServico statusOrdem,
										 @Param("valorOrdemInicial") Float valorOrdemInicial,
										 @Param("valorOrdemFinal") Float valorOrdemFinal,
										 @Param("dataAberturaInicial") LocalDate dataAberturaInicial,
										 @Param("dataAberturaFinal") LocalDate dataAberturaFinal,
										 @Param("dataConclusaoInicial") LocalDate dataConclusaoInicial,
										 @Param("dataConclusaoFinal") LocalDate dataConclusaoFinal,
										 @Param("prioridadeOrdem") Prioridade prioridadeOrdem,
			                             Pageable pageable);
	
	
	
	
	@Query("from Gestor gestor where gestor.nome like :nome or :nome is null")
	public Page<Gestor> listGestorByNome( @Param("nome") String nome, Pageable pageable);
}


