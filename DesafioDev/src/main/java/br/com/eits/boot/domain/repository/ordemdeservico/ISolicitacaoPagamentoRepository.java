package br.com.eits.boot.domain.repository.ordemdeservico;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;

public interface ISolicitacaoPagamentoRepository extends JpaRepository<SolicitacaoPagamento, Long>{

}