package org.cibertec.edu.pe.interfaceServices;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.model.Producto;

public interface IProductoService {
	public List<Producto> Listado();
	public Optional<Producto> Buscar(int id);
	public int Grabar(Producto ObjP);
	public void Suprimir(int id);
}
