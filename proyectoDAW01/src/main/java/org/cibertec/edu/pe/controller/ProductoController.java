package org.cibertec.edu.pe.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.model.Producto;
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
	@Autowired
	private IProductoService servicio;
	
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
		return "editar"; //editar.html
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable int id, Model m) {
	    Optional<Producto> p = servicio.Buscar(id);
	    m.addAttribute("producto", p.orElse(new Producto()));
	    m.addAttribute("timestamp", System.currentTimeMillis());
	    return "editar"; //editar.html
	}
	
	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Producto p, @RequestParam("img") MultipartFile file, Model m) {
	    // Guardar la imagen en la carpeta img
	    if (!file.isEmpty()) {
	        try {
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get("src/main/resources/static/img/" + file.getOriginalFilename());
	            Files.write(path, bytes);
	            p.setImagen(file.getOriginalFilename());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    servicio.Grabar(p);
	    return "redirect:/producto";
	}
	
	@GetMapping("/eliminar/{id}")
	//Metodo para suprimir
	public String eliminar(@PathVariable int id, Model m) {
		servicio.Suprimir(id);		
		return "redirect:/producto";
	}
}
