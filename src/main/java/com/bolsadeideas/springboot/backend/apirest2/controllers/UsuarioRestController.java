package com.bolsadeideas.springboot.backend.apirest2.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest2.models.entity.Role;
import com.bolsadeideas.springboot.backend.apirest2.models.entity.Usuario;
import com.bolsadeideas.springboot.backend.apirest2.models.services.IUsuarioService;

@SuppressWarnings("unused")
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	
	@GetMapping("/usuarios")
	public List<Usuario> index1(){
		return  usuarioService.findall();
		
	}
	
	@PostMapping("/registro")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario,BindingResult result  ){
		
		Usuario usuarioNew = null;
		
		
	    
		Map<String , Object> response = new HashMap<>();
		
		List<Role> roles= new ArrayList<>();
		
		
		if(result.hasErrors()) {
			
			List<String> errors  = result.getFieldErrors()
									.stream()
									.map(err -> "El campo '" + err.getField() +"'	"+err.getDefaultMessage())
									.collect(Collectors.toList());
									
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
			
		}
		
		
		try {
	        
		
			
			String passwordBcrypt = passwordEncoder.encode(usuario.getPassword());
			
	        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	        usuario.setEnabled(true);
	     //   usuario.setRoles(roles);
	        usuarioNew = usuarioService.save(usuario);
	        
	        //usuario.setPassword(passwordBcrypt);
			//usuarioNew = usuarioService.saveRole(usuario);
	} catch(DataAccessException e) {
		
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	response.put("mensaje", "El usuario ha sido creado con éxito!");
	response.put("usuario", usuarioNew);
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
		
		
	} 
	
	
	
	
	
	
	
	
	
	
	
}
