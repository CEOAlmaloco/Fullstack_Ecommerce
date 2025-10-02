package com.ampuero.msvc.promociones.assemblers;

import com.ampuero.msvc.promociones.controllers.PromocionController;
import com.ampuero.msvc.promociones.models.Promocion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PromocionModelAssembler implements RepresentationModelAssembler<Promocion, EntityModel<Promocion>> {

    @Override
    public EntityModel<Promocion> toModel(Promocion promocion) {
        return EntityModel.of(promocion,
                linkTo(methodOn(PromocionController.class).traerPorId(promocion.getIdPromocion())).withSelfRel(),
                linkTo(PromocionController.class).withRel("promociones"));
    }
}
