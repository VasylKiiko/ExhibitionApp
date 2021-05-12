package com.Kiiko.ExhibitionsApp;

import com.Kiiko.ExhibitionsApp.controller.model.ExhibitionModel;
import com.Kiiko.ExhibitionsApp.controller.model.TicketModel;
import com.Kiiko.ExhibitionsApp.controller.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExhibitionsAppApplicationTests {

    @Value("http://localhost:${local.server.port}/api/v1/")
    private String baseUrl;

    @Value("${app.user.email}")
    private String userEmail;

    @Value("${app.exhibition.id}")
    private Long exbId;

    @Value("${app.ticket.id}")
    private Long ticketId;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void getUser() {
        UserModel userModel = restTemplate.getForObject(baseUrl + "users/email/" + userEmail, UserModel.class);
        assertEquals(userModel.getUserDto().getEmail(), userEmail);
    }

    @Test
    void getExhibition() {
        ExhibitionModel exbModel = restTemplate.getForObject(baseUrl + "exhibitions/" + exbId, ExhibitionModel.class);
        assertEquals(exbModel.getExhibitionDto().getExbId(), exbId);
    }

    @Test
    void getTicket() {
        TicketModel ticketModel = restTemplate.getForObject(baseUrl + "users/" + 1 +"/tickets/" + ticketId, TicketModel.class);
        assertEquals(ticketModel.getTicketDto().getId(), ticketId);
    }

}
