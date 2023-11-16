package org.cibertec.edu.pe.controller;

import java.util.List;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.model.Producto;
import org.cibertec.edu.pe.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"login","carrito","subtotal","desc","total"})
public class IndexController {
	
	@ModelAttribute("login")
	public String getLogin() {
		return null;
	}
	
	
	
	
	@Autowired
	private IProductoService servicioProd;
	
	@GetMapping("/index")
	public String Listar(Model m) {
		List<Producto> lista = servicioProd.Listado();
		m.addAttribute("productos", lista);
		
		//Usuario logeado o no
		String usuario = (String)m.getAttribute("login");
		m.addAttribute("login", usuario);
		//Cargar lista categorias
		return "index"; //index.html
	}
	
	@GetMapping("/deslogear")
	public String Deslogear(Model m) {
		m.addAttribute("login", null);
		return "index";
	}
}
