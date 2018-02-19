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
import br.com.eits.boot.domain.entity.ordemdeservico.OrdemDeServico;
import br.com.eits.boot.domain.entity.ordemdeservico.SolicitacaoPagamento;
import br.com.eits.boot.domain.repository.ordemdeservico.IOrdemDeServicoRepository;

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
		
		//@Autowired
		//private ISolicitacaoPagamentoRepository solicitacaopagamentoRepository;
		
		@Autowired
		private MessageSource messageSource;
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico insertOrdemDeServico(OrdemDeServico ordemdeservico )
		{
			Assert.notNull(ordemdeservico, this.messageSource.getMessage("ordemdeservico.null", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save( ordemdeservico );
			return ordemdeservico;
		}
		
		/*@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public SolicitacaoPagamento insertSolicitacaoPagamento(SolicitacaoPagamento solicitacaopagamento )
		{
			Assert.notNull(solicitacaopagamento, this.messageSource.getMessage("solicitacaopagamento.null", null, LocaleContextHolder.getLocale()));
			solicitacaopagamento = this.solicitacaopagamentoRepository.save( solicitacaopagamento );
			return solicitacaopagamento;
		}*/
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServico(OrdemDeServico ordemdeservico )
		{
			Assert.notNull(ordemdeservico, this.messageSource.getMessage("ordemdeservico.null", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToAprovar(OrdemDeServico ordemdeservico )
		{	
			Assert.notNull(ordemdeservico, this.messageSource.getMessage("ordemdeservico.null", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToCancelar(OrdemDeServico ordemdeservico )
		{	
			Assert.notNull(ordemdeservico, this.messageSource.getMessage("ordemdeservico.null", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToHomologar(OrdemDeServico ordemdeservico )
		{	
			Assert.notNull(ordemdeservico, this.messageSource.getMessage("ordemdeservico.null", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public OrdemDeServico updateOrdemDeServicoToConcluir(OrdemDeServico ordemdeservico )
		{	
			Assert.notNull(ordemdeservico, this.messageSource.getMessage("ordemdeservico.null", null, LocaleContextHolder.getLocale()));
			ordemdeservico = this.ordemdeservicoRepository.save(ordemdeservico);
			return ordemdeservico;
		}
		
		@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
		public void removeOrdemDeServico(long id)
		{	
			Assert.notNull( id, this.messageSource.getMessage( "id.null", null, LocaleContextHolder.getLocale() ) );
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
