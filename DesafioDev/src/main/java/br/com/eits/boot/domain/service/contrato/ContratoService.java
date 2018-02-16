package br.com.eits.boot.domain.service.contrato;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;

import java.time.LocalDate;

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

import br.com.eits.boot.domain.entity.account.User;
import br.com.eits.boot.domain.entity.account.UserRole;
import br.com.eits.boot.domain.entity.contrato.Contrato;
import br.com.eits.boot.domain.repository.contrato.IContratoRepository;


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
	private MessageSource messageSource;
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato insertContrato(Contrato contrato )
	{
		//Assert.notNull(contrato, this.messageSource.getMessage("contrato.null", null, LocaleContextHolder.getLocale()));
		contrato = this.contratoRepository.save( contrato );
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContrato(Contrato contrato )
	{
		Assert.notNull(contrato, this.messageSource.getMessage("contrato.null", null, LocaleContextHolder.getLocale()));
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContratoToEncerrar(Contrato contrato )
	{	
		Assert.notNull(contrato, this.messageSource.getMessage("contrato.null", null, LocaleContextHolder.getLocale()));
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContratoToSuspender(Contrato contrato )
	{
		Assert.notNull(contrato, this.messageSource.getMessage("contrato.null", null, LocaleContextHolder.getLocale()));
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContratoToReabrir(Contrato contrato )
	{
		Assert.notNull(contrato, this.messageSource.getMessage("contrato.null", null, LocaleContextHolder.getLocale()));
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public void removeContrato(long id)
	{	
		Assert.notNull( id, this.messageSource.getMessage( "id.null", null, LocaleContextHolder.getLocale() ) );
		this.contratoRepository.deleteById(id);
	}
	
	/*@Transactional(readOnly = true)
	public Page<Contrato> listUsersByFilters( String numeroContrato,
											  String nomeCliente,
											  Integer statusContrato,
											  LocalDate dataAbertura,
											  LocalDate dataEncerramento,
			                                  PageRequest pageable )
	{
		Page<Contrato> contratos = this.contratoRepository.listByFilters(numeroContrato, nomeCliente,
				statusContrato, dataAbertura, dataEncerramento, pageable);
		return contratos;
	}*/
	
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
}
