package br.com.eits.boot.test.domain.service.contrato;

import static org.junit.Assert.assertNull;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		contrato.setDataContrato(LocalDate.now());
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
		contrato.setDataContrato(LocalDate.now());
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
		contrato.setDataContrato(LocalDate.now());
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
		contrato.setDataContrato(LocalDate.now());
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
		contrato.setDataContrato(LocalDate.now());
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
		final String motivo = "Contrato finalizado";
		contrato = this.contratoService.updateContratoToSuspender(contrato, motivo);
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
	 * Buscar cliente por nome
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void listClienteByNomeMustPass()
	{
		PageRequest pageable = new PageRequest(1, 1);
		final Page<Cliente> clientes = this.contratoService.listClienteByNome("Sergio", pageable);
		
		Assert.assertNotNull( clientes );
		Assert.assertTrue(clientes.getContent().size() > 0); 
	}
	
	/**
	 * Buscar cliente por nome inexistente
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void listClienteByNomeMustFail()
	{
		PageRequest pageable = new PageRequest(0, 0);
		final Page<Cliente> clientes = this.contratoService.listClienteByNome("xxxxxxx", pageable);
		
		Assert.assertNull( clientes );	
	}
	
	/**
	 * Buscar contrato por filtros
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void listContratoByFiltersMustPass()
	{
		String [] datas = {"18/02/2018", "22/02/2018", "17/02/2018", "27/02/2018"};
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataAberturaInicial = LocalDate.parse(datas[0],formatter);
		LocalDate dataAberturaFinal = LocalDate.parse(datas[1],formatter);
		LocalDate dataEncerramentoInicial = LocalDate.parse(datas[2],formatter);
		LocalDate dataEncerramentoFinal = LocalDate.parse(datas[3],formatter);
		PageRequest pageable = new PageRequest(1, 1);
		final Page<Contrato> contratos = 
				this.contratoService.listContratoByFilters("59233", 
						                                   "Sergio", 
						                                   StatusContrato.ENCERRADO, 
						                                   null, 
						                                   null, 
						                                   null, 
						                                   null,
						                                   pageable);

		
		Assert.assertEquals(1, contratos.getContent().size());
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
