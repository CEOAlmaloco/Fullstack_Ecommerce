package com.ampuero.msvc.usuario.config;

import com.ampuero.msvc.usuario.entities.Usuario;
import com.ampuero.msvc.usuario.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * Inicializador de datos por defecto para el microservicio de usuarios
 * Se ejecuta al iniciar la aplicación si default.data.enabled=true
 * 
 * Ubicación: src/main/java/com/ampuero/msvc/usuario/config/UsuarioDataInitializer.java
 * 
 * Para usar en otros microservicios:
 * 1. Copiar esta estructura
 * 2. Adaptar las entidades y repositorios según el microservicio
 * 3. Configurar los datos por defecto en application.properties o application-dev.properties
 */
@Configuration
@ConditionalOnProperty(name = "default.data.enabled", havingValue = "true", matchIfMissing = true)
public class UsuarioDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDataInitializer.class);

    /**
     * Inicializa los datos por defecto en la base de datos
     */
    @Bean
    @Order(1)
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Iniciando carga de datos por defecto para usuarios");

            // Usuario Administrador
            if (!usuarioRepository.existsByCorreo("admin@levelup.com")) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setApellido("Sistema");
                admin.setCorreo("admin@levelup.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setTelefono("+56912345678");
                admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));
                admin.setGenero(Usuario.Genero.NO_ESPECIFICAR);
                admin.setDireccion("Oficina Central");
                admin.setRegion("Región Metropolitana");
                admin.setComuna("Santiago");
                admin.setCiudad("Santiago");
                admin.setPais("Chile");
                admin.setCodigoPostal("8320000");
                admin.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);
                admin.setEstado(Usuario.EstadoUsuario.ACTIVO);
                admin.setNivelUsuario(Usuario.NivelUsuario.GRAN_MAESTRO);
                admin.setCodigoReferido("ADMIN001");
                admin.setPuntosLevelUp(1000);
                admin.setEmailVerificado(true);
                admin.setTelefonoVerificado(true);
                admin.setAceptaTerminos(true);
                admin.setAceptaMarketing(false);
                usuarioRepository.save(admin);
                logger.info("Usuario administrador creado: admin@levelup.com");
            }

            // Usuario Cliente de Prueba 1
            if (!usuarioRepository.existsByCorreo("cliente1@levelup.com")) {
                Usuario cliente1 = new Usuario();
                cliente1.setNombre("Juan");
                cliente1.setApellido("Pérez");
                cliente1.setCorreo("cliente1@levelup.com");
                cliente1.setPassword(passwordEncoder.encode("cliente123"));
                cliente1.setTelefono("+56987654321");
                cliente1.setFechaNacimiento(LocalDate.of(1995, 5, 15));
                cliente1.setGenero(Usuario.Genero.MASCULINO);
                cliente1.setDireccion("Av. Principal 123");
                cliente1.setRegion("Región Metropolitana");
                cliente1.setComuna("Las Condes");
                cliente1.setCiudad("Santiago");
                cliente1.setPais("Chile");
                cliente1.setCodigoPostal("7550000");
                cliente1.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                cliente1.setEstado(Usuario.EstadoUsuario.ACTIVO);
                cliente1.setNivelUsuario(Usuario.NivelUsuario.ORO);
                cliente1.setCodigoReferido("JUAN001");
                cliente1.setPuntosLevelUp(500);
                cliente1.setEmailVerificado(true);
                cliente1.setTelefonoVerificado(true);
                cliente1.setAceptaTerminos(true);
                cliente1.setAceptaMarketing(true);
                usuarioRepository.save(cliente1);
                logger.info("Usuario cliente creado: cliente1@levelup.com");
            }

            // Usuario Cliente de Prueba 2
            if (!usuarioRepository.existsByCorreo("cliente2@levelup.com")) {
                Usuario cliente2 = new Usuario();
                cliente2.setNombre("María");
                cliente2.setApellido("González");
                cliente2.setCorreo("cliente2@levelup.com");
                cliente2.setPassword(passwordEncoder.encode("cliente123"));
                cliente2.setTelefono("+56912345679");
                cliente2.setFechaNacimiento(LocalDate.of(1992, 8, 20));
                cliente2.setGenero(Usuario.Genero.FEMENINO);
                cliente2.setDireccion("Calle Secundaria 456");
                cliente2.setRegion("Región de Valparaíso");
                cliente2.setComuna("Valparaíso");
                cliente2.setCiudad("Valparaíso");
                cliente2.setPais("Chile");
                cliente2.setCodigoPostal("2340000");
                cliente2.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                cliente2.setEstado(Usuario.EstadoUsuario.ACTIVO);
                cliente2.setNivelUsuario(Usuario.NivelUsuario.PLATA);
                cliente2.setCodigoReferido("MARIA001");
                cliente2.setPuntosLevelUp(250);
                cliente2.setEmailVerificado(true);
                cliente2.setTelefonoVerificado(false);
                cliente2.setAceptaTerminos(true);
                cliente2.setAceptaMarketing(true);
                cliente2.setReferidoPor("JUAN001");
                usuarioRepository.save(cliente2);
                logger.info("Usuario cliente creado: cliente2@levelup.com (referido por JUAN001)");
            }

            // Usuario Cliente de Prueba 3 (NOVATO)
            if (!usuarioRepository.existsByCorreo("cliente3@levelup.com")) {
                Usuario cliente3 = new Usuario();
                cliente3.setNombre("Carlos");
                cliente3.setApellido("Rodríguez");
                cliente3.setCorreo("cliente3@levelup.com");
                cliente3.setPassword(passwordEncoder.encode("cliente123"));
                cliente3.setTelefono("+56923456780");
                cliente3.setFechaNacimiento(LocalDate.of(1998, 3, 10));
                cliente3.setGenero(Usuario.Genero.MASCULINO);
                cliente3.setDireccion("Pasaje Nuevo 789");
                cliente3.setRegion("Región Metropolitana");
                cliente3.setComuna("Providencia");
                cliente3.setCiudad("Santiago");
                cliente3.setPais("Chile");
                cliente3.setCodigoPostal("7500000");
                cliente3.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                cliente3.setEstado(Usuario.EstadoUsuario.ACTIVO);
                cliente3.setNivelUsuario(Usuario.NivelUsuario.NOVATO);
                cliente3.setCodigoReferido("CARLOS001");
                cliente3.setPuntosLevelUp(50);
                cliente3.setEmailVerificado(true);
                cliente3.setTelefonoVerificado(false);
                cliente3.setAceptaTerminos(true);
                cliente3.setAceptaMarketing(false);
                usuarioRepository.save(cliente3);
                logger.info("Usuario cliente creado: cliente3@levelup.com");
            }

            // Usuario "aa aa" (aa@gmail.com / aaaa) - Fecha 11/11/1111
            if (!usuarioRepository.existsByCorreo("aa@gmail.com")) {
                Usuario usuarioAA = new Usuario();
                usuarioAA.setNombre("aa");
                usuarioAA.setApellido("aa");
                usuarioAA.setCorreo("aa@gmail.com");
                usuarioAA.setPassword(passwordEncoder.encode("aaaa"));
                usuarioAA.setTelefono("+56911111111");
                usuarioAA.setFechaNacimiento(LocalDate.of(1111, 11, 11));
                usuarioAA.setGenero(Usuario.Genero.NO_ESPECIFICAR);
                usuarioAA.setDireccion("Av. aa 1111");
                usuarioAA.setRegion("Región Metropolitana");
                usuarioAA.setComuna("Santiago");
                usuarioAA.setCiudad("Santiago");
                usuarioAA.setPais("Chile");
                usuarioAA.setCodigoPostal("8320000");
                usuarioAA.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                usuarioAA.setEstado(Usuario.EstadoUsuario.ACTIVO);
                usuarioAA.setNivelUsuario(Usuario.NivelUsuario.NOVATO);
                usuarioAA.setCodigoReferido("AAAA001");
                usuarioAA.setPuntosLevelUp(0);
                usuarioAA.setEmailVerificado(true);
                usuarioAA.setTelefonoVerificado(false);
                usuarioAA.setAceptaTerminos(true);
                usuarioAA.setAceptaMarketing(false);
                usuarioAA.setReferidoPor(null);
                usuarioRepository.save(usuarioAA);
                logger.info("Usuario 'aa aa' creado: aa@gmail.com / aaaa - Fecha: 11/11/1111");
            }

            // Usuario de Prueba Completo (aa@test.com / aaaa) - ID será 5 (después de admin, cliente1, cliente2, cliente3)
            // Este usuario tiene TODO: carrito con items, referidos, código de referido, etc.
            Usuario usuarioPrueba = null;
            if (!usuarioRepository.existsByCorreo("aa@test.com")) {
                usuarioPrueba = new Usuario();
                usuarioPrueba.setNombre("Usuario");
                usuarioPrueba.setApellido("Prueba Completo");
                usuarioPrueba.setCorreo("aa@test.com");
                // Contraseña "aaaa" hasheada con BCrypt
                usuarioPrueba.setPassword(passwordEncoder.encode("aaaa"));
                usuarioPrueba.setTelefono("+56911111111");
                usuarioPrueba.setFechaNacimiento(LocalDate.of(1995, 6, 15));
                usuarioPrueba.setGenero(Usuario.Genero.MASCULINO);
                usuarioPrueba.setDireccion("Av. Prueba Completa 123");
                usuarioPrueba.setRegion("Región Metropolitana");
                usuarioPrueba.setComuna("Santiago");
                usuarioPrueba.setCiudad("Santiago");
                usuarioPrueba.setPais("Chile");
                usuarioPrueba.setCodigoPostal("8320000");
                usuarioPrueba.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                usuarioPrueba.setEstado(Usuario.EstadoUsuario.ACTIVO);
                usuarioPrueba.setNivelUsuario(Usuario.NivelUsuario.ORO);
                usuarioPrueba.setCodigoReferido("AA001");
                usuarioPrueba.setPuntosLevelUp(750);
                usuarioPrueba.setEmailVerificado(true);
                usuarioPrueba.setTelefonoVerificado(true);
                usuarioPrueba.setAceptaTerminos(true);
                usuarioPrueba.setAceptaMarketing(true);
                // No tiene referido por (es usuario raíz)
                usuarioPrueba.setReferidoPor(null);
                usuarioPrueba = usuarioRepository.save(usuarioPrueba);
                logger.info("Usuario de prueba completo creado: aa@test.com / aaaa - ID: {}", usuarioPrueba.getIdUsuario());
            } else {
                // Si ya existe, obtenerlo para usar su ID
                usuarioPrueba = usuarioRepository.findByCorreo("aa@test.com").orElse(null);
                if (usuarioPrueba != null) {
                    logger.info("Usuario de prueba ya existe: aa@test.com / aaaa - ID: {}", usuarioPrueba.getIdUsuario());
                }
            }

            // Crear usuarios referidos por el usuario "aa"
            if (usuarioPrueba != null) {
                // Usuario referido 1 por "aa"
                if (!usuarioRepository.existsByCorreo("referido1@levelup.com")) {
                    Usuario referido1 = new Usuario();
                    referido1.setNombre("Referido");
                    referido1.setApellido("Uno");
                    referido1.setCorreo("referido1@levelup.com");
                    referido1.setPassword(passwordEncoder.encode("referido123"));
                    referido1.setTelefono("+56922222222");
                    referido1.setFechaNacimiento(LocalDate.of(1998, 3, 10));
                    referido1.setGenero(Usuario.Genero.MASCULINO);
                    referido1.setDireccion("Av. Referido 1");
                    referido1.setRegion("Región Metropolitana");
                    referido1.setComuna("Santiago");
                    referido1.setCiudad("Santiago");
                    referido1.setPais("Chile");
                    referido1.setCodigoPostal("8320000");
                    referido1.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                    referido1.setEstado(Usuario.EstadoUsuario.ACTIVO);
                    referido1.setNivelUsuario(Usuario.NivelUsuario.BRONCE);
                    referido1.setCodigoReferido("REF001");
                    referido1.setPuntosLevelUp(150);
                    referido1.setEmailVerificado(true);
                    referido1.setTelefonoVerificado(false);
                    referido1.setAceptaTerminos(true);
                    referido1.setAceptaMarketing(true);
                    referido1.setReferidoPor("AA001"); // Referido por el usuario "aa"
                    usuarioRepository.save(referido1);
                    logger.info("Usuario referido 1 creado: referido1@levelup.com (referido por AA001)");
                }

                // Usuario referido 2 por "aa"
                if (!usuarioRepository.existsByCorreo("referido2@levelup.com")) {
                    Usuario referido2 = new Usuario();
                    referido2.setNombre("Referida");
                    referido2.setApellido("Dos");
                    referido2.setCorreo("referido2@levelup.com");
                    referido2.setPassword(passwordEncoder.encode("referido123"));
                    referido2.setTelefono("+56933333333");
                    referido2.setFechaNacimiento(LocalDate.of(1997, 7, 20));
                    referido2.setGenero(Usuario.Genero.FEMENINO);
                    referido2.setDireccion("Av. Referido 2");
                    referido2.setRegion("Región Metropolitana");
                    referido2.setComuna("Santiago");
                    referido2.setCiudad("Santiago");
                    referido2.setPais("Chile");
                    referido2.setCodigoPostal("8320000");
                    referido2.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
                    referido2.setEstado(Usuario.EstadoUsuario.ACTIVO);
                    referido2.setNivelUsuario(Usuario.NivelUsuario.BRONCE);
                    referido2.setCodigoReferido("REF002");
                    referido2.setPuntosLevelUp(100);
                    referido2.setEmailVerificado(true);
                    referido2.setTelefonoVerificado(false);
                    referido2.setAceptaTerminos(true);
                    referido2.setAceptaMarketing(true);
                    referido2.setReferidoPor("AA001"); // Referido por el usuario "aa"
                    usuarioRepository.save(referido2);
                    logger.info("Usuario referido 2 creado: referido2@levelup.com (referido por AA001)");
                }
            }

            logger.info("Carga de datos por defecto de usuarios completada");
        };
    }
}

