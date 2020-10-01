package com.bolsadeideas.springboot.backend.apirest2.models.services;

import java.util.List;

//import com.bolsadeideas.springboot.backend.apirest2.models.entity.Role;
import com.bolsadeideas.springboot.backend.apirest2.models.entity.Usuario;

/*Esta es una interfaz personalizada vamos a agregar un contrato 
 * que nos retorne al usuario por su username	y esta interfaz tambien la tiene que implementar
 * el usarioservice */
public interface IUsuarioService {

	
	public  Usuario findByUsername(String  username);

	public List<Usuario> findall();
	
	public Usuario save (Usuario usuario );
	
//	public Usuario saveRole(Usuario usuario);
	
//	public List<Role> findRoledById();
	
}
