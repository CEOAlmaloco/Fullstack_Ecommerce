package com.ampuero.msvc.contenido.assemblers;

import com.ampuero.msvc.contenido.controllers.ContenidoController;
import com.ampuero.msvc.contenido.models.Articulo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticuloModelAssembler implements RepresentationModelAssembler<Articulo, EntityModel<Articulo>> {

    @Override
    public EntityModel<Articulo> toModel(Articulo articulo) {
        return EntityModel.of(articulo,
                linkTo(methodOn(ContenidoController.class).traerArticuloPorId(articulo.getIdArticulo())).withSelfRel(),
                linkTo(ContenidoController.class).withRel("articulos"));
    }
}
