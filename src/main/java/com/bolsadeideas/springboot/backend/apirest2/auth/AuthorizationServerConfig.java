package com.bolsadeideas.springboot.backend.apirest2.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/*Ahora nos vamos a engargar de la consfiguracion del AuthorizationServer
*   que se encargrga de todo el  proceso de auntenticacion por el lado  de oauth2
*   todo lo que teine que ver con el toker con jwt desde el  proceso del login 
*   crear el token  validarlo 
*   tenemos que extender de una configuracion base  e implementar alguanos metodos
*   lo sgundo es marcar como configuracion pero ademas tenemos que habilitar el
*   AuthorizationServer anotamos en @Enable para habilitar estaconfiguracion   entocnes
*   lo priemro que vamos a implementar es un par de atributos por ejemplo el passwordencoder
*   con bcrypt en la clase anteriori lo anotamos como bean pro lo tanto lo podemos utilizar l
*   lo podemos inyectar usamos la notacion @Autowierd , lo segundo tenemos 
*   que nyectar tambien AuthenticationMnager, nececitamos el AuthenticationMnager
*   con soto lo que configuramos la clase anterior  con sus usarios y roles 
*   UserDetailService lo necesitamos para que  el AuthorizationServer lo pueda
*   utiliar para el procesor de login AuthenticationManager lo importamos 
*   es una interfaz , pero por defecto AuthenticationManager  no es un beans 
*   no es un compoenente de spring de alguna forma tenemos 
*   que registrar este objeto en el contenedor de spring proque lo vamos a inyectar
*   con Autowired para que sea un compoenente de spring  que lo podamos reutilizar 
*   en diferentes partes  pero el AuthenticationManager  lo tenemos  en SpringSegurityConfig
*   podemos implementar un metodo que heredamos de la clase configureAdapter y ese metodo lo anotamos con bean
*   paara que retorne este objeto el AuthetincactionManager, 
*   entonces a qui en el @Autowired de que sea ese ol objeto lo vamos a seleccionar 
*   mediante el nombre del beans lo podemos ahcer con el @Qualifier con el calificacor
*   y a qui le inticamos el nombre del beans que queremso inyectar @Qualifier("nom bre del beans")
*   en caso de que tengamos mas instancias del tipo AuthenticatioManager .
*   Lo segudno es implementar  3 metodos de configuracion
*   primero nos engargamos del metodo AuthorizationServerEndpointsConfigurer 
*   que se encarga de todo el proceso autenticacion y tambien de validar
*   el token  primero cada vez que iniciamos secion enviamos nuestro usuario
*   y  contraseña y si todo sale bien realiza la autnenticacion genera el toke 
*   se lo entrega al usuario y despues el suario con ese token el usuario puede
*   acceder a  las distintas paginas o recursos  de nuestra aplicacion back end
*   pero para eso se teien que validar y se realiza en endpoint en  unas rutas que maneja
*   el servidor  de autorizacion tanto para  el login autenticacion donde genera el token 
*   genera el token y su firma 	
*   usamos el endpoin para estas configuraciones primero registramos el authenticationManager
*   primero lo inyectamos en AuthorizationServerEndpointsConfigurer 
*   el segundi paso es registrar el accses token converter ese compoenente lo tenemos
*   que implementar es el encargado de manejar varias cosas al token* al jwt
*   como por ejemplo alamacena los datos de autneticacion del usario username los roles 
*   o cualquier infromacion extra que queramos agregar que serian los claims pro ejemplo
*   si queremos guardar entro del token el email del usario el nombre completo de su apellido
*   su nuemro lo recomendable es que no sea infromacion sensible que ponga en riesgo la informacion del
*   usuario como numeros  de tarjetas de credio o contraseñas ademas del compoenente el accesTokenConverter
*   se engarga de traducir estos valores estos datos del usario que estan codificados dentro del jwt 
*   las conierte en informacion decodificada  para  que el authenticationManager mediante
*   oAuth2 pueda realizar el proceso de autenticacion y por su puesto validar el token
*   en general se encarga  de traducri el token de  acceso para la autenticacion
*   para verificar que el token sea valido, pero vamos a crear este metodo
*   lo anotmos con bean que vamos a crear  un compoenente de spring lo seugndo como crao 
*   de forma automatica el meotod   AccessTokenConverter accessTokenConverter()
*   lo creo como private es importante cambrialo a public , pero vamos a importar
*   la implementacion para jwt JwtAccessTokenConverter  vamos a crear este objeto 
*   creamosla instancia jwtAccessTokenConverter y retornamos este objeto
*   en el metodo accesTokenConverter este metodo que retorna un beans por debajo 
*   trabaja con toda la implementacion el token jwt  para
*   traducri todo la infroamcion y autenticacion oauth2, basicamente
*   los datos resian roles y nombre de usuarios  y cualquien informacion
*   extra dentro de los claims.
*   opcionalmente podemos usar tokenStore.
*   Ahora lo que sigue es configurar nuestro clientes las aplicaciones que van a acceder
*   a nuestra Api-rest en nuestra aplicacion vamos a tener un suolo cliente  
*   que seria nuestro front-end nuestra aplicacion con angular 
*   pero en caso de que tuvieramos varias aplicaciones  que cosnumen estee servicio rest
*   tenemos que registrar uno por uno a estos clientes  con su id codigo secreto o contraseña
*   entocnes nos vamos al metodo configure(ClientDetailsServiceConfigurer clients) 
*   vamos a registrar nuestra aplicacion de angular con sus credenciales nos solo autenticamos
*   con los usuarios de nuestro back end si no con las credenciales que se va a concectar
*   con el backend pdoriamos decir que teien una doble autenticacion un el cliente
*   por el lado de la autenticacion y otro por el usario por el lado de la aplicacion	
*   vamos a crear un nuevo cliente primero vamos a seleccionar el tipo de almacenamiento
*   que es inMemory() vamos a crear un cliene incvocando un metodo withCLiente() , 
*   encontces con withCLiente("angularappp") indicamos el id  corresponde como al username de un usario
*   pero teninedo en cuenta que se refiere a la aplicacion al  cliente,
*   lo sieugiente seria el secret la contraseña  secret("1234") por ejemplo cualquier
*   contraseña pero tenemos que usar el passwordEncoder para encriptar codificar 
*   esta contrseña justo para eso se usa se inyeca passwordEncoder,
*   lo siguiente es el alacnace el letscope basicamente el permiso que
*   va a tener el cliente la aplicacion	 va poder leer informacion y datos
*   y escribir basicamente un curd lo que hace nuestra aplicacion con angular entonces
*   damos permisos de lectura y escritura con el metodo scopes("read","write"),
*   lo sigueinte el tipo de concesrion que tendra nuestra autenticacion 
*   lo hacemos con el metodo athorizedGrantType()  como vamos a obetener el token
*   en nuestro caso  va ser con password authorizedGrantType("password") usamos
*   password cuadno es paracredenciales es cuando nuestros usuario existen en el sistema
*   del back end para autenticar requiere un username y una contraseña ,
*   entonces basicamente es un intercambio entre el codgio de autorizacion
*   via redireccionamiento  url a diferencia de password donde 
*   intercambiamso credenciales de los usarios por el token pero tenemos tambien
*   un 3 tipo implicito co una autenticacion del cliente mucho mas devil es parecida 	
*   al codigo de autorizacion  pero la diferencia es que enviamos el cliente id 
*   password automaticamente en la direccion url recibiremos el token	 sin 
*   pasar por ningun codigo de autorizacion baicamente se usa para aplicaciones 
*   publicas que norequieran mucha seguridad adema tendriamos
*   otroa concecion que seria 
*   refresh_token:Basicamente nos permite tener un token de acceso renovado
*   a travez de este refresh_token  podemos obetener un token de acceso 
*   renovado y actualizado de es forma poder acceder a nuestras pagians o recursos protegidos
*   sin tener que iniciar secion nuevamente sin tener que inicar secion nuevamente
*   siemplemente tenemos este nuevo token  antes que caduque el timpo de validacion
*   podemos tener este token de acceso renovado ,
*   lo siguiente es la validez en vcuanto tiempo va a caducar nuestro token
*   para eso implementamos el metodo accessTokenValiditySeconds( );
*   por ejemplo 1 hora accessTokenValiditySeconds( 3600); , pero 
*   tambien tenemos que configurar algo parecido el tiempo de aspiracion
*   del refresh token entonces con lo mismo refreshTokenValiditySeconds(3600) podemos
*   obetener en token de accseso rnovado si ntener que volver a iniciar secion 
*   coo solo tenemos una aplicacion front end con aangular vamos a tener un solo 
*   cliente  por ejemplo con angular con react andriod en fin cada aplicacacion
*   va a tener su propia credencial,
*   El sigueinte paso es configurara el configure(AuthorizationServerSecurity()
*   lo que se configura a qui son los  permisos de nuestraos end point de nuestrar 
*   rutas de acceso recordemos que tenemos dos  endpoint en el   authorizationServer
*   uno para autnetnticarnos iniciar secion  y se engarga de generar el token y enviarlo 
*   al usario y esa ruta tieen que se  completamente publica acceso para todos 
*   ya que el usario puede iniciar secion puede autenticarse y recibir un token 
*   entonces ocn security damos el permiso , entonces con security.tokenKeyAccess("permitAll()")
*   y a qui usamos la funcion que da accesos a springsecurity y entonces con 
*   permitAll() damos permiso a cualquier usario ya sea anonimo  o no pueda atuenticarse
*   en el Endpoint con e el	oauth/token -> es nuestra ruta para iniciar secion,
*   lo segundo es dar el permiso al endpoint  que se encarga de validar el token
*   cadavez que desamos acceder a una pagina protegida tenemos que 
*   neviar nuestro token nuestro jwt en las cabeceras de nuestra peticion
*   dentro del authorization el metodo seria checkTokenAccess(): este metodo
*   es para validar el token y el primer metodo es para generar el token cuando 
*   se autentica  solo puede acceder a esta ruta los lcientes autenticados
*   checkTokenAccess("isAuthenticated"):.
*   Esto estan el metodo public JwtAccessTokenConverter accessTokenConverter() 
*   Entonces en el objeto jwtAccessTokenConverter vamos a asignar la llava con
*   setSigningKey("codigo.secreto.inportante.123456789"); recrodemos
*   esta clave secreta siempre se guarda en el servidor
*   
*   Nota cuanod es con clave rsa es  la llave privada es la que firma 
*   y la que valida es la llave publica 
*   registraremos en el seridor AuthorizationServer InfoAdicionalToken
*   en el emtodo configure lo autorizamos lo que  haremos es crear una instancia
*   con TokenEnhancerChain: que es una cadena que obetenemos a travez del access 
*   token converter esta nueva informacion  adicional  en el fondo tenemos 
*   que enlazar ambas infromaciones la que viene pro defecto y la que vamos gregar
*   ceremo el objeto TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
*   y econtces ahora con el metodo setTokenEnhancers() vamos a agregar ambos basicamente
*   
*   es un arraylist setTokenEnhancers(infoAdicionalToken,accessTokenConverter())
*   y agregamos InfoAdicionalToken  pero antes inyectamos este compoenete en 
*   Authorization
*   accessTokenConverter(): este seria el metodo que retorna el Accesstoeknconverter
*   del jwt , por ultimo  tenemos que asigna con el metodo tokenEnhacer(pasamos la cadena)
*   tokenEnhacer(tokenEnhacerChain)*/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken; 
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
	clients.inMemory().withClient("angularapp")
	.secret(passwordEncoder.encode("12345"))
	.scopes("read","write")
	.authorizedGrantTypes("password","refresh_token")
	.accessTokenValiditySeconds(3600).
	refreshTokenValiditySeconds(3600);
		
	}
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
		
		
	}
	@Bean
	public JwtTokenStore tokenStore() {
		
		return new JwtTokenStore(accessTokenConverter());
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter .setSigningKey(JwtConfig.RSA_PRIVADA );
		jwtAccessTokenConverter .setVerifierKey(JwtConfig.RSA_PUBLICA);
		return jwtAccessTokenConverter;
	}  
	
	
	
	
}
