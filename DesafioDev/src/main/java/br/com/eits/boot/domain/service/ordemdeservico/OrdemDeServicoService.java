package br.com.eits.boot.domain.service.ordemdeservico;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.application.security.RequestContext;
import br.com.eits.boot.domain.entity.account.User;
import br.com.eits.boot.domain.entity.account.UserRole;
import br.com.eits.boot.domain.entity.contrato.Cliente;
import br.com.eits.boot.domain.entity.contrato.HistoricoContrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;
import br.com.eits.boot.domain.entity.ordemdeservico.Gestor;
import br.com.eits.boot.domain.entity.ordemdeservico.HistoricoOrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.Prioridade;
import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;
import br.com.eits.boot.domain.entity.ordemdeservico.StatusOrdemDeServico;
import br.com.eits.boot.domain.repository.ordemdeservico.IHistoricoOrdemDeServicoRepository;
import br.com.eits.boot.domain.repository.ordemdeservico.IOrdemDeServicoRepository;
import br.com.eits.boot.domain.repository.ordemdeservico.ISolicitacaoPagamentoRepository;

@Service
@RemoteProxy
@Transactional
public class OrdemDeServicoService {

	//Repositories
		/**
		 *
		 */
		@Autowired
		private IOrdemDeServicoRepository ordemdeservicoRepository;
		
		@Autowired
		private ISolicitacaoPagamentoRepository solicitacaopagamentoRepository;
		
		@Autowired
		private IHistoricoOrdemDeServicoRepository historicoOrdemDeServicoRepository;
		
		@Autowired
		private MessageSource messageSource;
			
		/**
		 * Inserir Nova Ordem de Serviço
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico insertOrdemDeServico(OrdemDeServico ordemdeservico )
		{
			//Seta o Status como aberto
			ordemdeservico.setStatus(StatusOrdemDeServico.ABERTA);
			Assert.notNull(ordemdeservico.getNumeroOrdemDeServico(), this.messageSource.getMessage("ordemdeservico.required.numeroOrdem", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getPrioridade(), this.messageSource.getMessage("ordemdeservico.required.prioridade", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDataAbertura(), this.messageSource.getMessage("ordemdeservico.required.dataAbertura", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDescricaoProblema(), this.messageSource.getMessage("ordemdeservico.required.descricaoProblema", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getStatus(), this.messageSource.getMessage("ordemdeservico.required.status", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getGestor(), this.messageSource.getMessage("ordemdeservico.required.gestor", null, LocaleContextHolder.getLocale()));
			//Salvar Ordem de Serviço
			ordemdeservico = this.ordemdeservicoRepository.save( ordemdeservico );
			//Salvar Historico
			this.insertHistoricoOrdemDeServico(new HistoricoOrdemDeServico(LocalDate.now(), null, StatusOrdemDeServico.ABERTA, ordemdeservico));
			return ordemdeservico;
		}
		/**
		 * Inseri uma solicitação de pagamento com o status homologada
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public SolicitacaoPagamento insertSolicitacaoPagamento(SolicitacaoPagamento solicitacaopagamento )
		{
			//Validar Status
			assertTrue(this.messageSource.getMessage("ordemdeservico.validation.solicitarpagamento", null, LocaleContextHolder.getLocale()), 
					solicitacaopagamento.getOrdemdeservico().validarSolicitacaoDePagamento(solicitacaopagamento.getOrdemdeservico().getStatus()));
			Assert.notNull(solicitacaopagamento.getValorPagamento(), this.messageSource.getMessage("solicitacaoPagamento.required.valorPagamento", null, LocaleContextHolder.getLocale()));
			Assert.notNull(solicitacaopagamento.getDataVencimento(), this.messageSource.getMessage("solicitacaoPagamento.required.dataVencimento", null, LocaleContextHolder.getLocale()));
			solicitacaopagamento = this.solicitacaopagamentoRepository.save( solicitacaopagamento );
			return solicitacaopagamento;
		}
		/**
		 * Grava automaticamente o registro do historico em toda evolução e alteração de status.
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public HistoricoOrdemDeServico insertHistoricoOrdemDeServico(HistoricoOrdemDeServico historico )
		{
			//Busca usuário logado
			final User user = RequestContext.currentUser().get();
			historico.setUser(user);
			Assert.notNull(historico.getData(), this.messageSource.getMessage("historico.required.data", null, LocaleContextHolder.getLocale()));
			Assert.notNull(historico.getStatus(), this.messageSource.getMessage("historico.required.status", null, LocaleContextHolder.getLocale()));
			Assert.notNull(historico.getUser(), this.messageSource.getMessage("historico.required.user", null, LocaleContextHolder.getLocale()));
			if (historico.getStatus() == StatusOrdemDeServico.CANCELADA)
				Assert.notNull(historico.getObservacao(), this.messageSource.getMessage("historico.required.observacao", null, LocaleContextHolder.getLocale()));
			historico = this.historicoOrdemDeServicoRepository.save( historico );
			return historico;
		}
		/**
		 * Altera a Ordem de Serviço selecionada
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServico(OrdemDeServico ordemdeservico )
		{
			
			Assert.notNull(ordemdeservico.getNumeroOrdemDeServico(), this.messageSource.getMessage("ordemdeservico.required.numeroOrdem", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getPrioridade(), this.messageSource.getMessage("ordemdeservico.required.prioridade", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDataAbertura(), this.messageSource.getMessage("ordemdeservico.required.dataAbertura", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDescricaoProblema(), this.messageSource.getMessage("ordemdeservico.required.descricaoProblema", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getStatus(), this.messageSource.getMessage("ordemdeservico.required.status", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getGestor(), this.messageSource.getMessage("ordemdeservico.required.gestor", null, LocaleContextHolder.getLocale()));
			//Update Ordem
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		/**
		 * Aprova a Ordem de Serviço com o status "ABERTA"
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToAprovar(OrdemDeServico ordemdeservico )
		{	
			//Validar Status
			assertTrue(this.messageSource.getMessage("ordemdeservico.validation.aprovar", null, LocaleContextHolder.getLocale()), 
					ordemdeservico.validarAprovarOrdemDeServico(ordemdeservico.getStatus()));
			ordemdeservico.setStatus(StatusOrdemDeServico.APROVADA);// Aprovar
			//Update Ordem
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);	
			//Salvar Historico
			this.insertHistoricoOrdemDeServico(new HistoricoOrdemDeServico(LocalDate.now(), "", StatusOrdemDeServico.APROVADA, ordemdeservico));
			return ordemdeservico;
		}
		/**
		 *  Cancela a Ordem de Serviço
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToCancelar(OrdemDeServico ordemdeservico, String motivo )
		{	
			//Validar Status
			assertTrue(this.messageSource.getMessage("ordemdeservico.validation.cancelar", null, LocaleContextHolder.getLocale()), 
					ordemdeservico.validarCancelarOrdemDeServico(ordemdeservico.getStatus()));
			ordemdeservico.setStatus(StatusOrdemDeServico.CANCELADA);// Cancelar
			//Update Ordem
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);	
			//Salvar Historico
			this.insertHistoricoOrdemDeServico(new HistoricoOrdemDeServico(LocalDate.now(), motivo, StatusOrdemDeServico.CANCELADA, ordemdeservico));
			return ordemdeservico;
		}
		/**
		 * Homologa a Ordem de Serviço com o status "APROVADA"
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToHomologar(OrdemDeServico ordemdeservico )
		{	
			//Validar Status
			assertTrue(this.messageSource.getMessage("ordemdeservico.validation.homologar", null, LocaleContextHolder.getLocale()), 
					ordemdeservico.validarHomologacaoOrdemDeServico(ordemdeservico.getStatus()));
			ordemdeservico.setStatus(StatusOrdemDeServico.HOMOLOGADA);// Homologada
			//Update Ordem
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			//Salvar Historico
			this.insertHistoricoOrdemDeServico(new HistoricoOrdemDeServico(LocalDate.now(), "", StatusOrdemDeServico.HOMOLOGADA, ordemdeservico));
			return ordemdeservico;
		}
		/**
		 * Conclui a Ordem de Serviço com o solicitações de pagamento
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToConcluir(OrdemDeServico ordemdeservico )
		{	
			Page<SolicitacaoPagamento> lista = this.listSolicitacaoPagamentoByOrdemDeServicoId(ordemdeservico.getId(), null);
			ordemdeservico.setSolicitacoesPagamento(lista.getContent());
			//Validar Status
			assertTrue(this.messageSource.getMessage("ordemdeservico.validation.conclusao", null, LocaleContextHolder.getLocale()), 
					ordemdeservico.validarConclusaoOrdemDeServico(ordemdeservico.getSolicitacoesPagamento()));
			ordemdeservico.setStatus(StatusOrdemDeServico.CONCLUIDA);// Concluir
			//Update Ordem
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);			
			//Salvar Historico
			this.insertHistoricoOrdemDeServico(new HistoricoOrdemDeServico(LocalDate.now(), "", StatusOrdemDeServico.CONCLUIDA, ordemdeservico));
			return ordemdeservico;
		}
		/**
		 * Exclui a Ordem de Serviço com o status "ABERTA"
		 */
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public void removeOrdemDeServico(long id)
		{	
			//Buscar Ordem de Serviço
			final OrdemDeServico ordemdeservico = this.findOrdemDeServicoById(id);
			//Validar Status
			assertTrue(this.messageSource.getMessage("ordemdeservico.validation.excluir", null, LocaleContextHolder.getLocale()), 
					ordemdeservico.getStatus() == StatusOrdemDeServico.ABERTA);
			Assert.notNull( id, this.messageSource.getMessage( "ordemdeservico.id", null, LocaleContextHolder.getLocale() ) );
			this.ordemdeservicoRepository.deleteById(id);
		}
		/**
		 * Lista as Ordens de Serviço
		 */
		@Transactional(readOnly = true)
		public Page<OrdemDeServico> listOrdemDeServicosByFilters( String numeroContrato,
																  String numeroOrdem,
																  String nomeCliente,
																  StatusOrdemDeServico statusOrdem,
																  Float valorOrdemInicial,
																  Float valorOrdemFinal,
																  LocalDate dataAberturaInicial,
																  LocalDate dataAberturaFinal,
																  LocalDate dataConclusaoInicial,
																  LocalDate dataConclusaoFinal,
																  Prioridade prioridadeOrdem,
								                                  PageRequest pageable )
		{
			Page<OrdemDeServico> ordensdeservico = 
					this.ordemdeservicoRepository.listByFilters(numeroContrato, 
							                                    numeroOrdem,
							                                    nomeCliente,
							                                    statusOrdem,
							                                    valorOrdemInicial,
							                                    valorOrdemFinal,
							                                    dataAberturaInicial,
							                                    dataAberturaFinal,
							                                    dataConclusaoInicial,
							                                    dataConclusaoFinal,
							                                    prioridadeOrdem,
					                                            pageable);
			return ordensdeservico;
		}
		
		/**
		 * @param id
		 * @return
		 */
		@Transactional(readOnly = true)
		public OrdemDeServico findOrdemDeServicoById( long id ) 
		{
			return this.ordemdeservicoRepository.findById( id )
					.orElseThrow( () -> new IllegalArgumentException( translate( "repository.notFoundById", id ) ) );
		}
		/**
		 * Busca o gestor filtrando pelo nome
		 */
		@Transactional(readOnly = true)
		public Page<Gestor> listGestorByNome( String nome,
				                              PageRequest pageable)
		{
			return this.ordemdeservicoRepository.listGestorByNome(nome, pageable);
		}
		/**
		 * Lista as solicitações de pagamento da Ordem de Serviço
		 */
		@Transactional(readOnly = true)
		public Page<SolicitacaoPagamento> listSolicitacaoPagamentoByOrdemDeServicoId( Long Id,
				                                                          PageRequest pageable)
		{
			return this.solicitacaopagamentoRepository.listSolicitacaoPagamentoByOrdemId(Id, pageable);
		}
		/**
		 * Lista os históricos da Ordem de Serviço
		 */		
		@Transactional(readOnly = true)
		public Page<HistoricoOrdemDeServico> listHistoricoOrdemDeServicoByOrdemDeServicoId( Long Id,
				                                                          PageRequest pageable)
		{
			return this.historicoOrdemDeServicoRepository.listHistoricoOrdemDeServicoByOrdemDeServicoId(Id, pageable);
		}
}
