package org.cibertec.edu.pe.interfaceServices;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.model.Usuario;

public interface IUsuarioService {
	public List<Usuario> Listado();
	public Optional<Usuario> Buscar(int id);
	public int Grabar(Usuario ObjU);
	public void Suprimir(int id);
}
