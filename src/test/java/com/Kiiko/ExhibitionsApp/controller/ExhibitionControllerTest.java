package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.controller.assembler.ExhibitionAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.ExhibitionModel;
import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.exceptions.ExhibitionNotFoundException;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.model.enums.SearchType;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import com.Kiiko.ExhibitionsApp.test.config.TestConfig;
import com.Kiiko.ExhibitionsApp.test.util.TestDataUtil;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.Kiiko.ExhibitionsApp.test.util.TestDataUtil.TEST_EXHIBITION_ID;
import static com.Kiiko.ExhibitionsApp.test.util.TestDataUtil.createExhibitionDto;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@WebMvcTest(ExhibitionController.class)
class ExhibitionControllerTest {

    private final static String DEFAULT_URI_PART = "/api/v1/exhibitions";
    private static ObjectMapper objectMapper;

    @MockBean
    private ExhibitionService exhibitionService;
    @MockBean
    private ExhibitionAssembler exhibitionAssembler;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setObjectMapper() {
        // change format of LocalDate when serializing object to JSON
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getExhibitionDetailsSuccessfulTest() throws Exception {
        ExhibitionDto exhibitionDto = TestDataUtil.createExhibitionDto(TEST_EXHIBITION_ID);
        ExhibitionModel exhibitionModel = new ExhibitionModel(exhibitionDto);

        when(exhibitionService.getExhibitionDetails(TEST_EXHIBITION_ID)).thenReturn(exhibitionDto);
        when(exhibitionAssembler.toModel(exhibitionDto)).thenReturn(exhibitionModel);

        mockMvc.perform(get(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exbId").value(TEST_EXHIBITION_ID));
    }

    @Test
    void getExhibitionDetailsFailed_ExhibitionNotFoundTest() throws Exception {
        String errorMessage = "Exhibition not found!";
        when(exhibitionService.getExhibitionDetails(TEST_EXHIBITION_ID))
                .thenThrow(new ExhibitionNotFoundException(errorMessage));

        mockMvc.perform(get(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    void createExhibitionSuccessfulTest() throws Exception {
        ExhibitionDto exhibitionDto = createExhibitionDto(TEST_EXHIBITION_ID);
        ExhibitionModel exhibitionModel = new ExhibitionModel(exhibitionDto);

        when(exhibitionService.addExhibition(exhibitionDto)).thenReturn(exhibitionDto);
        when(exhibitionAssembler.toModel(exhibitionDto)).thenReturn(exhibitionModel);

        mockMvc.perform(post(DEFAULT_URI_PART)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exhibitionDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.exbId").value(TEST_EXHIBITION_ID));
    }

    @Test
    void createExhibitionFailed_InvalidDateTest() throws Exception {
        ExhibitionDto exhibitionDto = createExhibitionDto(TEST_EXHIBITION_ID);
        LocalDate dateFrom = exhibitionDto.getDateFrom();
        exhibitionDto.setDateFrom(exhibitionDto.getDateTo());
        exhibitionDto.setDateTo(dateFrom);

        mockMvc.perform(post(DEFAULT_URI_PART)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exhibitionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].message").exists())
                .andExpect(jsonPath("$.[0].errorType").exists());
    }

    @Test
    void updateExhibitionSuccessfulTest() throws Exception {
        ExhibitionDto exhibitionDto = createExhibitionDto(TEST_EXHIBITION_ID);
        ExhibitionModel exhibitionModel = new ExhibitionModel(exhibitionDto);

        when(exhibitionService.updateExhibition(TEST_EXHIBITION_ID, exhibitionDto)).thenReturn(exhibitionDto);
        when(exhibitionAssembler.toModel(exhibitionDto)).thenReturn(exhibitionModel);

        mockMvc.perform(put(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exhibitionDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exbId").exists());
    }

    @Test
    void updateExhibitionFailed_ExhibitionNotFoundTest() throws Exception {
        ExhibitionDto exhibitionDto = createExhibitionDto(TEST_EXHIBITION_ID);

        when(exhibitionService.updateExhibition(TEST_EXHIBITION_ID, exhibitionDto)).thenThrow(new ExhibitionNotFoundException());

        mockMvc.perform(put(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exhibitionDto)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Exhibition not found!"))
                .andExpect(jsonPath("$.errorType").exists());
    }

    @Test
    void updateExhibitionFailed_InvalidDateTest() throws Exception {
        ExhibitionDto exhibitionDto = createExhibitionDto(TEST_EXHIBITION_ID);
        LocalDate dateFrom = exhibitionDto.getDateFrom();
        exhibitionDto.setDateFrom(exhibitionDto.getDateTo());
        exhibitionDto.setDateTo(dateFrom);

        mockMvc.perform(put(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exhibitionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].message").exists())
                .andExpect(jsonPath("$.[0].errorType").exists());
    }

    @Test
    void deleteExhibitionSuccessfulTest() throws Exception {
        mockMvc.perform(delete(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID))
                .andExpect(status().is(204));
    }

    @Test
    void deleteExhibitionFailed_ExhibitionNotFoundTest() throws Exception {
        doThrow(new ExhibitionNotFoundException()).when(exhibitionService).deleteExhibition(TEST_EXHIBITION_ID);

        mockMvc.perform(delete(DEFAULT_URI_PART + "/" + TEST_EXHIBITION_ID))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getFilteredExhibitionsSuccessfulTest() throws Exception {
        SearchDetails searchDetails = SearchDetails.builder()
                .searchType(SearchType.BY_NAME)
                .page(0)
                .recordsPerPage(1)
                .name("Name")
                .build();

        ExhibitionDto exhibitionDto = createExhibitionDto(TEST_EXHIBITION_ID);
        ExhibitionModel exhibitionModel = new ExhibitionModel(exhibitionDto);

        List<ExhibitionDto> exbDtoList = Collections.singletonList(exhibitionDto);
        when(exhibitionService.getFilteredExhibitions(searchDetails)).thenReturn(exbDtoList);
        when(exhibitionAssembler.toModel(exhibitionDto)).thenReturn(exhibitionModel);

        mockMvc.perform(post(DEFAULT_URI_PART + "/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchDetails)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(searchDetails.getRecordsPerPage())));
    }
}