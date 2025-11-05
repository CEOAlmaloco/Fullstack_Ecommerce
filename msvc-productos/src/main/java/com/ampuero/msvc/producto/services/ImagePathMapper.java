package com.ampuero.msvc.producto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para mapear rutas de imágenes incorrectas a rutas correctas
 * 
 * Este servicio resuelve el problema de rutas en la base de datos que no coinciden
 * con los archivos reales en resources/static/img/
 */
@Service
public class ImagePathMapper {

    private static final Logger logger = LoggerFactory.getLogger(ImagePathMapper.class);

    // Mapeo de rutas incorrectas a rutas correctas
    private static final Map<String, String> PATH_MAP = new HashMap<>();

    static {
        // Consolas
        PATH_MAP.put("img/consolas/4.png", "img/play5white.png");
        PATH_MAP.put("img/consolas/1.png", "img/play5white.png");
        PATH_MAP.put("img/consolas/2.png", "img/play4.png");
        PATH_MAP.put("img/consolas/3.png", "img/mandoplay.png");
        PATH_MAP.put("img/consolas/5.png", "img/mandoplayazul.png");
        PATH_MAP.put("img/consolas/6.png", "img/audifonoazul.png");
        
        // Polerones
        PATH_MAP.put("img/polerones/1.png", "img/poleronstarcraf.png");
        PATH_MAP.put("img/polerones/2.png", "img/poleronpapa.png");
        PATH_MAP.put("img/polerones/3.png", "img/poleronthekin.png");
        PATH_MAP.put("img/polerones/4.png", "img/poleronplay.png");
        PATH_MAP.put("img/polerones/5.png", "img/stumblepoleron.png");
        PATH_MAP.put("img/polerones/6.png", "img/poleronstars.png");
        
        // Periféricos
        PATH_MAP.put("img/perifericos/1.png", "img/audilogitech.png");
        PATH_MAP.put("img/perifericos/2.png", "img/teclado_rd_rgb.png");
        PATH_MAP.put("img/perifericos/3.png", "img/mousecougar.png");
        PATH_MAP.put("img/perifericos/4.png", "img/monitorasus.png");
        PATH_MAP.put("img/perifericos/5.png", "img/webcamlogitech.png");
        PATH_MAP.put("img/perifericos/6.png", "img/micrologitech.png");
        
        // Entretenimiento
        PATH_MAP.put("img/entretenimiento/catan.png", "img/catan.png");
        PATH_MAP.put("img/entretenimiento/carcassone.png", "img/carcassone.png");
    }

    /**
     * Mapea una ruta incorrecta a la ruta correcta
     * 
     * @param imagePath Ruta de imagen (puede tener prefijo ./)
     * @return Ruta correcta o la ruta original si no hay mapeo
     */
    public String mapImagePath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return imagePath;
        }

        // Limpiar la ruta: remover prefijo ./ si existe
        String rutaLimpia = imagePath;
        if (rutaLimpia.startsWith("./")) {
            rutaLimpia = rutaLimpia.substring(2);
        }
        rutaLimpia = rutaLimpia.trim();

        // Buscar en el mapeo
        String rutaMapeada = PATH_MAP.get(rutaLimpia);
        
        if (rutaMapeada != null) {
            logger.debug("Ruta mapeada: {} -> {}", rutaLimpia, rutaMapeada);
            return rutaMapeada;
        }

        // Si no hay mapeo, retornar la ruta original (limpia)
        logger.debug("No se encontró mapeo para: {}, usando ruta original", rutaLimpia);
        return rutaLimpia;
    }

    /**
     * Mapea un array de rutas de imágenes
     * 
     * @param imagePaths Array de rutas de imágenes
     * @return Array de rutas mapeadas
     */
    public String[] mapImagePaths(String[] imagePaths) {
        if (imagePaths == null || imagePaths.length == 0) {
            return imagePaths;
        }

        String[] rutasMapeadas = new String[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            rutasMapeadas[i] = mapImagePath(imagePaths[i]);
        }
        return rutasMapeadas;
    }
}

