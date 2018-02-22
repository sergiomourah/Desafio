package br.com.eits.boot.domain.service.contrato;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.entity.contrato.HistoricoContrato;
import br.com.eits.boot.domain.entity.contrato.StatusContrato;
import br.com.eits.boot.domain.repository.contrato.IClienteRepository;
import br.com.eits.boot.domain.repository.contrato.IContratoRepository;
import br.com.eits.boot.domain.repository.contrato.IHIstoricoContratoRepository;


@Service
@RemoteProxy
@Transactional
public class ContratoService {

	//Repositories
	/**
	 *
	 */
	@Autowired
	private IContratoRepository contratoRepository;
	
	@Autowired
	private IHIstoricoContratoRepository historicoContratoRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	public Contrato insertContrato(Contrato contrato )
	{
		Assert.notNull(contrato.getStatus(), this.messageSource.getMessage("contrato.required.status", null, LocaleContextHolder.getLocale()));
		Assert.notNull(contrato.getNumeroContrato(), this.messageSource.getMessage("contrato.required.numeroContrato", null, LocaleContextHolder.getLocale()));
		Assert.notNull(contrato.getDataContrato(), this.messageSource.getMessage("contrato.required.dataContrato", null, LocaleContextHolder.getLocale()));
		Assert.notNull(contrato.getCliente(), this.messageSource.getMessage("contrato.required.cliente", null, LocaleContextHolder.getLocale()));
		//Salvar Contrato
		contrato = this.contratoRepository.save( contrato );		
		//Salvar Historico
		this.insertHistoricoContrato(new HistoricoContrato(LocalDate.now(), "", StatusContrato.ABERTO, contrato));
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public HistoricoContrato insertHistoricoContrato(HistoricoContrato historico )
	{
		//Buscar User logado.
		final User user = RequestContext.currentUser().get();
		historico.setUser(user);
		Assert.notNull(historico.getData(), this.messageSource.getMessage("historico.required.data", null, LocaleContextHolder.getLocale()));
		Assert.notNull(historico.getStatus(), this.messageSource.getMessage("historico.required.status", null, LocaleContextHolder.getLocale()));
		Assert.notNull(historico.getUser(), this.messageSource.getMessage("historico.required.user", null, LocaleContextHolder.getLocale()));
		if (historico.getStatus() == StatusContrato.SUSPENSO)
			Assert.notNull(historico.getObservacao(), this.messageSource.getMessage("historico.required.observacao", null, LocaleContextHolder.getLocale()));
		historico = this.historicoContratoRepository.save( historico );
		return historico;
	}
	
	public Contrato updateContrato(Contrato contrato )
	{
		Assert.notNull(contrato.getStatus(), this.messageSource.getMessage("contrato.required.status", null, LocaleContextHolder.getLocale()));
		Assert.notNull(contrato.getNumeroContrato(), this.messageSource.getMessage("contrato.required.numeroContrato", null, LocaleContextHolder.getLocale()));
		Assert.notNull(contrato.getDataContrato(), this.messageSource.getMessage("contrato.required.dataContrato", null, LocaleContextHolder.getLocale()));
		Assert.notNull(contrato.getCliente(), this.messageSource.getMessage("contrato.required.cliente", null, LocaleContextHolder.getLocale()));
		//Update Contrato
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	public Contrato updateContratoToEncerrar(Contrato contrato )
	{	
		contrato.setStatus(StatusContrato.ENCERRADO);//2 - Encerrar Contrato;
		//Update Contrato
		contrato = this.contratoRepository.save(contrato);
		//Salvar Historico
		this.insertHistoricoContrato(new HistoricoContrato(LocalDate.now(), "", StatusContrato.ENCERRADO, contrato));
		return contrato;
	}
	
	public Contrato updateContratoToSuspender(Contrato contrato , String motivo)
	{
		contrato.setStatus(StatusContrato.SUSPENSO);//2 - Suspender Contrato;
		//Update Contrato
		contrato = this.contratoRepository.save(contrato);
		//Salvar Historico
		this.insertHistoricoContrato(new HistoricoContrato(LocalDate.now(), motivo, StatusContrato.SUSPENSO, contrato));
		return contrato;
	}
	
	public Contrato updateContratoToReabrir(Contrato contrato )
	{
		contrato.setStatus(StatusContrato.ABERTO);//2 - Reabrir Contrato;
		//Update Contrato
		contrato = this.contratoRepository.save(contrato);	
		//Salvar Historico
		this.insertHistoricoContrato(new HistoricoContrato(LocalDate.now(), "", StatusContrato.ABERTO, contrato));
		return contrato;
	}
	
	public void removeContrato(long id)
	{	
		Assert.notNull(id, this.messageSource.getMessage( "contrato.id", null, LocaleContextHolder.getLocale() ) );
		this.contratoRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Page<Contrato> listContratoByFilters(String numeroContrato,
											  String nomeCliente,
											  StatusContrato statusContrato,
											  LocalDate dataAberturaInicial,
											  LocalDate dataAberturaFinal,
											  LocalDate dataEncerramentoInicial,
											  LocalDate dataEncerramentoFinal,
			                                  PageRequest pageable )
	{
		Page<Contrato> contratos = 
				this.contratoRepository.listByFilters(numeroContrato, 
						                              nomeCliente,
				                                      statusContrato, 
				                                      dataAberturaInicial,
				                                      dataAberturaFinal,
				                                      dataEncerramentoInicial,
				                                      dataEncerramentoFinal,
				                                      pageable);
		return contratos;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Contrato findContratoById( long id )
	{
		return this.contratoRepository.findById( id )
				.orElseThrow( () -> new IllegalArgumentException( translate( "repository.notFoundById", id ) ) );
	}
	
	@Transactional(readOnly = true)
	public Page<Cliente> listClienteByNome( String nome,
			                              PageRequest pageable)
	{
		return this.clienteRepository.listClienteByNome(nome, pageable);
	}
}
