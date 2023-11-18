package org.cibertec.edu.pe.Repository;


import org.cibertec.edu.pe.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductoRepository  extends JpaRepository<Producto, Integer>{

}
