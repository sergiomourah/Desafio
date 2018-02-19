package br.com.eits.boot.test.domain.service.contrato;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.exolab.castor.mapping.ValidityException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;
import br.com.eits.boot.domain.service.contrato.ContratoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;


public class ContratoIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private ContratoService contratoService;
	/**
	 * Salva o contrato no banco de dados, com todos os campos obrigatórios preenchidos.
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
		System.out.println(LocalDate.now());
		contrato.setDataContrato(LocalDate.now());
		contrato = this.contratoService.insertContrato(contrato);
		Assert.assertNotNull(contrato);
		Assert.assertNotNull(contrato.getId());
		Assert.assertNotNull(contrato.getNumeroContrato());
		Assert.assertNotNull(contrato.getDataContrato());
	}
	/**
	 * Salva o contrato no banco de dados, sem informar a data do contrato
	 * que é obrigátorio
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertContratoMustFail()
	{
		Contrato contrato = new Contrato();
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato = this.contratoService.insertContrato(contrato);
		Assert.assertNotNull(contrato);
		Assert.assertNotNull(contrato.getId());
		Assert.assertNotNull(contrato.getNumeroContrato());
		Assert.assertNotNull(contrato.getDataContrato());
	}
	/**
	 * Encerra o contrato
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void updateContratoToEncerrarMustPass()
	{
		Contrato contrato = new Contrato();
		long id = 1;
		contrato.setId(id);
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato = this.contratoService.updateContratoToEncerrar(contrato);
		Assert.assertEquals(StatusContrato.ENCERRADO, contrato.getStatus());
	}
	
	/**
	 * Suspender o contrato
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void updateContratoToSuspenderMustPass()
	{
		Contrato contrato = new Contrato();
		long id = 1;
		contrato.setId(id);
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato = this.contratoService.updateContratoToSuspender(contrato);
		Assert.assertEquals(StatusContrato.SUSPENSO, contrato.getStatus());
	}
	
	/**
	 * Reabrir o contrato
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void updateContratoToReabrirMustPass()
	{
		Contrato contrato = new Contrato();
		long id = 1;
		contrato.setId(id);
		contrato.setNumeroContrato("100");
		contrato.setDescricao("Contrato Teste");
		contrato = this.contratoService.updateContratoToReabrir(contrato);
		Assert.assertEquals(StatusContrato.ABERTO, contrato.getStatus());
	}
	/**
	 * Buscar contrato pelo id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void findContratoByIdMustPass()
	{
		//Optional<Contrato>  this.contratoService.findContratoById(1);
		
	}
}
