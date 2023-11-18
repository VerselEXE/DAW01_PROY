package org.cibertec.edu.pe.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.cibertec.edu.pe.controller.ProductoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

	
private String folder="src/main/resources/static/img/";
	
public String saveImage(MultipartFile file) {
    try {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
    } catch (IOException e) {
        LOGGER.error("Error de E/S al intentar guardar la imagen", e);
    }

    return "default.jpg";
}

	
	public void deleteImage(String nombre) {
		String ruta="src/main/resources/static/img/";
		File file=new File(ruta+nombre);
		file.delete();
	}
}
