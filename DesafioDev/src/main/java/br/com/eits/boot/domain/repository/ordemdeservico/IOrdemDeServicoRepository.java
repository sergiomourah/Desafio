package br.com.eits.boot.domain.repository.ordemdeservico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.contrato.Cliente;
import br.com.eits.boot.domain.entity.ordemdeservico.Gestor;
import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;

/**
*
*/
public interface IOrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long>
{
	@Query("from Gestor gestor where gestor.nome like :nome ")
	public Page<Gestor> listGestorByNome( @Param("nome") String nome, Pageable pageable);
}


