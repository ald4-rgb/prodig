package com.bolsadeideas.springboot.backend.apirest2.models.services;

//import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest2.models.dao.IUsuarioDao;
//import com.bolsadeideas.springboot.backend.apirest2.models.entity.Role;
import com.bolsadeideas.springboot.backend.apirest2.models.entity.Usuario;

/*a qui tenemos que implementar una interface , pero 
 * no es cualquier interface ya que en este caso  en particular
 * la intercace la prove spring security es una interfaz propia 
 * para trabajar con jpa o con cualquie tiemp de provedor 
 *  para implementar  el proceso  de login  el proceso de autenticacion 
 *  ya se a travez de jpa  o jdc cualquei provedro  tambien podemos tener
 *  nuestra propia interfez . pero ademas tenemos quei mplementar 
 *   esta interface  de spring security se llama UserDetailService
 *   UserDetailService: tiene un contrato un metodo que tenemos que implemntar
 *   sobre la clase UsuarioService  ahora tenemos que anotar UsersService
 *   como una clase  compoenente de spring pero  del tipo  @Service
 *   Nota
 *   Recordemos que con un @Service la anotamos como bean dentro del contenedor 
 *   recrodemos que esta anotacion es un estereotipo de la notacion compoenent,
 *   y lo siguietne es anotar el metodo UserDetail como  @Transactionl  es importante 
 *   que sea de spring y no de javax . 
 *   como es una consulta vamos realizar una consulta por el usaer name tien que ser 
 *   solamente  de lectura  read only.
 *   El seugno paso seria inyectar el repositorio que creamos anteriormente 
 *   el objeto beans dado y eotnces con Autowired para inyectar.
 *   dentro del metodo UserDetails obtenemos el usuario a travez de su username
 *   usando el dao con .finbyuser el metodo retorna un UserDetails que seria
 *   el usario de springsecurity esto es una interfaz pero tambien tenesmo la implementacion
 *   concreta y ecntonces en el return creamos la instancia del User vamos ai mportar de springsecurity
 *   pero el username lo tenemos en el mismo metodo y tambien tenemos el usario por 
 *   lo tanto el username lo obetenemos a travez del parametro  que recibimos por argumento o
 *   bien con el meotod get del objeto usuario vamo a crear al varaible authorities que es un tipo de 
 *   clorecion es un list es un colecction del tipo GrantedAuthority 
 *   con list agregamos  GrantedAuthority  y con athortities obetenmos los roles del usuario
 *   pero si nos fijamos nuestros roles son del tipo de la clase entity lo tenemos que convertir 
 *   a un tipo GrantedAuthority pero para eso usaremo el api de java 8 que nos rpove los streams
 *   de una forma bastante simple  pero como roles es una lista podemos convertir este objeto de 
 *   coleccion en podemos convertir lo en un un streiam() de java 
 *   y con esta api podemos  obtener cada elemento de este flujo y convertirlo
 *   al tipo GrantedAuthority como por ejemplo  vamos a invocar el metodo map
 *   para convertir los metodos del flujo los objetos
 *   por argumento recibimos el objeto role del flujo del stream y por cada 
 *   role  lo vamos a convertir a un objeto  GrantedAuthority esta es la interfaz
 *   lo tenemos que convertir a la impelemntacion concreta  que es SimpleGrantedAuthority
 *   pero a qui usamos una funcio anonima  expresion lamda -> encotnes por cada
 *   role lo vamos a transformar en un objeto simple  SimpleGrantedAuthority y le pasamos el nombre 
 *   del role  que seria del tipo stream pero eso lo obtenemos a travez del objeto role
 *   , pero sigue siendo un stream un stream grantedauthority  ahora tenemos que convertir este
 *   stream a un tipo de coleecion  collect listaun tipo lies 
 *   .map(role -> new SimpleGrantedAuthority(role.getNombre())
 *   y usando la clase  Collectors y el metodo estatico tolist para convertir 
 *   .collect
 *   entonces de forma autnmatica lo que hizimos fue tomar  la lista de roles de tipo de la
 *   calse entity role un arraylist y lo convertimso a un tipo collection o list tambien a un
 *   tipo list pero del tipo SimpleGrantedAuthority con el new usansmo el metodo map 
 *   basicamente estamos haciendo uso del api stream de java 8 invocando el operador map o metodo map 
 *   para realizar estas conversiones dentro del flujo
 *   y se lo pasamos al objeto user de SpringSecurity.
 *   Lo siguiente seria manejar  errores  ¿Que pasa siel usuario no existe  a travez del usar name?
 *   tambien podraimos implemntar el logg  para escribir los errores en la consola en tonces
 *   declaramos el atributo Loggger logger 	
 *  dentron del metodo entonces con un if  pregutnamos si el usuario es null vamos a guardar 
 *  un mensaje de error el log  logger.error("Error en el login no existe el usuario  '"+username+"'en el sistema");
 *  y ademoas lanzamos una expcepcion del tipo   UsernameNotFoundException  con throw 
 *  con el mismo mendsaje de error 
 * 	throw  new UsernameNotFoundException("Error en el login no existe el usuario  '"+username+"'en el sistema");
 * ahora que pasa si yo quiero mostrar el nombre del role en la consola tabmien entonces por cada item 
 * por cada elemento del stream a medida que vamos recorriendo  convirtiendo el tipo role 
 * en un tipo SimpleGrantedAuthority vaya mostrando el nombre del role para eso usamos el meotodo pick 
 * pick : que basicamente es una funcon anonima o de flecha  en expresion lamda basicamente  se recibe el role
 * mas que el role seria el authority de SpringSecurirty 
 * getAuthority() = contiene el nombre del role
 * .pick(authority -> logger.info("Role"+ authority.getAuthority())).
 * UsuarioService  va tener implementada la interfaz de spring pero tambien la nuestra 
 * y como ya implemnta otro contrato mas perzonalizado a nuestras  consultas
 * y operaciones con jpa vamos ai mplementar el metodo findByUsername dentro 
 * de ese emtodo invocado usamos el usariodao invoacamos el findByUsername(usarname);
 * para boetener el objeto usario completo con toda su informacion,
 * podemos inyectar con cualquier interface IUsuarioService,UserDetailsService
 * pero usaremos IUsuarioService   */
@Service
public class UsuarioService implements IUsuarioService,UserDetailsService{

		private Logger logger  = LoggerFactory.getLogger(UsuarioService.class);
	
	
	@Autowired	
	private IUsuarioDao usuarioDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findall() {
		
		return (List<Usuario>) usuarioDao.findAll();
	}
	
	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		
		return usuarioDao.save(usuario);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		
		return usuarioDao.findByUsername(username);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario	usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null ) {
			
			logger.error("Error en el login no existe el usuario  '"+username+"'en el sistema");
			throw  new UsernameNotFoundException("Error en el login no existe el usuario  '"+username+"'en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario .getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role"+ authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.geEnabled() , true, true, true, authorities);
	}

	/*@Override
	@Transactional
	public Usuario saveRole(Usuario usuario) {
		
		usuario.setRoles(Arrays.asList( new Role((long ) 1, "Role_CLIENTE")  ));
		usuarioDao.save(usuario);
		return usuario;
	}*/

/*	@Override
	@Transactional(readOnly = true)
	public List<Role> findRoledById() {
		// TODO Auto-generated method stub
		return usuarioDao.findById();
	}*/


	
	
}
