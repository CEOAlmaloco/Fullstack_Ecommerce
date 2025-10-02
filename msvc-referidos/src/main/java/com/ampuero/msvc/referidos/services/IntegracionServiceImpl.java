package com.ampuero.msvc.referidos.services;

import com.ampuero.msvc.referidos.clients.*;
import com.ampuero.msvc.referidos.entities.Referido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntegracionServiceImpl implements IntegracionService {

    @Autowired
    private AuthClientRest authClientRest;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Autowired
    private ProductoClientRest productoClientRest;

    @Autowired
    private InventarioClientRest inventarioClientRest;

    @Autowired
    private PromocionClientRest promocionClientRest;

    @Autowired
    private NotificacionClientRest notificacionClientRest;
    @Autowired
    private ReferidoService referidoService; // Para acceder a los datos del referido

    @Override
    public Boolean validarUsuarioExistente(String email, String run) {
        try {
            // Verificar en msvc-auth si el usuario ya existe
            Boolean emailExiste = authClientRest.verificarEmailExistente(email);
            Boolean runExiste = authClientRest.verificarRunExistente(run);

            return emailExiste || runExiste;
        } catch (Exception e) {
            // Si hay error en la comunicación, asumir que no existe
            return false;
        }
    }

    @Override
    public Map<String, Object> sincronizarUsuarioConAuth(Long idReferido) {
        try {
            // Obtener datos del referido
            Referido referido = referidoService.traerPorId(idReferido);

            // Crear usuario en msvc-auth
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("nombre", referido.getNombreReferido());
            usuarioData.put("apellidos", referido.getApellidosReferido());
            usuarioData.put("email", referido.getEmailReferido());
            usuarioData.put("run", referido.getRunReferido());
            usuarioData.put("nivel", referido.getNivelUsuario());
            usuarioData.put("puntosLevelup", referido.getPuntosLevelup());

            return authClientRest.crearUsuario(usuarioData);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al sincronizar con msvc-auth: " + e.getMessage());
            return error;
        }
    }

    @Override
    public Map<String, Object> actualizarPerfilUsuario(Long idReferido, String nivel) {
        try {
            Map<String, String> nivelData = new HashMap<>();
            nivelData.put("nivel", nivel);

            return usuarioClientRest.actualizarNivelUsuario(idReferido, nivelData);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al actualizar perfil: " + e.getMessage());
            return error;
        }
    }

    @Override
    public Map<String, Object> procesarCanjeProducto(Long idReferido, Long idProducto, Integer puntosRequeridos) {
        try {
            // Verificar disponibilidad del producto
            if (!verificarDisponibilidadProducto(idProducto)) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Producto no disponible");
                return error;
            }

            // Procesar canje en msvc-productos
            Map<String, Object> canjeData = new HashMap<>();
            canjeData.put("idUsuario", idReferido);
            canjeData.put("idProducto", idProducto);
            canjeData.put("puntosRequeridos", puntosRequeridos);

            return productoClientRest.procesarCanjeProducto(canjeData);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al procesar canje: " + e.getMessage());
            return error;
        }
    }

    @Override
    public Boolean verificarDisponibilidadProducto(Long idProducto) {
        try {
            return inventarioClientRest.verificarDisponibilidadProducto(idProducto, 1);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean reservarProductoParaCanje(Long idProducto) {
        try {
            Map<String, Object> resultado = inventarioClientRest.reservarProducto(idProducto, 1);
            return resultado != null && !resultado.containsKey("error");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> obtenerDescuentosDisponibles(String nivel, Integer puntos) {
        try {
            List<Map<String, Object>> promocionesNivel = promocionClientRest.obtenerPromocionesPorNivel(nivel);
            List<Map<String, Object>> promocionesPuntos = promocionClientRest.obtenerPromocionesPorPuntos(puntos);

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("promocionesNivel", promocionesNivel);
            resultado.put("promocionesPuntos", promocionesPuntos);

            return resultado;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Error al obtener descuentos: " + e.getMessage());
            return error;
        }
    }

    @Override
    public Boolean notificarReferidoExitoso(Long idReferidor, String nombreReferido) {
        try {
            Map<String, Object> notificacionData = new HashMap<>();
            notificacionData.put("idReferidor", idReferidor);
            notificacionData.put("nombreReferido", nombreReferido);
            notificacionData.put("tipo", "referido_exitoso");

            Map<String, Object> resultado = notificacionClientRest.notificarReferidoExitoso(notificacionData);
            return resultado != null && !resultado.containsKey("error");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean notificarPuntosOtorgados(Long idUsuario, Integer puntos) {
        try {
            Map<String, Object> puntosData = new HashMap<>();
            puntosData.put("idUsuario", idUsuario);
            puntosData.put("puntos", puntos);
            puntosData.put("tipo", "puntos_otorgados");

            Map<String, Object> resultado = notificacionClientRest.notificarPuntosOtorgados(puntosData);
            return resultado != null && !resultado.containsKey("error");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean notificarNivelAscendido(Long idUsuario, String nuevoNivel) {
        try {
            Map<String, Object> nivelData = new HashMap<>();
            nivelData.put("idUsuario", idUsuario);
            nivelData.put("nuevoNivel", nuevoNivel);
            nivelData.put("tipo", "nivel_ascendido");

            Map<String, Object> resultado = notificacionClientRest.notificarNivelAscendido(nivelData);
            return resultado != null && !resultado.containsKey("error");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean notificarCanjeRealizado(Long idUsuario, String producto, Integer puntosGastados) {
        try {
            Map<String, Object> canjeData = new HashMap<>();
            canjeData.put("idUsuario", idUsuario);
            canjeData.put("producto", producto);
            canjeData.put("puntosGastados", puntosGastados);
            canjeData.put("tipo", "canje_realizado");

            Map<String, Object> resultado = notificacionClientRest.notificarCanjeRealizado(canjeData);
            return resultado != null && !resultado.containsKey("error");
        } catch (Exception e) {
            return false;
        }
    }
}
