package com.ampuero.msvc.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuario", url = "${MSVC_USUARIO_URL:http://localhost:8095}")
public interface UsuarioClientRest {

    @PostMapping("/api/v1/usuarios/validate-credentials")
    CredentialsValidationResponse validarCredenciales(@RequestBody Object credentials);

    @PostMapping("/api/v1/usuarios")
    Object registrarUsuario(@RequestBody Object registerRequest);

    @GetMapping("/usuarios/{id}")
    UsuarioInfo obtenerUsuarioPorId(@PathVariable Long id);

    // DTOs para respuestas
    class CredentialsValidationResponse {
        private boolean valid;
        private Long userId;
        private String nombreUsuario;
        private String apellidosUsuario;
        private String correoUsuario;
        private String tipoUsuario;
        private Boolean descuentoDuoc;
        private String region;
        private String comuna;
        private String mensaje;

        // Getters y setters
        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getApellidosUsuario() {
            return apellidosUsuario;
        }

        public void setApellidosUsuario(String apellidosUsuario) {
            this.apellidosUsuario = apellidosUsuario;
        }

        public String getCorreoUsuario() {
            return correoUsuario;
        }

        public void setCorreoUsuario(String correoUsuario) {
            this.correoUsuario = correoUsuario;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public void setTipoUsuario(String tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
        }

        public Boolean getDescuentoDuoc() {
            return descuentoDuoc;
        }

        public void setDescuentoDuoc(Boolean descuentoDuoc) {
            this.descuentoDuoc = descuentoDuoc;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getComuna() {
            return comuna;
        }

        public void setComuna(String comuna) {
            this.comuna = comuna;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    class UsuarioInfo {
        private Long idUsuario;
        private String nombreUsuario;
        private String apellidosUsuario;
        private String correoUsuario;
        private String tipoUsuario;
        private Boolean descuentoDuoc;
        private String region;
        private String comuna;

        // Getters y setters
        public Long getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getApellidosUsuario() {
            return apellidosUsuario;
        }

        public void setApellidosUsuario(String apellidosUsuario) {
            this.apellidosUsuario = apellidosUsuario;
        }

        public String getCorreoUsuario() {
            return correoUsuario;
        }

        public void setCorreoUsuario(String correoUsuario) {
            this.correoUsuario = correoUsuario;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public void setTipoUsuario(String tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
        }

        public Boolean getDescuentoDuoc() {
            return descuentoDuoc;
        }

        public void setDescuentoDuoc(Boolean descuentoDuoc) {
            this.descuentoDuoc = descuentoDuoc;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getComuna() {
            return comuna;
        }

        public void setComuna(String comuna) {
            this.comuna = comuna;
        }
    }
}