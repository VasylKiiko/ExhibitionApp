package com.Kiiko.ExhibitionsApp.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Kiiko.ExhibitionsApp.controller.TicketController;
import com.Kiiko.ExhibitionsApp.controller.model.TicketModel;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TicketAssembler extends RepresentationModelAssemblerSupport<TicketDto, TicketModel> {


    public TicketAssembler() {
        super(TicketController.class, TicketModel.class);
    }

    @Override
    public TicketModel toModel(TicketDto entity) {
        TicketModel ticketModel = new TicketModel(entity);
        Link getTicketInfo = linkTo(methodOn(TicketController.class).getTicketInfo(entity.getUserId(), entity.getId())).withRel("get ticket info");
        Link deleteTicket = linkTo(methodOn(TicketController.class).deleteTicket(entity.getUserId(), entity.getId())).withRel("delete");
        Link createTicket = linkTo(methodOn(TicketController.class).createTicket(entity, entity.getUserId())).withRel("create");

        ticketModel.add(getTicketInfo, deleteTicket, createTicket);

        return ticketModel;
    }
}
