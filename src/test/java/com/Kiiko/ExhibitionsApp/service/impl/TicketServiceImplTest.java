package com.Kiiko.ExhibitionsApp.service.impl;

import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.exceptions.TicketNotFoundException;
import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.Ticket;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.repository.ExhibitionRepository;
import com.Kiiko.ExhibitionsApp.repository.TicketRepository;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Kiiko.ExhibitionsApp.test.util.TestDataUtil.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class TicketServiceImplTest {
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExhibitionRepository exhibitionRepository;

    @Test
    void getUsersTicketsTest() {
        User user = createUser(TEST_USER_ID);
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        Ticket ticket = createTicket(1L);
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        when(ticketRepository.findAllByUserEquals(user)).thenReturn(tickets);

        List<TicketDto> ticketDtoList = ticketService.getUsersTickets(TEST_USER_ID);

        assertThat(ticketDtoList.get(0), allOf(
                hasProperty("id", equalTo(ticket.getId())),
                hasProperty("userId", equalTo(user.getUserId())),
                hasProperty("visitingDate", equalTo(ticket.getVisitingDate()))
        ));

        assertThrows(UserNotFoundException.class, () -> ticketService.getUsersTickets(0L));
    }

    @Test
    void getTicketDetailsTest() {
        Long ticketId = 1L;
        Long ticketNotExistId = 0L;
        Ticket ticket = createTicket(ticketId);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.findById(ticketNotExistId)).thenReturn(Optional.empty());

        TicketDto ticketDto = ticketService.getTicketDetails(ticketId);

        assertThat(ticketDto, allOf(
                hasProperty("id", equalTo(ticket.getId())),
                hasProperty("userId", equalTo(ticket.getUser().getUserId())),
                hasProperty("visitingDate", equalTo(ticket.getVisitingDate()))
        ));

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketDetails(ticketNotExistId));
    }

    @Test
    void createTicketTest() {
        Long ticketId = 1L;

        User user = createUser(TEST_USER_ID);
        Exhibition exhibition = createExhibition(TEST_EXHIBITION_ID);
        TicketDto ticketDto = createTicketDto(ticketId);
        Ticket ticket = createTicket(ticketId);
        ticket.setPrice(ticket.getVisitorsNumber() * exhibition.getPrice());
        ticket.setUser(user);
        ticket.setExhibition(exhibition);

        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(user));
        when(exhibitionRepository.findById(TEST_EXHIBITION_ID)).thenReturn(Optional.of(exhibition));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDto createdTicket = ticketService.createTicket(ticketDto, TEST_USER_ID);
        assertThat(createdTicket, allOf(
                hasProperty("id", equalTo(ticketId)),
                hasProperty("userId", equalTo(user.getUserId())),
                hasProperty("exhibitionId", equalTo(exhibition.getExbId())),
                hasProperty("price", equalTo(ticket.getVisitorsNumber() * exhibition.getPrice())),
                hasProperty("visitingDate", equalTo(ticket.getVisitingDate()))
        ));

    }

    @Test
    void deleteTicketTest() {
        Long ticketId = 1L;
        Long ticketNotExistId = 0L;
        Ticket ticket = createTicket(ticketId);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.findById(ticketNotExistId)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> ticketService.deleteTicket(ticketId));
        assertThrows(TicketNotFoundException.class, () -> ticketService.deleteTicket(ticketNotExistId));
    }

    @Test
    void isTicketBelongsToUserTest() {
        Long ticketIdExist = 1L;
        Long userIdExists = 1L;
        Long ticketIdNotExist = 0L;
        Long userIdNotExists = 0L;

        Ticket ticket = createTicket(ticketIdExist);

        when(ticketRepository.findByTicketIdAndUserId(ticketIdExist, userIdExists)).thenReturn(Optional.of(ticket));
        when(ticketRepository.findByTicketIdAndUserId(ticketIdNotExist, userIdNotExists)).thenReturn(Optional.empty());

        assertTrue(ticketService.isTicketBelongsToUser(ticketIdExist, userIdExists));
        assertFalse(ticketService.isTicketBelongsToUser(ticketIdNotExist, userIdNotExists));
    }
}