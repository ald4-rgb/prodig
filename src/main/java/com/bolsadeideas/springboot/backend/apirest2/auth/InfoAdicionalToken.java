package com.bolsadeideas.springboot.backend.apirest2.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.backend.apirest2.models.entity.Usuario;
import com.bolsadeideas.springboot.backend.apirest2.models.services.IUsuarioService;

/*loprimero es habilitar esta interfaz importamos 
 * y tenemos que importar el metodo enhance
 * dentro del emtodo enhance tenemos 
 * que implementar el bojeto del tipo map implementado
 * con hashmap y dentro de ese hasmap vamos agregar nueva informacion
 * y la asignamos a accesstoken el cual tenemos que 
 * retornar  iportamos el map de java util
 * Map del tipo  String  y vamos a guardar object y creamos 
 * el objeto hashpam el bojeto se llama info 
 * a qui en info  podemos fuargar cualquier 
 * informacion info.put("",""): la idea es que sea liviana la ifnromacion
 * para que nel token sea lo mas ismple posible , tambien podemos 
 * guardar datos del usuario por ejemplo si en la bd tenemos el nombre
 *  el apellido email y queremos pasar esos datos como datos autneticado lo 
 *  podemos hacer por ejemplo implementando en jpa una consulta que 
 *  nos retonre el usuario  por lo tanto tendiramos que inyectar el usuaio
 *  service para obtener esta informacion adicional  y como podriamos
 *  tener el usarname ara realizar esa  con sulta a travez del objeto authentication
 *  justo vamos a contact(authentication.getname,....etc)
 *  Otro tema importante es que  este InforAdicionalToken tiene que ser un compoenente de 
 *  spring por lo tanto hay que usar la notacion @Compoenent
 *  aseignar info al objeto accesesToken pero es del tipo
 *  DefaultAOuth2AccessToken de la interfaz y hacemos un cast que es convertir 
 *  accessTokne a DefaultAOuth2AccessToken  y pasamos el info  
 *  ((DefaultAOuth2AccessToken)accessTokne).setAdditionalInformation(info) 
 *  y inyectatamos la interface con @Autowired y en el metodo OAUTH2AccessToken
 *  vamos  tener el objeto usuario*/

@Component	 
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario	usuario = usuarioService.findByUsername(authentication.getName());  
		
		Map<String, Object> info  = new HashMap<>(); 
		
		info.put("info_adicional", "Hola bienvenido: ".concat(authentication.getName()));  
		
		
		info.put("nombre:",usuario.getNombre());
		
		info.put("apellido:",usuario.getApellido());
		
		info.put("email:",usuario.getEmail());
		
		
		((DefaultOAuth2AccessToken)	accessToken).setAdditionalInformation(info);
		
		return accessToken;

	
	}

}
