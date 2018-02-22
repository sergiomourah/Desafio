package br.com.eits.boot.domain.entity.contrato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class Cliente extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3930887222070290941L;
	
	@Column(length = 144)
	private String nome;
	
	
	public Cliente()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public Cliente( Long id )
	{
		super( id );
	}
}
