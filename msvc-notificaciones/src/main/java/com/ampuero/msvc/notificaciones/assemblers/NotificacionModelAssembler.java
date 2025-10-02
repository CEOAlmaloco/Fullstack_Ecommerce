package com.ampuero.msvc.notificaciones.assemblers;

import com.ampuero.msvc.notificaciones.controllers.NotificacionController;
import com.ampuero.msvc.notificaciones.models.Notificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {
        return EntityModel.of(notificacion,
                linkTo(methodOn(NotificacionController.class).traerNotificacionPorId(notificacion.getIdNotificacion())).withSelfRel(),
                linkTo(NotificacionController.class).withRel("notificaciones"));
    }
}
