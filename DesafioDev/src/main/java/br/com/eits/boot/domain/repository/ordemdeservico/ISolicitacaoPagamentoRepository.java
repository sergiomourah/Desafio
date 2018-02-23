package br.com.eits.boot.domain.repository.ordemdeservico;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;

public interface ISolicitacaoPagamentoRepository extends JpaRepository<SolicitacaoPagamento, Long>{

	@Query(value="from SolicitacaoPagamento solicitacao where solicitacao.ordemdeservico.id = :id or :id is null ")
	public Page<SolicitacaoPagamento> listSolicitacaoPagamentoByOrdemId(@Param("id") Long id,
            Pageable pageable);	
}