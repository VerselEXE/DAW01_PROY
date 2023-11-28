package org.cibertec.edu.pe.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

//Reporte
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.interfaces.IDetalle;
import org.cibertec.edu.pe.interfaces.IProducto;
import org.cibertec.edu.pe.interfaces.IUsuario;
import org.cibertec.edu.pe.interfaces.IVenta;
import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Producto;
import org.cibertec.edu.pe.model.Usuario;
import org.cibertec.edu.pe.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"login","idLogeo","carrito","subtotal","desc","total"})
public class IndexController {
	//SESIONES
	@ModelAttribute("login")
	public String getLogin() {
		return null;
	}
	@ModelAttribute("idLogeo")
	public int getIdLogeo() {
		return 0;
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
	@Autowired
	private IUsuario usRepo;
	
	
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
	public String Deslogear(RedirectAttributes r, Model m) {		
		r.addFlashAttribute("login",null);
		r.addFlashAttribute("idLogeo", 0);
		m.addAttribute("login",null);
		m.addAttribute("idLogeo", 0);
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
	
	@PostMapping("/actualizarCarrito")
	public String actualizarCarrito(Model model) {
		
		double subtotal = (double)model.getAttribute("subtotal");		
		double descuento = 0.0;
		double total = 0.0;				
						
		//Si el subtotal es menor a 1500 no habrá descuento
		if(subtotal>=1500 && subtotal<=2500) {
			descuento = 0.025*subtotal;
		}else if(subtotal>2500 && subtotal<=3500){
			descuento = 0.035*subtotal;
		}else if(subtotal>3500 && subtotal<=4500) {
			descuento = 0.045*subtotal;
		}else if(subtotal>4500) {
			descuento = 0.055*subtotal;
		}		
		
		descuento = Math.floor(descuento*100)/100;		
		total = subtotal-descuento;
		total = Math.floor(total*100)/100;
		
		model.addAttribute("subtotal", subtotal);		
		model.addAttribute("desc", descuento);
		model.addAttribute("total", total);		
		
		return "carrito";
	}
	
	@GetMapping("/eliminarCarrito/{idProd}")
	public String eliminar(Model model,@PathVariable(name="idProd",required = true) int idProd) {
		int index=0;		
		double costoQuitado,nuevoSubTotal=0.0;
		List<Detalle> carrito = (List<Detalle>)model.getAttribute("carrito");
		for(Detalle d : carrito) {
			if(d.getProducto().getIdProd() != idProd) {
				index++;
			}else break;
		}
		costoQuitado = carrito.get(index).getSubtotal();
		nuevoSubTotal = (double)model.getAttribute("subtotal")-costoQuitado;
		carrito.remove(index);		
		model.addAttribute("carrito", carrito);
		model.addAttribute("subtotal", nuevoSubTotal);
		
		return "redirect:/carrito";
	}
	
	@GetMapping("/pagar")
	public String pagar(Model m) {
		String usuario = (String)m.getAttribute("login");
		int idLogeo = (int)m.getAttribute("idLogeo");
		
		
		if(usuario!=null) {
			int tamano;
			double montoTotal = (double)m.getAttribute("total");
			List<Detalle> carrito = (List<Detalle>)m.getAttribute("carrito");			
			
			tamano = (int)ventaRepo.count()+1;
			
			for(Detalle d : carrito) {
				d.setIdVenta(tamano);
				detalleRepo.save(d);		
			}			
									
			Venta venta = new Venta(tamano, new Date(), montoTotal, idLogeo);
			ventaRepo.save(venta);
			return "redirect:/index";
		}else
			return "redirect:/login";
	}
	
	//View del carrito
	@GetMapping("/carrito")
	public String carrito() {
		return "carrito";
	}
	@GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros"; 
	}
	
	@GetMapping("/login")
	public String login(Model m) {
		m.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	@PostMapping("/verificarLog")
	public String verificarLog(Usuario u, Model m) {
		Usuario usuarioBuscado = new Usuario();		
		List<Usuario> uL = usRepo.findAll();
		String vista = "login";
		boolean existe = false;
		boolean correcto = false;
		
		for(Usuario item:uL) {
			if(u.getCorreoUs().matches(item.getCorreoUs()) &&
			   u.getPassUs().matches(item.getPassUs())) {
				existe = true;
				correcto = true;
				usuarioBuscado = item;
				break;
			}
			else if(u.getCorreoUs().matches(item.getCorreoUs()) &&
					   !u.getPassUs().matches(item.getPassUs())) {
				existe = true;
			}
		}
		if(existe && correcto) {
			m.addAttribute("login",usuarioBuscado.getNombreUs());
			m.addAttribute("idLogeo",usuarioBuscado.getIdUs());
			if(usuarioBuscado.getIdCatUs()==1)
				vista = "gestionCrud";
			else
				vista = "redirect:/index";
		}else if(existe) {
			//mostrar mensaje de error en el login
			m.addAttribute("ooo", "aaa");			
		}
			
		return vista;
	}		
	
	@GetMapping("/gestionCrud")
	public String gestionCrud(Model m) {
		return "gestionCrud";
	}
	
	//REPORTE JASPER
	@GetMapping("/reporte")
	@ResponseBody
    public ResponseEntity<InputStreamResource> reporte() throws IOException {
        File file = new File("C:\\informes jasper\\ReporteVentas.pdf");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }
	
	@Autowired
    private DataSource dataSource;

    @Transactional
    public void generateReport() {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            
            JasperReport report = JasperCompileManager.compileReport("C:\\informes jasper\\ReporteProyectoDAW1.jrxml");

            JasperPrint print = JasperFillManager.fillReport(report, null, conn);

            // Exporta el informe a PDF
            JasperExportManager.exportReportToPdfFile(print, "C:\\informes jasper\\ReporteVentas.pdf");            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
