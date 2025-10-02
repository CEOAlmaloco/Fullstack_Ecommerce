package com.ampuero.msvc.pagos.assemblers;

import com.ampuero.msvc.pagos.controllers.PagoController;
import com.ampuero.msvc.pagos.models.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoController.class).traerPagoPorId(pago.getIdPago())).withSelfRel(),
                linkTo(PagoController.class).withRel("pagos"));
    }
}
