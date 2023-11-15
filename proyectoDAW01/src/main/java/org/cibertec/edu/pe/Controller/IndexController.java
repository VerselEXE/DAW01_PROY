package org.cibertec.edu.pe.Controller;

import java.util.List;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"carrito","subtotal","desc","total"})
public class IndexController {
	@Autowired
	private IProductoService servicioProd;
	
	@GetMapping("/index")
	public String Listar(Model m) {
		List<Producto> lista = servicioProd.Listado();
		m.addAttribute("productos", lista);
		//Cargar lista categorias
		return "index"; //index.html
	}
	
	
}
