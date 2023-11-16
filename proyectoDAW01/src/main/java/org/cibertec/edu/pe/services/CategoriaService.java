package org.cibertec.edu.pe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.ICategoriaService;
import org.cibertec.edu.pe.interfaces.ICategoria;
import org.cibertec.edu.pe.model.Categoria;

@Service
public class CategoriaService implements ICategoriaService{

@Autowired
private ICategoria data;

@Override
public List<Categoria> Listado() {
	
	return (List<Categoria>)data.findAll();
}

@Override
public Optional<Categoria> Buscar(int id) {
	
	return data.findById(id);
}

@Override
public int Grabar(Categoria ObjC) {
	
	int rpta = 0;
	Categoria p = data.save(ObjC);
	if(!p.equals(null))
		rpta=1;
	return rpta;
}

@Override
public void Suprimir(int id) {
	
	data.deleteById(id);
}

}