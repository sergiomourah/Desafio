package br.com.eits.boot.domain.repository.contrato;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.account.User;
import br.com.eits.boot.domain.entity.contrato.Contrato;

public interface IContratoRepository extends JpaRepository<Contrato, Long>
{
	/*@Query("FROM User user " +
			"WHERE filter(:filter, user.id, user.name, user.email) = TRUE ")
	public Page<Contrato> listByFilters( @Param("numeroContrato") String numeroContrato,
									 @Param("nomeCliente") String nomeCliente,
									 @Param("statusContrato") Integer statusContrato,
									 @Param("dataAbertura") LocalDate dataAbertura,
									 @Param("dataEncerramento") LocalDate dataEncerramento,
			                         Pageable pageable );*/
}
