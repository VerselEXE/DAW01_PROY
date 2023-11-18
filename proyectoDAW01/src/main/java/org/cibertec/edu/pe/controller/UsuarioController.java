package org.cibertec.edu.pe.controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.IUsuarioService;
import org.cibertec.edu.pe.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
	@Autowired 
	private IUsuarioService servicio;	
	
	@GetMapping("/listarUs")
	public String Listar(Model m) {
		List<Usuario> lista = servicio.Listado();
		m.addAttribute("usuarios", lista);
		return "listarUs";
	}
	
	@GetMapping("/verUs/{id}")	
	public String ver(@PathVariable int id, Model m) {
		Optional<Usuario> u = servicio.Buscar(id);
		m.addAttribute("usuario", u);
		return "viewUs";
	}
	
	@GetMapping("/nuevoUs")	
	public String agregar(Model m) {
		m.addAttribute("usuario", new Usuario(2));
		return "formUs";
	}
	
	@GetMapping("/editarUs/{id}")
	public String editar(@PathVariable int id, Model m) {
		Optional<Usuario> u = servicio.Buscar(id);
		m.addAttribute("usuario", u);
		return "formUsUpdate";
	}
	
	@PostMapping("/salvarUs")	
	public String salvar(Usuario u, Model m) {
		servicio.Grabar(u);
		return "redirect:/listarUs";
	}
	
	@GetMapping("/eliminarUs/{id}")	
	public String eliminar(@PathVariable int id, Model m) {
		servicio.Suprimir(id);		
		return "redirect:/listarUs";
	}
}
