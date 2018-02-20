package br.com.eits.boot.test.domain.entity.ordemdeservico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;
import br.com.eits.boot.domain.entity.ordemdeservico.StatusOrdemDeServico;
import br.com.eits.boot.test.domain.AbstractUnitTests;

public class OrdemDeServicoTests  extends AbstractUnitTests{

	/*-------------------------------------------------------------------
	 *                           ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 *                           TESTS
	 *-------------------------------------------------------------------*/
	/**
     * validar alterar ordem de serviço somente com ordens com status 0 "ABERTA".
     */
	@Test
	public void validarAlterarOrdemDeServicoMustPass()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();
		boolean result = ordemdeservico.validarAlterarOrdemDeServico(StatusOrdemDeServico.ABERTA);
		assertTrue(result);
	}
	/**
     * Teste com falha com status 1 "APROVADA"
     */
	@Test
	public void validarAlterarOrdemDeServicoMustFail()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();
		boolean result = ordemdeservico.validarAlterarOrdemDeServico(StatusOrdemDeServico.APROVADA);
		assertFalse(result);
	}
	/**
     * validar Homologação de  ordem de serviço somente com ordens com status 1 "APROVADA".
     */
	@Test
	public void validarHomologacaoOrdemDeServicoMustPass()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();
		boolean result = ordemdeservico.validarHomologacaoOrdemDeServico(StatusOrdemDeServico.APROVADA);
		assertTrue(result);
	}
	/**
     * Teste com falha com status 0 "ABERTA"
     */
	@Test
	public void validarHomologacaoOrdemDeServicoMustFail()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();
		boolean result = ordemdeservico.validarHomologacaoOrdemDeServico(StatusOrdemDeServico.ABERTA);
		assertFalse(result);
	}
	/**
     * validar solicitacao de pagamento de  ordem de serviço somente com ordens com status 3 "HOMOLOGAÇÃO".
     */
	@Test
	public void validarSolicitacaoPagamentoMustPass()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();
		boolean result = ordemdeservico.validarSolicitacaoDePagamento(StatusOrdemDeServico.HOMOLOGADA);
		assertTrue(result);
	}
	/**
     * Teste com falha com status 1 "APROVADA"
     */
	@Test
	public void validarSolicitacaoPagamentoMustFail()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();
		boolean result = ordemdeservico.validarSolicitacaoDePagamento(StatusOrdemDeServico.APROVADA);
		assertFalse(result);
	}
	
	/**
     * validar conclusão de ordem de serviço somente com ordens que possuem solicitação de pagamento.
     */
	@Test
	public void validarConclusaoOrdemDePagamentoMustPass()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();	
		//Criar Solicitação de Pagamento
		SolicitacaoPagamento solicitacaoPagamento = new SolicitacaoPagamento();
		//popular os valores
		float valorPagamento = 50;
		solicitacaoPagamento.setValorPagamento(valorPagamento); 
		//Adicionar solicitação de pagamento a lista
		List<SolicitacaoPagamento> listSolicitacao = new ArrayList<SolicitacaoPagamento>();
		listSolicitacao.add(solicitacaoPagamento);
		ordemdeservico.setSolicitacoesPagamento(listSolicitacao);
		//Acionar método validar
		boolean result = ordemdeservico.validarConclusaoOrdemDeServico(listSolicitacao);
		assertTrue(result);
	}
	/**
     * Teste com falha com status 1 "APROVADA"
     */
	@Test
	public void validarConclusaoOrdemDePagamentoMustFail()
	{
		final OrdemDeServico ordemdeservico = new OrdemDeServico();	
		//Criar Solicitação de Pagamento
		SolicitacaoPagamento solicitacaoPagamento = new SolicitacaoPagamento();
		//Adicionar solicitação de pagamento a lista
		List<SolicitacaoPagamento> listSolicitacao = new ArrayList<SolicitacaoPagamento>();
		ordemdeservico.setSolicitacoesPagamento(listSolicitacao);
		//Acionar método validar
		boolean result = ordemdeservico.validarConclusaoOrdemDeServico(listSolicitacao);
		assertFalse(result);
	}
}
