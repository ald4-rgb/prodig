package com.bolsadeideas.springboot.backend.apirest2.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*el role tambien tendra el nombre al igual que usrname tambien es unico, 
 * ahora por defecto todo nombre role de springsecurity tiene que tener  un 
 * prefijo role_nombredelrole teninedo en cuenta tienedo en cuenta ese prefijo
 * con 20 caracteres  unique = true,*/
@Entity
@Table(name = "roles")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	@Column(length = 20)
	
	private String nombre;

	public Role	() {
		
		
	}
	
	/*public Role(Long id, String nombre) {
		this.id = id;
		this.nombre= nombre;
	}*/
	public Role	(String authorities) {
		
		this.nombre = authorities;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	
	
	
	
	private static final long serialVersionUID = 1L;

}
