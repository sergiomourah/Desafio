package br.com.eits.boot.domain.repository.contrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.contrato.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("from Cliente cliente where cliente.nome like :nome or :nome is null ")
	public Page<Cliente> listClienteByNome( @Param("nome") String nome, Pageable pageable);
}
