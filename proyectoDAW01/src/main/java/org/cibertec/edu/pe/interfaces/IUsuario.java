package org.cibertec.edu.pe.interfaces;

import org.cibertec.edu.pe.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Integer>{

}
