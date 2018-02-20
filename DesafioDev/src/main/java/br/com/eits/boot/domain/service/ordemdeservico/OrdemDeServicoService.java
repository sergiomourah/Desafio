package br.com.eits.boot.domain.service.ordemdeservico;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.account.UserRole;
import br.com.eits.boot.domain.entity.ordemdeservico.HistoricoOrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;
import br.com.eits.boot.domain.entity.ordemdeservico.StatusOrdemDeServico;
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
		private MessageSource messageSource;
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico insertOrdemDeServico(OrdemDeServico ordemdeservico )
		{
			Assert.notNull(ordemdeservico.getNumeroOrdemDeServico(), this.messageSource.getMessage("ordemdeservico.required.numeroOrdem", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getPrioridade(), this.messageSource.getMessage("ordemdeservico.required.prioridade", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDataAbertura(), this.messageSource.getMessage("ordemdeservico.required.dataAbertura", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDescricaoProblema(), this.messageSource.getMessage("ordemdeservico.required.descricaoProblema", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getStatus(), this.messageSource.getMessage("ordemdeservico.required.status", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getGestor(), this.messageSource.getMessage("ordemdeservico.required.gestor", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save( ordemdeservico );
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public SolicitacaoPagamento insertSolicitacaoPagamento(SolicitacaoPagamento solicitacaopagamento )
		{
			Assert.notNull(solicitacaopagamento.getValorPagamento(), this.messageSource.getMessage("solicitacaoPagamento.required.valorPagamento", null, LocaleContextHolder.getLocale()));
			Assert.notNull(solicitacaopagamento.getDataVencimento(), this.messageSource.getMessage("solicitacaoPagamento.required.dataVencimento", null, LocaleContextHolder.getLocale()));
			solicitacaopagamento = this.solicitacaopagamentoRepository.save( solicitacaopagamento );
			return solicitacaopagamento;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public HistoricoOrdemDeServico insertHistoricoOrdemDeServico(HistoricoOrdemDeServico historico )
		{
			//Assert.notNull(solicitacaopagamento.getValorPagamento(), this.messageSource.getMessage("solicitacaoPagamento.required.valorPagamento", null, LocaleContextHolder.getLocale()));
			//Assert.notNull(solicitacaopagamento.getDataVencimento(), this.messageSource.getMessage("solicitacaoPagamento.required.dataVencimento", null, LocaleContextHolder.getLocale()));
			//solicitacaopagamento = this.solicitacaopagamentoRepository.save( solicitacaopagamento );
			return historico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServico(OrdemDeServico ordemdeservico )
		{

			Assert.notNull(ordemdeservico.getNumeroOrdemDeServico(), this.messageSource.getMessage("ordemdeservico.required.numeroOrdem", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getPrioridade(), this.messageSource.getMessage("ordemdeservico.required.prioridade", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDataAbertura(), this.messageSource.getMessage("ordemdeservico.required.dataAbertura", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getDescricaoProblema(), this.messageSource.getMessage("ordemdeservico.required.descricaoProblema", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getStatus(), this.messageSource.getMessage("ordemdeservico.required.status", null, LocaleContextHolder.getLocale()));
			Assert.notNull(ordemdeservico.getGestor(), this.messageSource.getMessage("ordemdeservico.required.gestor", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToAprovar(OrdemDeServico ordemdeservico )
		{	
			ordemdeservico.setStatus(StatusOrdemDeServico.APROVADA);// Aprovar
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToCancelar(OrdemDeServico ordemdeservico )
		{	
			ordemdeservico.setStatus(StatusOrdemDeServico.CANCELADA);// Cancelar
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToHomologar(OrdemDeServico ordemdeservico )
		{	
			ordemdeservico.setStatus(StatusOrdemDeServico.HOMOLOGADA);// Homologada
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToConcluir(OrdemDeServico ordemdeservico )
		{	
			ordemdeservico.setStatus(StatusOrdemDeServico.CONCLUIDA);// Concluir
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public void removeOrdemDeServico(long id)
		{	
			Assert.notNull( id, this.messageSource.getMessage( "ordemdeservico.id", null, LocaleContextHolder.getLocale() ) );
			this.ordemdeservicoRepository.deleteById(id);
		}
		
		/*@Transactional(readOnly = true)
		public Page<OrdemdeServico> listOrdemDeServicosByFilters( String numeroContrato,
												  String nomeCliente,
												  Integer statusContrato,
												  LocalDate dataAbertura,
												  LocalDate dataEncerramento,
				                                  PageRequest pageable )
		{
			Page<OrdemDeServico> ordensdeservico = this.ordemdeservicoRepository.listByFilters(numeroContrato, nomeCliente,
					statusContrato, dataAbertura, dataEncerramento, pageable);
			return ordensdeservico;
		}*/
		
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
}
