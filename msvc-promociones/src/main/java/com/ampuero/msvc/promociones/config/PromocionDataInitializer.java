package com.ampuero.msvc.promociones.config;

import com.ampuero.msvc.promociones.models.Promocion;
import com.ampuero.msvc.promociones.repositories.PromocionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class PromocionDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(PromocionDataInitializer.class);

    @Bean
    @Order(1)
    CommandLineRunner initPromociones(PromocionRepository promocionRepository) {
        return args -> {
            logger.info("Inicializando promociones por defecto");
            crearPromocionSiNoExiste(promocionRepository,
                    "CONSOLA5",
                    "Descuento 5% Consolas",
                    "Promoción bienvenida con 5% de descuento en consolas",
                    "PORCENTAJE",
                    5.0,
                    "CO",
                    false);

            crearPromocionSiNoExiste(promocionRepository,
                    "PERI10",
                    "Periféricos 10% OFF",
                    "Accesorios seleccionados con 10% de descuento",
                    "PORCENTAJE",
                    10.0,
                    "PE",
                    false);

            crearPromocionSiNoExiste(promocionRepository,
                    "DUOC15",
                    "Especial Duoc 15%",
                    "Beneficio exclusivo para estudiantes DUOC",
                    "PORCENTAJE",
                    15.0,
                    null,
                    true);
        };
    }

    private void crearPromocionSiNoExiste(PromocionRepository promocionRepository,
                                          String codigo,
                                          String nombre,
                                          String descripcion,
                                          String tipoDescuento,
                                          Double valorDescuento,
                                          String categoriaAplicable,
                                          boolean aplicableDuoc) {
        if (promocionRepository.existsByCodigoPromocion(codigo)) {
            logger.debug("Promoción {} ya existe, se omite creación", codigo);
            return;
        }

        Promocion promocion = new Promocion();
        promocion.setCodigoPromocion(codigo);
        promocion.setNombrePromocion(nombre);
        promocion.setDescripcionPromocion(descripcion);
        promocion.setTipoDescuento(tipoDescuento);
        promocion.setValorDescuento(valorDescuento);
        promocion.setMontoMinimo(0.0);
        promocion.setMontoMaximoDescuento(null);
        promocion.setFechaInicio(LocalDateTime.now().minusDays(7));
        promocion.setFechaFin(LocalDateTime.now().plusDays(90));
        promocion.setUsosMaximos(null);
        promocion.setUsosActuales(0);
        promocion.setUsosPorUsuario(null);
        promocion.setActivo(true);
        promocion.setAplicableDuoc(aplicableDuoc);
        promocion.setCategoriaAplicable(categoriaAplicable);
        promocion.setPuntosRequeridos(0);
        promocion.setTipoPromocion("DESCUENTO");

        promocionRepository.save(promocion);
        logger.info("Promoción {} creada", codigo);
    }
}
