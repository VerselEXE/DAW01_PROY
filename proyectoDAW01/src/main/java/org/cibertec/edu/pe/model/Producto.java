package org.cibertec.edu.pe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="producto")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProd;
	private int idCat;	
	private String nombreProd;
	private double precioProd;
	private int stockProd;
	private String descProd;
	private String imagen;
	
	@ManyToOne
	private Usuario usuario;
	
	public Producto() {		
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public Producto(int idProd, int idCat, String nombreProd, double precioProd, int stockProd, String descProd,
			String imagen, Usuario usuario) {
		this.idProd = idProd;
		this.idCat = idCat;
		this.nombreProd = nombreProd;
		this.precioProd = precioProd;
		this.stockProd = stockProd;
		this.descProd = descProd;
		this.imagen = imagen;
		this.usuario = usuario;
	}



	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public int getIdCat() {
		return idCat;
	}

	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}

	public String getNombreProd() {
		return nombreProd;
	}

	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}

	public double getPrecioProd() {
		return precioProd;
	}

	public void setPrecioProd(double precioProd) {
		this.precioProd = precioProd;
	}

	public int getStockProd() {
		return stockProd;
	}

	public void setStockProd(int stockProd) {
		this.stockProd = stockProd;
	}

	public String getDescProd() {
		return descProd;
	}

	public void setDescProd(String descProd) {
		this.descProd = descProd;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}		
}
