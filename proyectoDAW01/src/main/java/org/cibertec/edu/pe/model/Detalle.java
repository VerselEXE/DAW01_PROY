package org.cibertec.edu.pe.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detalle")
public class Detalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDetalle;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProd")
	private Producto producto;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "idVenta")
	private int idVenta;
	
	private int cantidad;
	private double subtotal;
	
	public Detalle() {
	}

	public Detalle(int idDetalle, Producto producto, int idVenta, int cantidad, double subtotal) {
		this.idDetalle = idDetalle;
		this.producto = producto;
		this.idVenta = idVenta;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
	}

	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}			
}
