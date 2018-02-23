package br.com.eits.boot.domain.repository.ordemdeservico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.ordemdeservico.HistoricoOrdemDeServico;


public interface IHistoricoOrdemDeServicoRepository extends JpaRepository<HistoricoOrdemDeServico, Long>{

	@Query("from HistoricoOrdemDeServico historico where historico.ordemdeservico.id = :id or :id is null ")
	public Page<HistoricoOrdemDeServico> listHistoricoOrdemDeServicoByOrdemDeServicoId( @Param("id") Long id, Pageable pageable);
}
