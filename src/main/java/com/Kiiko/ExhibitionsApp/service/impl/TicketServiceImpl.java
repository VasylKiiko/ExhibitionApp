package com.Kiiko.ExhibitionsApp.service.impl;

import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.exceptions.ExhibitionNotFoundException;
import com.Kiiko.ExhibitionsApp.exceptions.TicketNotFoundException;
import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.Ticket;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.repository.ExhibitionRepository;
import com.Kiiko.ExhibitionsApp.repository.TicketRepository;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import com.Kiiko.ExhibitionsApp.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ExhibitionRepository exhibitionRepository;

    @Override
    public List<TicketDto> getUsersTickets(Long userId) {
        log.info("TicketService@getUsersTickets. User id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return ticketRepository.findAllByUserEquals(user).stream()
                .map(this::mapTicketToTicketDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDto getTicketDetails(Long ticketId) {
        log.info("TicketService@getTicketDetails. Ticket id: {}", ticketId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(TicketNotFoundException::new);
        return mapTicketToTicketDto(ticket);
    }

    @Override
    public TicketDto createTicket(TicketDto ticketDto, Long userId) {
        log.info("TicketService@createTicket. TicketDto: {}", ticketDto);

        Exhibition exhibition = exhibitionRepository.findById(ticketDto.getExhibitionId())
                .orElseThrow(ExhibitionNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Ticket ticketToCreate = mapTicketDtoToTicket(ticketDto);
        ticketToCreate.setCreationTime(LocalDateTime.now());
        ticketToCreate.setPrice(ticketToCreate.getVisitorsNumber() * exhibition.getPrice());
        ticketToCreate.setUser(user);
        ticketToCreate.setExhibition(exhibition);

        Ticket createdTicket = ticketRepository.save(ticketToCreate);
        log.info("TicketService@createTicket. Created Ticket: {}", createdTicket);

        return mapTicketToTicketDto(createdTicket);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        log.info("TicketService@deleteTicket. Deleting ticket with id {}", ticketId);
        Ticket ticketToDelete = ticketRepository.findById(ticketId).orElseThrow(TicketNotFoundException::new);
        ticketRepository.delete(ticketToDelete);
    }

    private Ticket mapTicketDtoToTicket(TicketDto ticketDto) {
        return Ticket.builder()
                .visitorsNumber(ticketDto.getVisitorsNumber())
                .visitingDate(ticketDto.getVisitingDate())
                .build();
    }

    private TicketDto mapTicketToTicketDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .price(ticket.getPrice())
                .visitorsNumber(ticket.getVisitorsNumber())
                .visitingDate(ticket.getVisitingDate())
                .creationTime(ticket.getCreationTime())
                .exhibitionId(ticket.getExhibition().getExbId())
                .userId(ticket.getUser().getUserId())
                .build();
    }

}
