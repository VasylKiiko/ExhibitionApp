package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.controller.assembler.TicketAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.TicketModel;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.exceptions.ExhibitionNotFoundException;
import com.Kiiko.ExhibitionsApp.service.TicketService;
import com.Kiiko.ExhibitionsApp.test.config.TestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.Kiiko.ExhibitionsApp.test.util.TestDataUtil.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TicketController.class)
@Import(TestConfig.class)
class TicketControllerTest {

    private static ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;
    @MockBean
    private TicketAssembler ticketAssembler;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getTicketInfoSuccessfulTest() throws Exception {
        TicketDto ticketDto = createTicketDto(TEST_TICKET_ID);
        TicketModel ticketModel = new TicketModel(ticketDto);

        when(ticketService.isTicketBelongsToUser(TEST_USER_ID, TEST_TICKET_ID)).thenReturn(true);
        when(ticketService.getTicketDetails(TEST_TICKET_ID)).thenReturn(ticketDto);
        when(ticketAssembler.toModel(ticketDto)).thenReturn(ticketModel);

        mockMvc.perform(get("/api/v1/users/" + TEST_USER_ID + "/tickets/" + TEST_TICKET_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_TICKET_ID));
    }

    @Test
    void getTicketInfoFailed_NoAccessToRecourseTest() throws Exception {
        when(ticketService.isTicketBelongsToUser(TEST_USER_ID, TEST_TICKET_ID)).thenReturn(false);

        mockMvc.perform(get("/api/v1/users/" + TEST_USER_ID + "/tickets/" + TEST_TICKET_ID))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getUsersTicketsTest() throws Exception {
        TicketDto ticketDto = createTicketDto(TEST_TICKET_ID);
        TicketModel ticketModel = new TicketModel(ticketDto);

        List<TicketDto> ticketList = Collections.singletonList(ticketDto);

        when(ticketService.getUsersTickets(TEST_USER_ID)).thenReturn(ticketList);
        when(ticketAssembler.toModel(ticketDto)).thenReturn(ticketModel);

        mockMvc.perform(get("/api/v1/users/" + TEST_USER_ID + "/tickets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.links").exists());

    }

    @Test
    void createTicketSuccessfulTest() throws Exception {
        TicketDto ticketDto = createTicketDto(TEST_TICKET_ID);
        TicketModel ticketModel = new TicketModel(ticketDto);

        when(ticketService.createTicket(ticketDto, TEST_USER_ID)).thenReturn(ticketDto);
        when(ticketAssembler.toModel(ticketDto)).thenReturn(ticketModel);

        mockMvc.perform(post("/api/v1/users/" + TEST_USER_ID + "/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createTicketFailed_NoSuchExhibitionTest() throws Exception {
        String errorMessage = "No Such Exhibition!";
        TicketDto ticketDto = createTicketDto(TEST_TICKET_ID);
        when(ticketService.createTicket(ticketDto, TEST_USER_ID)).thenThrow(new ExhibitionNotFoundException(errorMessage));

        mockMvc.perform(post("/api/v1/users/" + TEST_USER_ID + "/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    void deleteTicketSuccessfulTest() throws Exception {
        when(ticketService.isTicketBelongsToUser(TEST_TICKET_ID, TEST_USER_ID)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/users/" + TEST_USER_ID + "/tickets/" + TEST_TICKET_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTicketFailed_NoAccessToRecourseTest() throws Exception {
        when(ticketService.isTicketBelongsToUser(TEST_USER_ID, TEST_TICKET_ID)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/users/" + TEST_USER_ID + "/tickets/" + TEST_TICKET_ID))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").exists());
    }
}