package org.cibertec.edu.pe.interfaces;

import org.cibertec.edu.pe.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProducto extends JpaRepository<Producto, Integer>{

}
