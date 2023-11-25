package org.cibertec.edu.pe.controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.ICategoriaService;
import org.cibertec.edu.pe.model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoriaController {

	@Autowired
	private ICategoriaService servicio;
	
	//Metodo para Listar
	@GetMapping("/categoria")
	public String Listar(Model m) {
		List<Categoria> lista = servicio.Listado();
		m.addAttribute("categoria", lista);
		return "listarCat"; //categoria.html
	}
	
	  @GetMapping("/verCat/{id}")
	//Metodo para Buscar
	public String ver(@PathVariable int id, Model m) {
		Optional<Categoria> p = servicio.Buscar(id);
		m.addAttribute("categoria", p);
		return "viewCat"; //editar.html
	}
	
	
	@GetMapping("/crearCat")
	//Metodo para Agregar
	public String agregar(Model m) {
		m.addAttribute("categoria", new Categoria());
		return "crearCat"; //editar.html
	}
	
	@GetMapping("/editarCat/{id}")
	public String editar(@PathVariable int id, Model m) {
		Optional<Categoria> p = servicio.Buscar(id);
		m.addAttribute("categoria", p);
		return "editarCat"; //editar.html
	}
	
	@PostMapping("/salvarCat")
	//Metodo para grabar
	public String salvar(Categoria p, Model m) {
		servicio.Grabar(p);
		return "redirect:/categoria";
	}
	
	@GetMapping("/eliminarCat/{id}")
	//Metodo para suprimir
	public String eliminar(@PathVariable int id, Model m) {
		servicio.Suprimir(id);		
		return "redirect:/categoria";
	}
}
