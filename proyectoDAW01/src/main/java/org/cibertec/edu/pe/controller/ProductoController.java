package org.cibertec.edu.pe.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.slf4j.*;
import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.model.Producto;
import org.cibertec.edu.pe.model.Usuario;
import org.cibertec.edu.pe.services.ProductoService;
import org.cibertec.edu.pe.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
//@RequestMapping
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private IProductoService servicio;
	
	@Autowired
	private UploadFileService upload;
	
	//Metodo para Listar
	@GetMapping("/producto")
	public String Listar(Model m) {
		List<Producto> lista = servicio.Listado();
		m.addAttribute("producto", lista);
		return "productos"; //productos.html
	}
	
	
	
	
	  @GetMapping("/ver/{id}")
	//Metodo para Buscar
	public String ver(@PathVariable int id, Model m) {
		Optional<Producto> p = servicio.Buscar(id);
		m.addAttribute("producto", p.orElse(new Producto()));
		return "view"; //editar.html
	}
	
	
	@GetMapping("/nuevo")
	//Metodo para Agregar
	public String agregar(Model m) {
		m.addAttribute("producto", new Producto());
		return "crear"; //crear.html
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable int id, Model m) {
	    Optional<Producto> p = servicio.Buscar(id);
	    m.addAttribute("producto", p.orElse(new Producto()));
	    m.addAttribute("timestamp", System.currentTimeMillis());
	    return "editar"; //editar.html
	}
	
	@PostMapping("/salvar")
	public String salvar(Producto p, @RequestParam("img")MultipartFile file ) throws IOException {
		LOGGER.info("este es el objeto producto{}",p);
		Usuario u= new Usuario(1, "", "", "");
		p.setUsuario(u);
		//imagen
		if (p.getIdProd() == 0) { // cuando se crea un producto
			String nombreImagen= upload.saveImage(file);
			p.setImagen(nombreImagen);
		}else {
					
		}
		servicio.Grabar(p);
	    return "redirect:/producto";
	}
	
	@PostMapping("/updateImage")
	public String updateImage(@ModelAttribute Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
			Producto p = new Producto();
			p=servicio.Buscar(producto.getIdProd()).get();
			
			if(file.isEmpty()) {
				producto.setImagen(p.getImagen());
			}else {
				if(!p.getImagen().equals("default.jpg")) {
					
				}
				String nombreImagen= upload.saveImage(file);
				producto.setImagen(nombreImagen);
			}
			producto.setUsuario(p.getUsuario());
			servicio.updateImage(producto);
		    return "redirect:/producto";
	}
		
	
	@GetMapping("/eliminar/{id}")
	//Metodo para suprimir
	public String eliminar(@PathVariable int id, Model m) {
		
		Producto p = new Producto();
		p=servicio.Buscar(id).get();
		
		//eliminar cuando no sea la imagen por defecto
				if(!p.getImagen().equals("default.jpg")) {
					upload.deleteImage(p.getImagen());
				}
				
		
		servicio.Suprimir(id);		
		return "redirect:/producto";
	}
	
	
}
