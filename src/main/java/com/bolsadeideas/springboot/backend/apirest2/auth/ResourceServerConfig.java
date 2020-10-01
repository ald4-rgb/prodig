package com.bolsadeideas.springboot.backend.apirest2.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*En esta clase vamos a crear la configuracion delservidor de recursos
 * se encarga de dar acceso a los clientes 	 a los recursos de nuestra aplicacion
 * simepre y cuando el token que esta enviado  el cliente en las cabeceras
 * sea un token valida
 * Es importatne usar @EnableResourceServer para habilitar el servidor de recursos
 * @EnableResourceServer
 * vamos a anotar con configuration tambien tenemos que exteneder de una clase adapter per
 * del ResourceServerConfigurerAdapter,
 * lo sigueitne es anotar para habilitar este servidor de recurso y a qui
 * solo tenemos que implementar un solo metodo  el metodo que nos permite implementar
 * toda la regla de seguridad de nuestros endpoints	de nuestras rutas a los recursos
 * de nuestra aplicacion por ejemplo si queremos dar permiso al listado clientes
 * o por ejemplo si el crud sea para los usarios que tengan el role admin,
 * entonces los primero a travez del objeto http vamos  a dar acceso a 
 * apiclientes acceso a todo invocando el metodo authorizeRequests().
 * antMatchers()  y como argumaneto podemos pasar por coma toda nuestras rutas
 * que sean publicas  que cualquie usuario pueda acceder independiente 
 * si haya inicado secion o no antMatchers("/api/clientes")
 * y por ultimo permitir a todos con el metodo  permitAll()
 * y lo suguiente vamos indicar que cualquier otra pagina es privada
 * solamente para usarios autenticados 	con los metodos anyRequest().authenticated()
 * sin enbargo en el metodo podemos indicar com oprimer argumento
 * la peticion  http antMatchers(HttpMethod.GET , "ruta/acceso") HttpMethod.get 
 * solamente ver el listado de cleitnes httpsecurity por el lado oauth.
 * Toda la paginacion tiene que ser publica  entonces con esto 
 * /api/clientes/page/** -> se puede navegar  para todos 
 * tenemos que crear un nuevo antMatchers con el mismot 
 * metodo HttpMethod.Get ,"/api/clientes/{id}":esto sololo vera el admin nadie mas 
 * y con hasAnyRole():en este cas un solo role , aora con el mismo metodo
 * anMatchers HttpMethod.
 * Vmos a llevar todas las reglas a anotaciones que hicimos anteriormente
 * lo importante es dejar  el metodo permitall() -> ya que estamos indicando que 
 * cualquier otro  request  requiere autenticacion  por lo tanto  todo lo que sea 
 * publico tambien lo tenemos que indicar y todo lo demas que sea permisos 
 * con roles.
 * Entocnes lo primero es crear un metodo que esta anotad con @Bean(crearMetodo) 
 * na vez creado el metodo CorsConfigurationSource cosrsConfigurationSource() 
 * dnetro de el tenemso que crear la isntnacia  de corsConfiguration
 * 	CorsConfiguration config =  new CorsConfiguration(); creamos un objeto
 * del tipo CorsCofniguration , enctnce vamso a configurar nuestro cors
 * lo priemro es permitir  el dominio donde reciden nuestras aplicaciones clientes
 * podemos configurar uno o varios entonces con config.setAllowedOrigins(cuales son los origienes)
 * config.setAllowedOrigins(cuales son los origienes) -> es una lista entonces vamos a usar
 * la clase helpedarrays Arrays.asList("htpp://localhost:4200"):a qui agregamos 
 * nuestro dominio que es nuestra aplicacion con angular si queremos seprar por comas
 * podemos usar varios o con asterisco podemos usar varios,
 * lo siguiente es configurar todos los metodos o los verbos que vamos a permitir 
 * en la aplicacion en el backend put pos delete usamos 
 * config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS") ,
 * "OPTIONS" el options es por que  en algunos navegadores cuando enviamos alguna
 * solicitud para solicitar el token a la ruta  OAuth token   esa peticion ese request
 * lo envia como obect , despues configuramos las credenciales
 *	config.setAllowCredentials(true);
 *y por ultimo permitir headers o cabeceras 	
 *config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization")); -> indicamos
 *todas la cabeceras que vamos a enviar  una vez todo configurado tenemos
 *que registrar nuestra configuracion del cors  para todas nuestras rutas 
 *endpiont del backend
 *UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
 *creamos la instancia de esa misma clase el objeto y vamos a registrar el 
 *corsConfiguration 
 *source.registerCorsConfiguration("/**", config); -> indicamos la ruta 
 *para todas las rtuas del bakcend /** , config ->  y pasamos  el config
 *el corsconfiguration por argumento y lo sigueinte es 
 *retornar el source , ahora en el metodo 
 *configure(HttpSecurity http) -> invocamos 
 *.and() -> para volver al objeto  HttpSecurity
 *ans..cors().configurationSource(cosrsConfigurationSource()); ->  y pasamos el metodo cosrsConfigurationSource()
 *lo sguiente es configurar un fltro un beans la prioridad mas alta  de los foltros  para regitrar un beans
 * es la prioddad mas alta  de los filtro de spring  tambien para registrar esta misma configuracion
 * y se aplique tanto al servidor de autorizacion cuando accedamos a los endpiont para
 * autenticarnos y generar el token el oAuth/ y cada vez que queramos Validar nuestro token
 * una vez que estemos autenticados para acceder a nuestro recursoso 
 *	despues @Bean
 *	FilterRegistrationBean<CorsFilter> corsFilter() -> despues de importar hay que indicar el tipo 
 *	de dato de datos que vamos a registrar corsFilter() y creamos la instancia de esta clase
 *	FilterRegistrationBean<CorsFilter> bean  = new  FilterRegistrationBean<CorsFilter> -> y 
 *	por argumento tenemos que pasar una instancia de new  FilterRegistrationBean<CorsFilter>
 *	y a CorsFilte le pasamos la configuracion que tenemos  de la clase antetior
 *	FilterRegistrationBean<CorsFilter> bean  = new  FilterRegistrationBean<CorsFilter>
 *(new CorsFilter(cosrsConfigurationSource())); -> lo pasamos por argumento,
 *lo siguiente es dar un orden bajo  entre mas bajo el orden mayor la precedencia 
 * o prioridad 		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
 * Ordered ->  y utilizando el numerodador Ordered seleccionamos la mas alta
 * setOrder(Ordered.HIGHEST_PRECEDENCE)
 * y por ultimo retornamos el bean. 
 * COnclucion y con esto ya queda configurado  por el springSecurity como 
 * por el lado de oAuth2 los enpiont para obtener el token y para validar 
 * el token y acceder a los recrusos	*/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
  public void configure(HttpSecurity http) throws Exception {
	
	 http.authorizeRequests()
	 .antMatchers(HttpMethod.GET ,"/api/clientes","/api/clientes/page/**","/api/uploads/img/**","/images/**",
			 "/api/usuarios","/api/registro/")
	 .permitAll()
	 /*.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER","ADMIN")
	 .antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("USER","ADMIN")
	 .antMatchers(HttpMethod.POST,"/api/clientes").hasRole("ADMIN")
	 .antMatchers("/api/clientes/**").hasAnyRole("ADMIN")*/
	 .anyRequest().authenticated()
	 .and().cors().configurationSource(cosrsConfigurationSource()) ; 
	}
	@Bean
	public CorsConfigurationSource cosrsConfigurationSource() {
		
		CorsConfiguration config =  new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET" ,"POST","PUT","DELETE","OPTIONS" ));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
		
		UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",config); 
	
		return source;
	
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		 
		FilterRegistrationBean<CorsFilter> bean  = new  FilterRegistrationBean<CorsFilter>
		(new CorsFilter(cosrsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return bean;
		
	}
	
	
	
}
