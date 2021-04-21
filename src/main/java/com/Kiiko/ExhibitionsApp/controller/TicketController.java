package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.api.TicketAPI;
import com.Kiiko.ExhibitionsApp.controller.assembler.TicketAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.TicketModel;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        //TODO: check if user is owner of this ticket
        return ticketAssembler.toModel(ticketService.getTicketDetails(ticketId));
    }

    @Override
    public List<TicketModel> getUsersTickets(Long userId) {
        log.info("TicketController@getUsersTickets. User ID : {}", userId);
        return ticketService.getUsersTickets(userId).stream()
                .map(ticketAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public TicketModel createTicket(TicketDto ticketDto, Long userId) {
        log.info("TicketController@createTicket. TicketDto : {}", ticketDto);
        return ticketAssembler.toModel(ticketService.createTicket(ticketDto, userId));
    }

    @Override
    public ResponseEntity<Void> deleteTicket(Long userId, Long ticketId) {
        //TODO: check if user is owner of this ticket
        log.info("TicketController@deleteTicket. Ticket ID : {}", ticketId);
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}
