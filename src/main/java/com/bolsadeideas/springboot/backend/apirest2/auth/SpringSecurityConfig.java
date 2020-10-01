package com.bolsadeideas.springboot.backend.apirest2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*Lo primero es decorar esta clase como configuracion de spring
 *anotamos con  @Configuration lo sigueinte 
 *es extender la clase webSecurityCOnfigurerAdapter y lo sigueinte
 *es un atributo de nuestra clase service del usuario service  que 
 *implementa la interface  UserDetailsService ya que nuestra  implementacion
 *suario serice  impelementa esta interfaz es del tipo  userdetailservice 
 *inyectamos con @Autowired nota al inyectar una interfaz va a buscar 
 *  una implementacion concreta que sea  de este tipo y como tenemos  una 
 *  sola va a inyectar usuarioService lo siguiente es registrar en el autentication 
 *  manayer de springsecurity  vamos a sobrescribir un metodo a qui tambien aremos
 *  un @Autowired ya que tenemos que inyectar este compoenente 
 *  AuthenticationManagerBuilder auth de spring tenemos que inyectar 
 *  via argumento del metodo auth.objeto para configurar y registramos
 *  el userDetailsService y vamos a padsar el primer atributo que declaramos
 *  lo sigueitne es configurar el passwordEnCoder
 *  passwordEnCoder: a qui tenemos que pasar una instancia 	 de una implementacion
 *  de un encriptador de password para dar mas  seguridad  vamos a utilizar 
 *  la encriptacion un encriptador de password para dar mas seguridad
 *   bycript es la encriptacion por defecto  que es la recomendado por springsecurity  
 *   da mucha mas seguridad a nueestras contraseñas  vamos a crear un nuevo metodo 
 *   que se encarga de crear  este  metodo passwordEncoder() con un tipo de retorno
 *   que seria un BCryptPasswordEnCoder y retornamos new la instancia de este beans
 *   pero ademas de este metodo tenemos que guardar  registrar este objeto que estamos 
 *	 creando y que esta retornando este metodo como un compoenete de spring un beans
 *  ya que mas adealnte sera necesariolo  y la forma de registrar  objetos que retronan
 *  un metodo  lo hacemos  con la notacion @Bean , entonces @Bean nos permitra a travez
 *   de un metodo  el objeto que retorna el metodo  BCryptPasswordEncoder() lo
 *   va a registrar en el  contenedor de spring por eso tambien se puede
 *   intectar con @Autowired y utilizar otra clase de configuracion o en cualquier 
 *   clase de spring. 
 *   podemos implementar un metodo que heredamos de la clase configureAdapter y ese metodo lo anotamos con bean
*   paara que retorne este objeto el AuthetincactionManager si nos fijamos el metodo ya viene list
*   retorna authenticationManager entonces simplemente le coloclamos  la notacion bean
*   entonces ya lo podemos inyectar por defecto ycentonces este nombre se va registrar con el
*   nombre authenticationManager el metodo ya viene listo y retrona con super.
*  y a qui tenemos httpsecurity por el lado de spring y por el lado de
*  spring tenemos que deshabilitar alguna protecciones por ejemplo CSRF:Cross-site
*  fogery o falsificacion de peticion en stios cruzados basicamente es para
*  protejes nuestro formulario	a travez de un token, pero como estamos 
*  trabjando con angular por el lado forntend  no necesitamos esta proteccion de 
*  formaulario en spring en el servido  por lo tanto hay que deshabilitar,
*  vamos a tener el and()  para volver  la connfgiuracion de httpsecurity
*  y despues vamos a deshabilitar csrf().disable() y ademas vamos a deshabilitar
*  las  seciones el manejo de secion en laautentiacion por el lado de 
*  springSecurity lo dejamos sin eestado stateless , tenemos 
*  que configurar  el  .sessionManagement() para que sea sin estado invocamos el metodo
*  .sessionCreationPolicy(SessionCreationPolicy.STATELESS) y de forma estatica con la 
*  clase SessionCreationPolicy invocamos la cosntante STATLESS
*  SessionCreationPolicy : nota importnante en realidad es un enumerador
*  mas que una clase  enumerados que es un conjunto de cosntante 
*  con configuracion y opciones y por lo tanto el manejo de secion 
*  queda deshabilitado por el lado de spring.
*  Aqui vamos a habilitar la notacion @EnableGlobalMethodSecurity
*  para hacer por notacion  y mas simplificada los permisos de usuario , admin
*  @EnableGlobalMethodSecurity(securedEnabled = true) habilitamos securedEnabled = true 	
*  */
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService usuarioService;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(); 
	}
	
	@Override
	@Autowired
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
	auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	

	}
	@Bean("authenticationManager")
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
	
			http.authorizeRequests().anyRequest()
			.authenticated()
			.and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	

}
