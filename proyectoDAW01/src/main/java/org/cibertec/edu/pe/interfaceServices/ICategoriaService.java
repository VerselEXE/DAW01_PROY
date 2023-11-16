package org.cibertec.edu.pe.interfaceServices;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.model.Categoria;

public interface ICategoriaService {
	public List<Categoria> Listado();
	public Optional<Categoria> Buscar(int id);
	public int Grabar(Categoria ObjC);
	public void Suprimir(int id);

}
