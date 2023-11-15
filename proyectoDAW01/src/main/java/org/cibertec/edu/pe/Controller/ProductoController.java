package org.cibertec.edu.pe.controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.interfaces.IProducto;
import org.cibertec.edu.pe.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping
public class ProductoController {
	@Autowired
	private IProductoService servicio;
	
	//Metodo para Listar
	@GetMapping("/index")
	public String Listar(Model m) {
		List<Producto> lista = servicio.Listado();
		m.addAttribute("producto", lista);
		return "index"; //productos.html
	}
	
	
	
	
	  @GetMapping("/ver/{id}")
	//Metodo para Buscar
	public String ver(@PathVariable int id, Model m) {
		Optional<Producto> p = servicio.Buscar(id);
		m.addAttribute("producto", p);
		return "view"; //editar.html
	}
	
	
	@GetMapping("/nuevo")
	//Metodo para Agregar
	public String agregar(Model m) {
		m.addAttribute("producto", new Producto());
		return "editar"; //editar.html
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable int id, Model m) {
		Optional<Producto> p = servicio.Buscar(id);
		m.addAttribute("producto", p);
		return "editar"; //editar.html
	}
	
	@PostMapping("/salvar")
	//Metodo para grabar
	public String salvar(Producto p, Model m) {
		servicio.Grabar(p);
		return "redirect:/productos";
	}
	
	@GetMapping("/eliminar/{id}")
	//Metodo para suprimir
	public String eliminar(@PathVariable int id, Model m) {
		servicio.Suprimir(id);		
		return "redirect:/productos";
	}
}
