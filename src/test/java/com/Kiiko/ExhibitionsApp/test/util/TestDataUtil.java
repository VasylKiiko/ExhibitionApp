package com.Kiiko.ExhibitionsApp.test.util;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.Ticket;
import com.Kiiko.ExhibitionsApp.model.User;
import com.Kiiko.ExhibitionsApp.model.enums.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataUtil {
    public static final String TEST_EMAIL = "vasylkiiko@gmail.com";
    public static final LocalDateTime TICKET_CREATION_TIME = LocalDateTime.parse("2021-07-09T11:06:22");
    public static final Long TEST_USER_ID = 1L;
    public static final Long TEST_EXHIBITION_ID = 1L;

    public static User createUser(Long id) {
        return User.builder()
                .userId(id)
                .name("Name")
                .surname("Surname")
                .userRole(UserRole.USER)
                .email(TEST_EMAIL)
                .password("Qwerty123!")
                .build();
    }

    public static UserDto createUserDto(Long id) {
        return UserDto.builder()
                .userId(id)
                .name("Name")
                .surname("Surname")
                .role(UserRole.USER)
                .email(TEST_EMAIL)
                .password("Qwerty123!")
                .build();
    }

    public static Exhibition createExhibition(Long id) {
        return Exhibition.builder()
                .exbId(id)
                .exbTheme("Theme")
                .description("Description")
                .price(20)
                .dateFrom(LocalDate.parse("2021-02-02"))
                .dateTo(LocalDate.parse("2021-10-10"))
                .build();
    }

    public static ExhibitionDto createExhibitionDto(Long id) {
        return ExhibitionDto.builder()
                .exbId(id)
                .exbTheme("Theme")
                .description("Description")
                .price(20)
                .dateFrom(LocalDate.parse("2021-02-02"))
                .dateTo(LocalDate.parse("2021-10-10"))
                .build();
    }

    public static Ticket createTicket(Long ticketId) {
        return Ticket.builder()
                .id(ticketId)
                .price(20)
                .visitingDate(LocalDate.parse("2021-10-10"))
                .visitorsNumber(4)
                .creationTime(TICKET_CREATION_TIME)
                .exhibition(createExhibition(TEST_EXHIBITION_ID))
                .user(createUser(TEST_USER_ID))
                .build();
    }

    public static TicketDto createTicketDto(Long ticketId) {
        return TicketDto.builder()
                .id(ticketId)
                .price(20)
                .visitingDate(LocalDate.parse("2021-10-10"))
                .visitorsNumber(4)
                .creationTime(TICKET_CREATION_TIME)
                .exhibitionId(TEST_EXHIBITION_ID)
                .userId(TEST_USER_ID)
                .build();
    }
}
