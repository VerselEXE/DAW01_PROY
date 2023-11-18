package org.cibertec.edu.pe.controller;

import java.util.ArrayList;
import java.util.List;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.interfaces.IDetalle;
import org.cibertec.edu.pe.interfaces.IVenta;
import org.cibertec.edu.pe.model.Detalle;
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
	//SESIONES
	@ModelAttribute("login")
	public String getLogin() {
		return null;
	}	
	@ModelAttribute("carrito")
	public List<Detalle> getCarrito(){
		return new ArrayList<>();
	}	
	@ModelAttribute("subtotal")
	public double getSubtotal() {
		return 0.0;
	}	
	@ModelAttribute("desc")
	public double getDesc() {
		return 0.0;
	}	
	@ModelAttribute("total")
	public double getTotal() {
		return 0.0;
	}
	
	//REPOSITORIOS
	@Autowired
	private IProductoService servicioProd;
	@Autowired
	private IVenta ventaRepo;
	@Autowired
	private IDetalle detalleRepo;
	
	
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
