package org.cibertec.edu.pe.interfaces;

import org.cibertec.edu.pe.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoria extends JpaRepository<Categoria, Integer>{

}
