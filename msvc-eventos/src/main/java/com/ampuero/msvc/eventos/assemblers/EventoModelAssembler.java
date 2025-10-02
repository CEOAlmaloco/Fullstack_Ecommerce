package com.ampuero.msvc.eventos.assemblers;

import com.ampuero.msvc.eventos.controllers.EventoController;
import com.ampuero.msvc.eventos.models.Evento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventoModelAssembler implements RepresentationModelAssembler<Evento, EntityModel<Evento>> {

    @Override
    public EntityModel<Evento> toModel(Evento evento) {
        return EntityModel.of(evento,
                linkTo(methodOn(EventoController.class).traerPorId(evento.getIdEvento())).withSelfRel(),
                linkTo(EventoController.class).withRel("eventos"));
    }
}
