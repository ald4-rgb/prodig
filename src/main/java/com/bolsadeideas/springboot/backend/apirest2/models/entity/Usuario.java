package com.bolsadeideas.springboot.backend.apirest2.models.entity;

import java.io.Serializable;
//import java.util.Arrays;
import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

/*Lo primeo como buenas prctica y reecomendacionque toda la clase
 * eentity impletmente la interfaz serealizable de esta formaa se puede
 * convertir serealizar de un objto de java a una estruvtura jsony tambien
 * nos permite akmzacenar o guardar el objet en una secion http, lo siguiente es
 * anotar con entity para marcar esta clase como de percistencia  una clase que 
 * esta mapeada a una tabla, tambien anotar con table 
 * apra indicar el nombre de la table si queremos que sea 
 * disitinta al nombre de la clase si se emite 
 * va a tomar el onmobre de la clase igual que al nonmbre de la table.
 * agrgamso la llave primario y entonces con esta antacion 	@Id: lo que 
 * hacemos es indicar a jpa que la llave  primaria  lo sigueinte 
 * es anotar ocn generatedvalue para indicar  la estrategia en como se genera  
 * este id el valor  como en este caso usamos mysql tenemos que usar el autoincrementable 
 * tenemos que usar la estrategia identity, pero ademas del id va a tener como  campo 
 * como atributo el username , password y enabled basicamente es un booleano 
 * para habilitar o deshabilitar la cueta de usuario.
 * En username usaremos la anotacion clumn para dar alguan configuracion extra
 *  en la meta data  del campo en la tabla  como por ejemeplo para 
 *  indicar que es unico  el username es unico  en la tabla 
 *  tabmien por ejemplo  si queremos agregar  el length la cantidad 
 *  de caracteres que tendra el username el password tambien lo podemos dejar con
 *  un @Column(unique = true , length = 60) por que el password despues lo vamos 
 *  a encriptar  en un hash en una secuencia alta de caracteres por ejemplo usando 
 *  Byscript para dar mayor seguridad.
 *  El siguiente atributo es importante por que permitira relacionar ambas clases usuario
 *  va tener una lista  una coleccion  de objetos  del tipo role y tenemos que indicar 
 *  como se van a relacionar  es una relacion de muchos a mucho por lo tanto vamos 
 *  a indicar many to many => anotacion de jpa la importamos y la configuramos 
 *  primero el fetch va ser del tipo  lizy con carga floja  por defecto tambien 
 *  el  cascade muy importante dejarlo en all , es decir cada que se elimine 
 *  al usario  va  eliminar  tambien  sus roles  asignados  o cda vez que  agreguemos
 *   un suario con roles  ademad de guardar  el usuario  tabmien va  aa guardar y crear los 
 *   roles que se asignan  por defecto recordemos  todo el esquema de tablas  de la bd  se 
 *   va acfrear a partir del entity , etonces cuando usamos ManyToMany va a crear 
 *   una tabla intermedia  esta tabla intermedia  es para unir el usuario con el role debe
 *   contener la llave foranea y los ids de cada uno por defecto esta tabla intermedia 
 *   se va llamar concatenando el nombre de las tablas por ejemplo usuarios_roles y las llaves
 *   la llave foranea va a corresponder al nombre de la clase_is para usuario_id 
 *   y el nombre del atributo de la reacion en este caso roles_id  se puede sobrescribir tambien se
 *   puede indicar el nombre  que teine  o que va a tener nuestra tabla intermedia y tambien los nombres
 *   de las llaves foraneas.
 *   crear meotdo getters and setters.
 *   otro punto importante es decir la relacion es un solo sentido solamente estamos dando la 
 *   relacion en usuario no en role ya que en role no tiene sentido obetener los usuarios
 *   pero si en usuarios es importante obetener sus roles .
 *   Que pasa si nuestra tabla tenga otro nombre para poderpersonalizar ese nombre igual 
 *   que los campos las llaves foraneas poder indicar el nombre eso lo podemos hacer en la 
 *   clase dueña es decir en usario con la notacion jointable ahi indicamos el nombre que 
 *   le queramos dar en vez de usuarior roles users_authorities y ¿como indicamos 
 *   el foreing key ? primero joinCulumn va hacer igual a la notacion @JoinCulumn
 *   y con el atributo namde indicamos el nombre de la foreing key con usuarios
 *   ya que estamos en la clase usuarios dueñar de la relacion y como impelemntamos
 *   la  ofreing key de roles usamos inverseJoinColumns entonces a qui indicamos 
 *   el nombre de la otra columna y eso seria todo , enontces en 
 *   @JoinTable indicamos con name = "nombre_tabla_intermedia",
 *   joinColumns indicamos con la anotacion @JoinColumn(name="indicamos_foreignkey") del
 *   dueño de la relacion en este caso usuario, 
 *   inverseJoinColumns @JoinColumn(name="nombre_foreign_key de la otra tabla de la realcion").
 *   y otro punto importante para que uqede mas robusto el dieseño de tabla  es indicar  que 
 *   el usuario id  y el role id van hacer unicos por lo tanto no podria tener repetido ese mismo role
 *   es para controlar para dar una regla un cosntrain al que el usario tenga repetido un role
 *   asignado en la tabla	 
 *   */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "Usuario_Seq",sequenceName = "usuario_sequence", allocationSize = 1)
	private Long id;
	@NotEmpty(message = "no puede estar vacio")
	@Column(unique = true, length = 20)
	private String username;
	@NotEmpty(message = "no puede estar vacio")
	@Column(length = 60)
	private String password;
	
	private boolean enabled;

	private String nombre;
	private String apellido;
	@Column(unique = true)
	private String email;
//, cascade = CascadeType.ALL
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"), 
	uniqueConstraints = {@UniqueConstraint(columnNames = { "usuario_id", "role_id" }) })
	

	private List<Role> roles;
	

/*	@PrePersist
	public void prePersist(){
	
		this.roles = Arrays.asList(new Role("ROLE_ADMIN"));
		
	}*/

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean geEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	
	
	
	

	private static final long serialVersionUID = 1L;

}
