package br.com.eits.boot.domain.repository.ordemdeservico;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.ordemdeservico.HistoricoOrdemDeServico;


public interface IHistoricoOrdemDeServicoRepository extends JpaRepository<HistoricoOrdemDeServico, Long>{

}
