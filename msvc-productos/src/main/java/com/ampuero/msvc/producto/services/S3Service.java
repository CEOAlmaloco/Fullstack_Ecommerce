package com.ampuero.msvc.producto.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio para construir URLs de S3 a partir de referencias guardadas en BD
 * Las imágenes se almacenan en S3 y solo se guarda la referencia (key) en la BD
 */
@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    @Value("${s3.bucket.name:levelup-gamer-products}")
    private String bucketName;

    @Value("${s3.region:us-east-1}")
    private String region;

    @Value("${s3.base.url:}")
    private String baseUrl;

    /**
     * Construye la URL completa de S3 a partir de una referencia (key)
     * 
     * @param s3Key Referencia a S3 guardada en BD (ej: "productos/123/imagen.jpg")
     * @return URL completa de S3 o null si la key es null/vacía
     */
    public String buildS3Url(String s3Key) {
        if (s3Key == null || s3Key.isEmpty()) {
            return null;
        }

        // Si ya es una URL completa (http/https), retornarla directamente
        if (s3Key.startsWith("http://") || s3Key.startsWith("https://")) {
            return s3Key;
        }

        // Si es Base64, retornarlo directamente (compatibilidad con datos existentes)
        if (s3Key.startsWith("data:image")) {
            return s3Key;
        }

        // Si hay una baseUrl configurada, usarla
        if (baseUrl != null && !baseUrl.isEmpty()) {
            // Remover trailing slash si existe
            String cleanBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
            // Remover leading slash de la key si existe
            String cleanKey = s3Key.startsWith("/") ? s3Key.substring(1) : s3Key;
            return cleanBaseUrl + "/" + cleanKey;
        }

        // Construir URL estándar de S3
        // Formato: https://{bucket}.s3.{region}.amazonaws.com/{key}
        String cleanKey = s3Key.startsWith("/") ? s3Key.substring(1) : s3Key;
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, cleanKey);
    }

    /**
     * Construye múltiples URLs de S3 a partir de un array de referencias
     * 
     * @param s3Keys Array de referencias a S3 (ej: ["productos/123/img1.jpg", "productos/123/img2.jpg"])
     * @return Array de URLs completas de S3
     */
    public String[] buildS3Urls(String[] s3Keys) {
        if (s3Keys == null || s3Keys.length == 0) {
            return new String[0];
        }

        String[] urls = new String[s3Keys.length];
        for (int i = 0; i < s3Keys.length; i++) {
            urls[i] = buildS3Url(s3Keys[i]);
        }
        return urls;
    }

    /**
     * Construye múltiples URLs de S3 a partir de un JSON array de referencias
     * 
     * @param s3KeysJson JSON array de referencias a S3 (ej: '["productos/123/img1.jpg","productos/123/img2.jpg"]')
     * @return JSON array de URLs completas de S3
     */
    public String buildS3UrlsJson(String s3KeysJson) {
        if (s3KeysJson == null || s3KeysJson.isEmpty()) {
            return "[]";
        }

        try {
            // Parsear JSON array
            String cleanJson = s3KeysJson.trim();
            if (cleanJson.startsWith("[")) {
                cleanJson = cleanJson.substring(1);
            }
            if (cleanJson.endsWith("]")) {
                cleanJson = cleanJson.substring(0, cleanJson.length() - 1);
            }

            // Si está vacío después de limpiar, retornar array vacío
            if (cleanJson.trim().isEmpty()) {
                return "[]";
            }

            // Dividir por comas
            String[] keys = cleanJson.split(",\\s*");
            StringBuilder jsonArray = new StringBuilder("[");
            
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i].trim();
                // Remover comillas si existen
                if (key.startsWith("\"") && key.endsWith("\"")) {
                    key = key.substring(1, key.length() - 1);
                }
                
                String url = buildS3Url(key);
                if (url != null) {
                    if (i > 0) {
                        jsonArray.append(",");
                    }
                    jsonArray.append("\"").append(url).append("\"");
                }
            }
            
            jsonArray.append("]");
            return jsonArray.toString();
        } catch (Exception e) {
            logger.error("Error al construir URLs de S3 desde JSON: {}", s3KeysJson, e);
            return "[]";
        }
    }

    /**
     * Extrae la referencia (key) de S3 desde una URL completa
     * 
     * @param s3Url URL completa de S3 (ej: "https://bucket.s3.region.amazonaws.com/productos/123/imagen.jpg")
     * @return Referencia (key) de S3 (ej: "productos/123/imagen.jpg") o la URL original si no es una URL de S3
     */
    public String extractS3Key(String s3Url) {
        if (s3Url == null || s3Url.isEmpty()) {
            return null;
        }

        // Si es Base64, retornarlo directamente
        if (s3Url.startsWith("data:image")) {
            return s3Url;
        }

        // Si no es una URL, retornarlo como está (ya es una key)
        if (!s3Url.startsWith("http://") && !s3Url.startsWith("https://")) {
            return s3Url;
        }

        // Extraer la key de la URL de S3
        try {
            java.net.URI uri = java.net.URI.create(s3Url);
            String path = uri.getPath();
            // Remover leading slash
            return path.startsWith("/") ? path.substring(1) : path;
        } catch (Exception e) {
            logger.error("Error al extraer key de URL de S3: {}", s3Url, e);
            return s3Url;
        }
    }
}

