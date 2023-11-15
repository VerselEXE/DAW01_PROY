package org.cibertec.edu.pe.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.interfaceServices.IProductoService;
import org.cibertec.edu.pe.model.Producto;

@Service
public class ProductoService implements IProductoService{

	@Override
	public List<Producto> Listado() {
		
		return null;
	}

	@Override
	public Optional<Producto> Buscar(int id) {
		
		return Optional.empty();
	}

	@Override
	public int Grabar(Producto ObjP) {
		
		return 0;
	}

	@Override
	public void Suprimir(int id) {
		
		
	}
	
}
