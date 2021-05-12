package com.Kiiko.ExhibitionsApp;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.model.enums.UserRole;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import com.Kiiko.ExhibitionsApp.service.TicketService;
import com.Kiiko.ExhibitionsApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class ExhibitionsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExhibitionsAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserService userService, ExhibitionService exbService, TicketService ticketService,
                                      @Value("${app.user.password}") String password, @Value("${app.user.email}") String email) {
        return args -> {
            UserDto userDto = UserDto.builder()
                    .name("Name")
                    .surname("Surname")
                    .email(email)
                    .password(password)
                    .role(UserRole.USER)
                    .repeatPassword(password)
                    .build();
            ExhibitionDto exhibitionDto = ExhibitionDto.builder()
                    .exbTheme("Theme")
                    .description("Description")
                    .price(20)
                    .dateFrom(LocalDate.parse("2021-02-02"))
                    .dateTo(LocalDate.parse("2021-10-10"))
                    .build();
            log.info("Adding user to database...");
            UserDto createdUser = userService.addUser(userDto);
            log.info("User added");
            log.info("Adding exhibition to database...");
            ExhibitionDto createdExhibition = exbService.addExhibition(exhibitionDto);
            log.info("Exhibition added");

            TicketDto ticketDto = TicketDto.builder()
                    .price(20)
                    .visitingDate(LocalDate.parse("2021-10-10"))
                    .visitorsNumber(4)
                    .exhibitionId(createdExhibition != null ? createdExhibition.getExbId() : 1L)
                    .userId(createdUser != null ? createdUser.getUserId() : 1L)
                    .creationTime(LocalDateTime.now())
                    .build();
            log.info("Adding ticket to database...");
            ticketService.createTicket(ticketDto, ticketDto.getUserId());
            log.info("Ticket added");

            log.info("All data loaded successfully");
        };
    }

}
