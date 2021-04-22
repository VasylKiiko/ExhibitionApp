package com.Kiiko.ExhibitionsApp.service;

import com.Kiiko.ExhibitionsApp.dto.TicketDto;

import java.util.List;

public interface TicketService {
    List<TicketDto> getUsersTickets(Long userId);

    TicketDto getTicketDetails(Long ticketId);

    TicketDto createTicket(TicketDto ticketDto, Long userId);

    void deleteTicket(Long ticketId);

    boolean isTicketBelongsToUser(Long ticketId, Long userId);
}
