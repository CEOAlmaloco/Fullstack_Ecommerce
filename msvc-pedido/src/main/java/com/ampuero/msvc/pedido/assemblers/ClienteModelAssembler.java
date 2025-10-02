package com.ampuero.msvc.pedido.assemblers;

import com.ampuero.msvc.pedido.controllers.ClienteControllerV2;
import com.ampuero.msvc.pedido.models.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ClienteControllerV2.class).traerCliente(entity.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(ClienteControllerV2.class).traerTodos()).withRel("clientes")
        );
    }
}
