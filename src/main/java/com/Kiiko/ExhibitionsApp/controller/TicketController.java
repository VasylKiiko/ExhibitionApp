package com.Kiiko.ExhibitionsApp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Kiiko.ExhibitionsApp.api.TicketAPI;
import com.Kiiko.ExhibitionsApp.controller.assembler.TicketAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.TicketModel;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.exceptions.NoAccessToRecourseException;
import com.Kiiko.ExhibitionsApp.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TicketController implements TicketAPI {
    private final TicketService ticketService;
    private final TicketAssembler ticketAssembler;

    @Override
    public TicketModel getTicketInfo(Long userId, Long ticketId) {
        log.info("TicketController@getTicketInfo. Ticket ID : {}", ticketId);
        if (!ticketService.isTicketBelongsToUser(ticketId, userId)) {
            throw new NoAccessToRecourseException();
        }
        return ticketAssembler.toModel(ticketService.getTicketDetails(ticketId));
    }

    @Override
    public CollectionModel<TicketModel> getUsersTickets(Long userId) {
        log.info("TicketController@getUsersTickets. User ID : {}", userId);
        List<TicketModel> ticketList = ticketService.getUsersTickets(userId).stream()
                .map(ticketAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(ticketList, linkTo(methodOn(TicketController.class).getUsersTickets(userId)).withSelfRel());
    }

    @Override
    public TicketModel createTicket(TicketDto ticketDto, Long userId) {
        log.info("TicketController@createTicket. TicketDto : {}", ticketDto);
        return ticketAssembler.toModel(ticketService.createTicket(ticketDto, userId));
    }

    @Override
    public ResponseEntity<Void> deleteTicket(Long userId, Long ticketId) {
        log.info("TicketController@deleteTicket. Ticket ID : {}", ticketId);
        if (!ticketService.isTicketBelongsToUser(ticketId, userId)) {
            log.info("Access to ticket rejected {}", ticketId);
            throw new NoAccessToRecourseException();
        }
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}
