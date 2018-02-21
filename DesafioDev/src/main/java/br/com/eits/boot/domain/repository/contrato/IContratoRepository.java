package br.com.eits.boot.domain.repository.contrato;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.contrato.Cliente;
import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;

public interface IContratoRepository extends JpaRepository<Contrato, Long>
{	
	@Query(value="FROM Contrato contrato " +
           "inner join contrato.cliente cliente " +
		   "where (cliente.nome like :nomeCliente) " +
		   "or (:numeroContrato like contrato.numeroContrato) " +
		   "or (:statusContrato = contrato.status) " +
		   "or (:dataAberturaInicial >= contrato.dataContrato) " +
		   "or (:dataAberturaFinal <= contrato.dataContrato) " +
		   "or (exists(from HistoricoContrato historico " +
		   "           where historico.contrato = contrato " +
		   "           and historico.status = 2 " +
		   "           and (:dataEncerramentoInicial >= historico.data) " +
		   "           and (:dataEncerramentoFinal <= historico.data))) ")
		
	
		   
	public Page<Contrato> listByFilters(@Param("numeroContrato") String numeroContrato,
									 @Param("nomeCliente") String nomeCliente,
									 @Param("statusContrato") StatusContrato statusContrato,
									 @Param("dataAberturaInicial") LocalDate dataAberturaInicial,
									 @Param("dataAberturaFinal") LocalDate dataAberturaFinal,
									 @Param("dataEncerramentoInicial") LocalDate dataEncerramentoInicial,
									 @Param("dataEncerramentoFinal") LocalDate dataEncerramentoFinal,
			                         Pageable pageable);
	
}
