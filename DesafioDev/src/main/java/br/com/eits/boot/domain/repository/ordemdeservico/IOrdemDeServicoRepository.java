package br.com.eits.boot.domain.repository.ordemdeservico;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;

/**
*
*/
public interface IOrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long>
{
	
}

