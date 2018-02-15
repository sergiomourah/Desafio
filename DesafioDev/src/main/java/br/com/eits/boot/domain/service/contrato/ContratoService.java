package br.com.eits.boot.domain.service.contrato;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato insertContrato(Contrato contrato )
	{		
		contrato = this.contratoRepository.save( contrato );
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContrato(Contrato contrato )
	{		
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContratoToEncerrar(Contrato contrato )
	{		
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContratoToSuspender(Contrato contrato )
	{		
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public Contrato updateContratoToReabrir(Contrato contrato )
	{		
		contrato = this.contratoRepository.save(contrato);
		return contrato;
	}
	
	@PreAuthorize("hasAnyAuthority('" + UserRole.ADMINISTRATOR_VALUE + "','" + UserRole.MANAGER_VALUE + "')")
	public void removeContrato(long id)
	{	
		this.contratoRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Page<User> listUsersByFilters( String filter, PageRequest pageable )
	{
		return this.contratoRepository.listByFilters( filter, pageable );
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
}
