package br.com.eits.boot.test.domain.service.ordemdeservico;

import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import br.com.eits.boot.domain.entity.ordemdeservico.Gestor;
import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.Prioridade;
import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;
import br.com.eits.boot.domain.entity.ordemdeservico.StatusOrdemDeServico;
import br.com.eits.boot.domain.service.contrato.ContratoService;
import br.com.eits.boot.domain.service.ordemdeservico.OrdemDeServicoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class OrdemDeServicoIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private OrdemDeServicoService ordemDeServicoService;
	@Autowired
	private ContratoService contratoService;
	/**
	 * Testa o inserir ordem de servico no banco de dados, com todos os campos obrigatórios preenchidos.
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql"
	})
	public void insertOrdemDeServicoMustPass()
	{
		OrdemDeServico ordemdeservico = new OrdemDeServico();
		Contrato contrato = this.contratoService.findContratoById(10001);
		ordemdeservico.setContrato(contrato);
		ordemdeservico.setNumeroOrdemDeServico("5896");;
		ordemdeservico.setDataAbertura(LocalDate.now());
		ordemdeservico.setPrioridade(Prioridade.BAIXA);
		ordemdeservico.setDescricaoProblema("Computador Quebrado");
		ordemdeservico.setGestor(new Gestor(1L));
		ordemdeservico.setValorOrdemDeServico(20F);
		ordemdeservico.setStatus(StatusOrdemDeServico.ABERTA);
		ordemdeservico = this.ordemDeServicoService.insertOrdemDeServico(ordemdeservico);
		Assert.assertNotNull(ordemdeservico);
		Assert.assertNotNull(ordemdeservico.getId());
		Assert.assertNotNull(ordemdeservico.getNumeroOrdemDeServico());
		Assert.assertNotNull(ordemdeservico.getPrioridade());
		Assert.assertNotNull(ordemdeservico.getDataAbertura());
		Assert.assertNotNull(ordemdeservico.getDescricaoProblema());
	}
	/**
	 * Testa o inserir a ordem de servico no banco de dados, sem informar a data de abertura
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql"
	})
	public void insertOrdemDeServicoMustFailDataContratoNull()
	{
		OrdemDeServico ordemdeservico = new OrdemDeServico();
		Contrato contrato = this.contratoService.findContratoById(10001);
		ordemdeservico.setContrato(contrato);
		ordemdeservico.setNumeroOrdemDeServico("5896");;
		ordemdeservico.setPrioridade(Prioridade.BAIXA);
		ordemdeservico.setDescricaoProblema("Computador Quebrado");
		ordemdeservico.setGestor(new Gestor(1L));
		ordemdeservico.setValorOrdemDeServico(20F);
		ordemdeservico.setStatus(StatusOrdemDeServico.ABERTA);
		ordemdeservico = this.ordemDeServicoService.insertOrdemDeServico(ordemdeservico);
		
		Assert.fail("O sistema deveria infomar ao usuário que a data de abertura não pode ser nula.");
	}
	/**
	 * Testa o inserir a ordem de servico no banco de dados, sem informar a descrição do problema
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql"
	})
	public void insertOrdemDeServicoMustFailDescricaoProblemaNull()
	{
		OrdemDeServico ordemdeservico = new OrdemDeServico();
		Contrato contrato = this.contratoService.findContratoById(10001);
		ordemdeservico.setContrato(contrato);
		ordemdeservico.setNumeroOrdemDeServico("5896");;
		ordemdeservico.setPrioridade(Prioridade.BAIXA);
		ordemdeservico.setDataAbertura(LocalDate.now());
		ordemdeservico.setGestor(new Gestor(1L));
		ordemdeservico.setValorOrdemDeServico(20F);
		ordemdeservico.setStatus(StatusOrdemDeServico.ABERTA);
		ordemdeservico = this.ordemDeServicoService.insertOrdemDeServico(ordemdeservico);
		
		Assert.fail("O sistema deveria infomar ao usuário que a descrição do problema não pode ser nula.");

	}
	/**
	 * Testa o inserir a ordem de servico no banco de dados, sem informar o gestor
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql"
	})
	public void insertOrdemDeServicoMustFailGestorNull()
	{
		OrdemDeServico ordemdeservico = new OrdemDeServico();
		Contrato contrato = this.contratoService.findContratoById(10001);
		ordemdeservico.setContrato(contrato);
		ordemdeservico.setNumeroOrdemDeServico("5896");;
		ordemdeservico.setPrioridade(Prioridade.BAIXA);
		ordemdeservico.setDataAbertura(LocalDate.now());
		ordemdeservico.setDescricaoProblema("Computador Quebrado");
		ordemdeservico.setValorOrdemDeServico(20F);
		ordemdeservico.setStatus(StatusOrdemDeServico.ABERTA);
		ordemdeservico = this.ordemDeServicoService.insertOrdemDeServico(ordemdeservico);
		
		Assert.fail("O sistema deveria infomar ao usuário que o gestor não pode ser nulo.");

	}
	/**
	 * Testa o inserir solicitação de pagamento no banco de dados, com todos os campos obrigatórios preenchidos.
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void insertSolicitacaoPagamentoMustPass()
	{
		SolicitacaoPagamento solicitacao = new SolicitacaoPagamento();
		OrdemDeServico ordemDeServico = this.ordemDeServicoService.findOrdemDeServicoById(5001);
		solicitacao.setOrdemdeservico(ordemDeServico);
		solicitacao.setDataVencimento(LocalDate.now());
		solicitacao.setValorPagamento(100F);
		solicitacao = this.ordemDeServicoService.insertSolicitacaoPagamento(solicitacao);
		Assert.assertNotNull(solicitacao);
		Assert.assertNotNull(solicitacao.getId());
		Assert.assertNotNull(solicitacao.getDataVencimento());
		Assert.assertNotNull(solicitacao.getValorPagamento());
	}
	/**
	 * Testar o inserir  solicitação de pagamento no banco de dados, sem informar a data de vencimento
	 * que é obrigátoria
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void insertSolicitacaoMustFailDataVencimentoNull()
	{
		SolicitacaoPagamento solicitacao = new SolicitacaoPagamento();
		OrdemDeServico ordemDeServico = this.ordemDeServicoService.findOrdemDeServicoById(5001);
		solicitacao.setOrdemdeservico(ordemDeServico);
		solicitacao.setValorPagamento(100F);
		solicitacao = this.ordemDeServicoService.insertSolicitacaoPagamento(solicitacao);
		
		Assert.fail("O sistema deveria informar ao usuário que a data de vencimento não pode ser nula.");

	}
	/**
	 * Testar o inserir a solicitação de pagamento no banco de dados, sem informar o valor de pagamento
	 * que é obrigátorio
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void insertSolicitacaoMustFailValorPagamentoNull()
	{
		SolicitacaoPagamento solicitacao = new SolicitacaoPagamento();
		OrdemDeServico ordemDeServico = this.ordemDeServicoService.findOrdemDeServicoById(5001);
		solicitacao.setOrdemdeservico(ordemDeServico);
		solicitacao.setDataVencimento(LocalDate.now());
		solicitacao = this.ordemDeServicoService.insertSolicitacaoPagamento(solicitacao);
		
		Assert.fail("O sistema deveria informar ao usuário que o valor de pagamento não pode ser nula.");
	}
	/**
	 * Testa o update ordem de servico no banco de dados, com todos os campos obrigatórios preenchidos.
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql"
	})
	public void updateOrdemDeServicoMustPass()
	{
		OrdemDeServico ordemdeservico = new OrdemDeServico();
		Contrato contrato = this.contratoService.findContratoById(10001);
		ordemdeservico.setContrato(contrato);
		ordemdeservico.setNumeroOrdemDeServico("5896");;
		ordemdeservico.setDataAbertura(LocalDate.now());
		ordemdeservico.setPrioridade(Prioridade.BAIXA);
		ordemdeservico.setDescricaoProblema("Computador Quebrado");
		ordemdeservico.setGestor(new Gestor(1L));
		ordemdeservico.setValorOrdemDeServico(20F);
		ordemdeservico.setStatus(StatusOrdemDeServico.ABERTA);
		ordemdeservico = this.ordemDeServicoService.insertOrdemDeServico(ordemdeservico);
		Assert.assertNotNull(ordemdeservico);
		Assert.assertNotNull(ordemdeservico.getId());
		Assert.assertNotNull(ordemdeservico.getNumeroOrdemDeServico());
		Assert.assertNotNull(ordemdeservico.getPrioridade());
		Assert.assertNotNull(ordemdeservico.getDataAbertura());
		Assert.assertNotNull(ordemdeservico.getDescricaoProblema());
	}
	
	/**null
	 * Aprovar a Ordem de Servico
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void updateOrdemDeServicoToAprovarMustPass()
	{
		OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(5001);
		ordemdeservico = this.ordemDeServicoService.updateOrdemDeServicoToAprovar(ordemdeservico);
		Assert.assertEquals(StatusOrdemDeServico.APROVADA, ordemdeservico.getStatus());
	}
	
	/**
	 * Cancelar a Ordem de Servico
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void updateOrdemDeServicoToCancelarMustPass()
	{
		OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(5001);
		final String motivo = "Cliente suspendeu o contrato";
		ordemdeservico = this.ordemDeServicoService.updateOrdemDeServicoToCancelar(ordemdeservico, motivo);
		Assert.assertEquals(StatusOrdemDeServico.CANCELADA, ordemdeservico.getStatus());
	}
	/**
	 * Homologar a Ordem de Servico
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void updateOrdemDeServicoToHomologarMustPass()
	{
		OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(5002);
		ordemdeservico = this.ordemDeServicoService.updateOrdemDeServicoToHomologar(ordemdeservico);
		Assert.assertEquals(StatusOrdemDeServico.HOMOLOGADA, ordemdeservico.getStatus());
	}
	/**
	 * Concluir a Ordem de Servico
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/gestor/gestor.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void updateOrdemDeServicoToConcluirMustPass()
	{
		OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(5003);
		ordemdeservico = this.ordemDeServicoService.updateOrdemDeServicoToConcluir(ordemdeservico);
		Assert.assertEquals(StatusOrdemDeServico.CONCLUIDA, ordemdeservico.getStatus());
	}
	/**
	 * Buscar Ordem de Serviço pelo id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void findOrdemDeServicoByIdMustPass()
	{
		final OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(5001);
		
		Assert.assertNotNull( ordemdeservico );
		Assert.assertNotNull( ordemdeservico.getId() );
		Assert.assertNotNull( ordemdeservico.getNumeroOrdemDeServico());
		Assert.assertEquals( "5684", ordemdeservico.getNumeroOrdemDeServico() );	
	}
	
	/**
	 * Buscar Ordem de Serviço com um id inexistente
	 */
	@Test(expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void findOrdemDeServicoByIdMustFail()
	{
		final OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(1000000);
		
		Assert.assertNull( ordemdeservico );
		
		Assert.fail("Nenhum registro foi encontrado.");
	}
	/**
	 * Buscar gestor por nome
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void listGestorByNomeMustPass()
	{
		PageRequest pageable = new PageRequest(1, 1);
		final Page<Gestor> gestor = this.ordemDeServicoService.listGestorByNome("Sergio", pageable);
		
		Assert.assertNotNull( gestor );	
	}
	
	/**
	 * Buscar gestor por nome inexistente
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql"
	})
	public void listGestorByNomeMustFail()
	{
		PageRequest pageable = new PageRequest(0, 0);
		final Page<Gestor> gestor = this.ordemDeServicoService.listGestorByNome("xxxxxxx", pageable);
		
		Assert.assertNull( gestor );	
	}
	
	/**
	 * Remove Ordem de Serviço pelo id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void removeOrdemDeServicoMustPass()
	{
		this.ordemDeServicoService.removeOrdemDeServico(5001);					
	}
	/**
	 * Tenta remover Ordem de Serviço com um id inexistente
	 */
	@Test(expected = EmptyResultDataAccessException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql",
		"/dataset/cliente/cliente.sql",
		"/dataset/contrato/contrato.sql",
		"/dataset/ordemdeservico/ordemdeservico.sql"
	})
	public void removeOrdemDeServicoMustFail()
	{
		this.ordemDeServicoService.removeOrdemDeServico(10001);	
		OrdemDeServico ordemdeservico = this.ordemDeServicoService.findOrdemDeServicoById(10001);
		assertNull(ordemdeservico);
		Assert.fail("Nenhum registro foi encontrado.");
	}
}
