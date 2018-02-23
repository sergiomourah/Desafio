package br.com.eits.boot.domain.repository.contrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.contrato.HistoricoContrato;

public interface IHIstoricoContratoRepository extends JpaRepository<HistoricoContrato, Long>{

	@Query("from HistoricoContrato historico where historico.contrato.id = :id or :id is null ")
	public Page<HistoricoContrato> listHistoricoContratoByContratoId( @Param("id") Long id, Pageable pageable);
}
