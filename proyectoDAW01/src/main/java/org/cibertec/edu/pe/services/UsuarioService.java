package org.cibertec.edu.pe.services;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.IUsuarioService;
import org.cibertec.edu.pe.interfaces.IUsuario;
import org.cibertec.edu.pe.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService{
	
	@Autowired
	private IUsuario data;
	
	@Override
	public List<Usuario> Listado() {
		
		return (List<Usuario>)data.findAll();
	}

	@Override
	public Optional<Usuario> Buscar(int id) {
		
		return data.findById(id);
	}

	@Override
	public int Grabar(Usuario ObjU) {
		int rpta = 0;
		Usuario user = data.save(ObjU);
		if(!user.equals(null))
			rpta=1;
		return rpta;
	}

	@Override
	public void Suprimir(int id) {
		
		data.deleteById(id);
	}
	
}
