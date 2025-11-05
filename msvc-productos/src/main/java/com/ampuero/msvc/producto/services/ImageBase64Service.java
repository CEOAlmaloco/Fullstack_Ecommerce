package com.ampuero.msvc.producto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Servicio para manejar conversión de imágenes a Base64 y viceversa
 * Las imágenes se almacenan en Base64 en la base de datos H2
 * El frontend y la app de Kotlin desencriptan/decodifican el Base64 para mostrar las imágenes
 */
@Service
public class ImageBase64Service {

    private static final Logger logger = LoggerFactory.getLogger(ImageBase64Service.class);

    /**
     * Convierte una imagen desde un archivo en resources/static a Base64
     * 
     * @param imagePath Ruta relativa desde resources/static (ej: "img/play5white.png")
     * @return String Base64 con prefijo data:image o null si hay error
     */
    public String convertImageToBase64(String imagePath) {
        try {
            // Limpiar la ruta: remover prefijo ./ si existe
            String rutaLimpia = imagePath;
            if (rutaLimpia.startsWith("./")) {
                rutaLimpia = rutaLimpia.substring(2);
            }
            // Remover espacios en blanco
            rutaLimpia = rutaLimpia.trim();
            
            // Construir la ruta completa desde resources/static
            String fullPath = "static/" + rutaLimpia;
            ClassPathResource resource = new ClassPathResource(fullPath);

            if (!resource.exists()) {
                logger.warn("Imagen no encontrada: {} (ruta completa: {})", imagePath, fullPath);
                return null;
            }

            byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
            String base64 = Base64.getEncoder().encodeToString(imageBytes);

            // Determinar el tipo MIME basado en la extensión
            String mimeType = getMimeType(rutaLimpia);
            
            // Retornar con prefijo data:image para que el frontend pueda usarlo directamente
            return "data:" + mimeType + ";base64," + base64;
        } catch (IOException e) {
            logger.error("Error al convertir imagen a Base64: {}", imagePath, e);
            return null;
        }
    }

    /**
     * Convierte un array de rutas de imágenes a un JSON array de Base64
     * 
     * @param imagePaths Array de rutas de imágenes
     * @return JSON array como String con las imágenes en Base64
     */
    public String convertImagesArrayToBase64Json(String[] imagePaths) {
        if (imagePaths == null || imagePaths.length == 0) {
            return "[]";
        }

        StringBuilder jsonArray = new StringBuilder("[");
        for (int i = 0; i < imagePaths.length; i++) {
            String base64 = convertImageToBase64(imagePaths[i]);
            if (base64 != null) {
                if (i > 0) {
                    jsonArray.append(",");
                }
                jsonArray.append("\"").append(base64).append("\"");
            }
        }
        jsonArray.append("]");
        return jsonArray.toString();
    }

    /**
     * Decodifica un Base64 a bytes (para uso interno si es necesario)
     * 
     * @param base64String String Base64 con o sin prefijo data:image
     * @return Array de bytes de la imagen
     */
    public byte[] decodeBase64ToBytes(String base64String) {
        try {
            // Remover prefijo data:image si existe
            String base64Data = base64String;
            if (base64String.contains(",")) {
                base64Data = base64String.split(",")[1];
            }

            return Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            logger.error("Error al decodificar Base64", e);
            return null;
        }
    }

    /**
     * Obtiene el tipo MIME basado en la extensión del archivo
     * 
     * @param imagePath Ruta del archivo
     * @return Tipo MIME (ej: "image/png", "image/jpeg")
     */
    private String getMimeType(String imagePath) {
        String lowerPath = imagePath.toLowerCase();
        if (lowerPath.endsWith(".png")) {
            return "image/png";
        } else if (lowerPath.endsWith(".jpg") || lowerPath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerPath.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerPath.endsWith(".webp")) {
            return "image/webp";
        } else if (lowerPath.endsWith(".svg")) {
            return "image/svg+xml";
        }
        // Por defecto, asumir PNG
        return "image/png";
    }
}

