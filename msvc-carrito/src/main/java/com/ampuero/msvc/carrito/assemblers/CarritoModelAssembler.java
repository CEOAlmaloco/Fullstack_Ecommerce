package com.ampuero.msvc.carrito.assemblers;

import com.ampuero.msvc.carrito.controllers.CarritoController;
import com.ampuero.msvc.carrito.models.Carrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).traerCarritoPorId(carrito.getIdCarrito())).withSelfRel(),
                linkTo(CarritoController.class).withRel("carritos"));
    }
}
