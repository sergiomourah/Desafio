package br.com.eits.boot.test.domain.service.contrato;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.com.eits.boot.domain.entity.contrato.Cliente;
import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;
import br.com.eits.boot.domain.service.contrato.ContratoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;


public class ContratoIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private ContratoService contratoService;
	/**
	 * Testa o inserir contrato no banco de dados, com todos os campos obrigatórios preenchidos.
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertContratoMustPass()
	{
		Contrato contrato = new Contrato();
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato.setDataContrato(LocalDateTime.now());
		contrato.setStatus(StatusContrato.ABERTO);
		contrato.setCliente(new Cliente(1L));
		contrato = this.contratoService.insertContrato(contrato);
		Assert.assertNotNull(contrato);
		Assert.assertNotNull(contrato.getId());
		Assert.assertNotNull(contrato.getNumeroContrato());
		Assert.assertNotNull(contrato.getDataContrato());
		Assert.assertNotNull(contrato.getStatus());
		Assert.assertNotNull(contrato.getCliente());
	}
	/**
	 * Testa o inserir contrato no banco de dados no banco de dados, sem informar o status
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertContratoMustFailStatusNull()
	{
		Contrato contrato = new Contrato();
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato.setDataContrato(LocalDateTime.now());
		contrato.setCliente(new Cliente(1L));
		this.contratoService.insertContrato(contrato);
		
		Assert.fail("O sistema deveria infomar ao usuário que o status não pode ser nulo.");

	}
	/**
	 * Testa o inserir contrato no banco de dados, sem informar a data do contrato
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertContratoMustFailDataContratoNull()
	{
		Contrato contrato = new Contrato();
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato.setStatus(StatusContrato.ABERTO);
		contrato.setCliente(new Cliente(1L));
		this.contratoService.insertContrato(contrato);
		
		Assert.fail("O sistema deveria infomar ao usuário que a data do contrato não pode ser nula.");

	}
	/**
	 * Testa o inserir contrato no banco de dados, sem informar o número do contrato
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertContratoMustFailNumeroContratoNull()
	{
		Contrato contrato = new Contrato();
		contrato.setDescricao("Contrato Teste");
		contrato.setStatus(StatusContrato.ABERTO);
		contrato.setDataContrato(LocalDateTime.now());
		contrato.setCliente(new Cliente(1L));
		this.contratoService.insertContrato(contrato);
		
		Assert.fail("O sistema deveria infomar ao usuário que a data do contrato não pode ser nula.");
	}
	/**
	 * Testa o inserir contrato no banco de dados, sem informar o cliente
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertContratoMustFailClienteNull()
	{
		Contrato contrato = new Contrato();
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato.setStatus(StatusContrato.ABERTO);
		contrato.setDataContrato(LocalDateTime.now());
		this.contratoService.insertContrato(contrato);
		
		Assert.fail("O sistema deveria infomar ao usuário que a data do contrato não pode ser nula.");

	}
	/**
	 * Testa o update no contrato no banco de dados, com todos os campos obrigatórios preenchidos.
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void updateContratoMustPass()
	{
		Contrato contrato = new Contrato();
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato.setDataContrato(LocalDateTime.now());
		contrato.setStatus(StatusContrato.ABERTO);
		contrato.setCliente(new Cliente(1L));
		contrato = this.contratoService.updateContrato(contrato);
		Assert.assertNotNull(contrato);
		Assert.assertNotNull(contrato.getId());
		Assert.assertNotNull(contrato.getNumeroContrato());
		Assert.assertNotNull(contrato.getDataContrato());
		Assert.assertNotNull(contrato.getStatus());
		Assert.assertNotNull(contrato.getCliente());
	}
	/**
	 * Encerra o contrato
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})	
	public void updateContratoToEncerrarMustPass()
	{
		Contrato contrato = this.contratoService.findContratoById(10001);
		contrato = this.contratoService.updateContratoToEncerrar(contrato);
		Assert.assertEquals(StatusContrato.ENCERRADO, contrato.getStatus());
	}
	
	/**
	 * Suspender o contrato
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void updateContratoToSuspenderMustPass()
	{
		Contrato contrato = this.contratoService.findContratoById(10002);
		contrato = this.contratoService.updateContratoToSuspender(contrato);
		Assert.assertEquals(StatusContrato.SUSPENSO, contrato.getStatus());
	}
	
	/**
	 * Reabrir o contrato
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void updateContratoToReabrirMustPass()
	{
		Contrato contrato = this.contratoService.findContratoById(10003);
		contrato = this.contratoService.updateContratoToReabrir(contrato);
		Assert.assertEquals(StatusContrato.ABERTO, contrato.getStatus());
	}
	/**
	 * Buscar contrato pelo id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void findContratoByIdMustPass()
	{
		final Contrato contrato = this.contratoService.findContratoById(10001);
		
		Assert.assertNotNull( contrato );
		Assert.assertNotNull( contrato.getId() );
		Assert.assertNotNull( contrato.getNumeroContrato());
		Assert.assertEquals( "59230", contrato.getNumeroContrato() );	
	}
	
	/**
	 * Buscar contrato com um id inexistente
	 */
	@Test(expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void findContratoByIdMustFail()
	{
		final Contrato contrato = this.contratoService.findContratoById(1000000);
		
		Assert.assertNull( contrato );
		Assert.assertEquals( "59230", contrato.getNumeroContrato() );
		
		Assert.fail("Nenhum registro foi encontrado.");
	}
	
	
	/**
	 * Remove contrato pelo id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void removeContratoMustPass()
	{
		this.contratoService.removeContrato(10001);					
	}
	/**
	 * Tenta remover contrato com um id inexistente
	 */
	@Test(expected = EmptyResultDataAccessException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void removeContratoMustFail()
	{
		this.contratoService.removeContrato(1000001);
		Contrato contrato = this.contratoService.findContratoById(10001);
		assertNull(contrato);
		Assert.fail("Nenhum registro foi encontrado.");
	}
}
