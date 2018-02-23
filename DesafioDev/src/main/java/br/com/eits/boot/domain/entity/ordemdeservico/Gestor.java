package br.com.eits.boot.domain.entity.ordemdeservico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@EqualsAndHashCode
@DataTransferObject
public class Gestor extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5392443927274071455L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 144)
	private String nome;
	
	public Gestor()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public Gestor( Long id )
	{
		super( id );
	}
}
