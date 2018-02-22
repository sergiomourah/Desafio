package br.com.eits.boot.domain.repository.contrato;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;

public interface IContratoRepository extends JpaRepository<Contrato, Long>
{	
	@Query(value="FROM Contrato contrato " +
			   "where (FILTER(contrato.numeroContrato, :numeroContrato) = TRUE or cast(:numeroContrato as text) is null) " +
			   "and (contrato.cliente.nome like :nomeCliente or :nomeCliente is null) " +
			   "and (contrato.status = :statusContrato or :statusContrato is null) " +
			   "and (contrato.dataContrato >= :dataAberturaInicial or cast(:dataAberturaInicial as date) is null) " +
			   "and (contrato.dataContrato <= :dataAberturaFinal or cast(:dataAberturaFinal as date) is null) " +
			   "and (exists(from HistoricoContrato historico " +
			   "           where historico.contrato = contrato " +
			   "           and historico.status = 2 " +//Filtrar somente historico com status 2 - ENCERRADO
			   "           and (historico.data >= :dataEncerramentoInicial) " +
			   "           and (historico.data <= :dataEncerramentoFinal )) " +
			   "           or cast(:dataEncerramentoInicial as date) is null or cast(:dataEncerramentoFinal as date) is null) ")
		
	
		   
	public Page<Contrato> listByFilters(@Param("numeroContrato") String numeroContrato,
			                         @Param("nomeCliente") String nomeCliente,
									 @Param("statusContrato") StatusContrato statusContrato,
									 @Param("dataAberturaInicial") LocalDate dataAberturaInicial,
									 @Param("dataAberturaFinal") LocalDate dataAberturaFinal,
									 @Param("dataEncerramentoInicial") LocalDate dataEncerramentoInicial,
									 @Param("dataEncerramentoFinal") LocalDate dataEncerramentoFinal,
			                         Pageable pageable);
	
}
