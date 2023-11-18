package org.cibertec.edu.pe.controller;

import java.util.ArrayList;
import java.util.List;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.interfaces.IDetalle;
import org.cibertec.edu.pe.interfaces.IProducto;
import org.cibertec.edu.pe.interfaces.IVenta;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Producto;
import org.cibertec.edu.pe.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"login","carrito","subtotal","desc","total"})
public class IndexController {
	//SESIONES
	@ModelAttribute("login")
	public String getLogin() {
		return "usuario1";
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
	private IProducto prodRepo;
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
		//Cargar productos
		return "index"; //index.html
	}
	
	@GetMapping("/deslogear")
	public String Deslogear(RedirectAttributes r) {		
		r.addFlashAttribute("login",null);
		return "redirect:/index";
	}
	
	@GetMapping("/agregar/{idProd}")
	public String agregar(Model model, @PathVariable(name="idProd",required=true) int idProd) {
		Producto p = prodRepo.findById(idProd).orElse(null);
		List<Detalle> carrito = (List<Detalle>)model.getAttribute("carrito");
		double total = 0.0;		
		boolean existe = false;
		Detalle detalle = new Detalle();
		//Jalamos el producto
		if(p!=null) {
			detalle.setProducto(p);
			detalle.setCantidad(1);
			detalle.setSubtotal(detalle.getProducto().getPrecioProd()*detalle.getCantidad());
		}
		//Si el carro está vacío
		if(carrito.size()==0)
			carrito.add(detalle);
		else {
			for(Detalle d:carrito) {
				if(d.getProducto().getIdProd() == p.getIdProd()) {
					d.setCantidad(d.getCantidad()+1);
					d.setSubtotal(d.getProducto().getPrecioProd()*d.getCantidad());
					existe=true;
				}
			}
			if(!existe)carrito.add(detalle);
		}
		for(Detalle d:carrito)total+=d.getSubtotal();
		
		//Pasar valores a la vista
		model.addAttribute("total",total);
		model.addAttribute("subtotal",total);
		model.addAttribute("carrito",carrito);
		return "redirect:/index";
	}
	
	//View del carrito
	@GetMapping("/carrito")
	public String carrito() {
		return "carrito";
	}
}
