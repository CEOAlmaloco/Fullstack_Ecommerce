package com.ampuero.msvc.contenido.services;

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
     * @param s3Key Referencia a S3 guardada en BD (ej: "img/blog.jpg")
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
}

