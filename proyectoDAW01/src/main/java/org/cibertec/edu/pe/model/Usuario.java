package org.cibertec.edu.pe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUs;
	private String nombreUs;
	private String correoUs;
	private String passUs;
	private int idCatUs;
	
	public Usuario() {
	}

	public Usuario(int idCatUs) {
		this.idCatUs = idCatUs;
	}

	public Usuario(int idUs, String nombreUs, String correoUs, String passUs, int idCatUs) {
		this.idUs = idUs;
		this.nombreUs = nombreUs;
		this.correoUs = correoUs;
		this.passUs = passUs;
		this.idCatUs = idCatUs;
	}

	public int getIdUs() {
		return idUs;
	}

	public void setIdUs(int idUs) {
		this.idUs = idUs;
	}

	public String getNombreUs() {
		return nombreUs;
	}

	public void setNombreUs(String nombreUs) {
		this.nombreUs = nombreUs;
	}

	public String getCorreoUs() {
		return correoUs;
	}

	public void setCorreoUs(String correoUs) {
		this.correoUs = correoUs;
	}

	public String getPassUs() {
		return passUs;
	}

	public void setPassUs(String passUs) {
		this.passUs = passUs;
	}

	public int getIdCatUs() {
		return idCatUs;
	}

	public void setIdCatUs(int idCatUs) {
		this.idCatUs = idCatUs;
	}		
}
