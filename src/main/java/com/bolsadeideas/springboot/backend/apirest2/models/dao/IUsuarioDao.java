package com.bolsadeideas.springboot.backend.apirest2.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest2.models.entity.Usuario;



/*para poder  trabajar e impleentar con crud rpository heredar
 * de la interface del api de springdata vamos a exteneder  de la interface
 * crud repository importamos y a qui tenemos que indicar los tipos
 * por ejemplo generic la clase etity para implementar las consultas  y operaciones
 *  en este caso  usuario y su id  que seria el tipo que es seria Long , lo siguiente
 *  es importar un metodo perzonalizado  para realizar  la consulta 
 *  hay diferentes formas de implementar  esta consulta  perzonalzada
 *   ya sea a travez  de la notacion query utlizando el lenguaje jpa 
 *   query languaje o bien se podriamos realizar consultas a travez del nombre del metodo 
 *  como por ejemplo vamos a retornar al usuario  luego viene el nombre del metodo del 
 *  metodo para que sea del tipo query de un tipo consulta tiene que tener convencion de nombre
 *  por ejemplo	find que es para buscar a travez de la palabra clave By buscar por  con By 
 *  estamos indicando que  vamso a implementar una condicion were y despues el aparametro
 *  lo vamos a buscar por ejemplo el nombre del campo del atributo comenzando en mayuscula
 *  entonces pro detras de escena lo que va hacer es realizar una consulta  jpa 
 *  A través del nombre  del método (query method name ) se ejecutara la consulta JPLQL:
 *  select u from Usuario u where u.username=? igual a un parametro , y este paraetro
 *  lo vamos a pasar por argumento  del tipo string eso seria la forma a travez del nombre del metodo.
 *	**public  Usuario findByUsernameAndEmail(String  username,String email);**
 *  Ahora en este caso no hablitamos  solo buscaremos el user name
 *  la otra foma es con la notacion implementamos la notacion
 * 	un select al objeto usuario a la clase entity Usuario mayuscula y singular le damos un alias 
 *  u.username =?1	?1 => es igual al signo  parametro una varaible que despues se va a remplazar por el
 *  valor de usarname por el patametro  si tenemos mas parmetro con el and u.otro=?2 =>  ya corresponde al segundo
 *  argumento 
 *  @Query("select u from  Usuario u where u.username=?1 ")*/


public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public  Usuario findByUsername(String  username);
	
	@Query("select u from Usuario u where  u.username=?1")
	public  Usuario findByUsername2(String  username);

}
