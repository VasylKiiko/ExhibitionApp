package com.Kiiko.ExhibitionsApp.service.impl;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.exceptions.ExhibitionNotFoundException;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.model.enums.SearchType;
import com.Kiiko.ExhibitionsApp.repository.ExhibitionRepository;
import com.Kiiko.ExhibitionsApp.repository.TicketRepository;
import com.Kiiko.ExhibitionsApp.test.util.TestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith({MockitoExtension.class})
class ExhibitionServiceImplTest {
    @InjectMocks
    private ExhibitionServiceImpl exhibitionService;

    @Mock
    private ExhibitionRepository exhibitionRepository;
    @Mock
    private TicketRepository ticketRepository;

    @Test
    void getFilteredByPriceExhibitionsTest() {
        int page = 0;
        int recordsPerPage = 5;
        Long id = 1L;
        SearchDetails searchDetailsByPrice = SearchDetails.builder()
                .searchType(SearchType.BY_PRICE)
                .page(page)
                .recordsPerPage(recordsPerPage)
                .priceFrom(200)
                .priceTo(300)
                .build();

        Exhibition exhibition = TestDataUtil.createExhibition(id);
        List<Exhibition> listToReturn = new ArrayList<>();
        listToReturn.add(exhibition);

        when(exhibitionRepository.findAllByPriceBetween(searchDetailsByPrice.getPriceFrom(), searchDetailsByPrice.getPriceTo(),
                PageRequest.of(page, recordsPerPage))).thenReturn(listToReturn);

        List<ExhibitionDto> exhibitionDtoList = exhibitionService.getFilteredExhibitions(searchDetailsByPrice);
        assertThat(exhibitionDtoList.get(0), allOf(
                hasProperty("description", equalTo(exhibition.getDescription())),
                hasProperty("price", equalTo(exhibition.getPrice())),
                hasProperty("dateTo", equalTo(exhibition.getDateTo())),
                hasProperty("dateFrom", equalTo(exhibition.getDateFrom()))
        ));
        assertThat(exhibitionDtoList.get(0).getExbId(), is(equalTo(id)));
        assertThat(exhibitionDtoList.size(), lessThan(recordsPerPage));
    }

    @Test
    void getFilteredByNameExhibitionsTest() {
        int page = 0;
        int recordsPerPage = 5;
        Long id = 1L;

        SearchDetails searchDetailsByName = SearchDetails.builder()
                .searchType(SearchType.BY_NAME)
                .page(page)
                .recordsPerPage(recordsPerPage)
                .name("Name")
                .build();

        Exhibition exhibition = TestDataUtil.createExhibition(id);
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(exhibition);
        when(exhibitionRepository.findAllByExbThemeContains(searchDetailsByName.getName(), PageRequest.of(page, recordsPerPage)))
                .thenReturn(exhibitions);

        List<ExhibitionDto> exhibitionDtoList = exhibitionService.getFilteredExhibitions(searchDetailsByName);
        assertThat(exhibitionDtoList.get(0), allOf(
                hasProperty("description", equalTo(exhibition.getDescription())),
                hasProperty("price", equalTo(exhibition.getPrice())),
                hasProperty("dateTo", equalTo(exhibition.getDateTo())),
                hasProperty("dateFrom", equalTo(exhibition.getDateFrom()))
        ));
    }

    @Test
    void getFilteredByDateExhibitionsTest() {
        int page = 0;
        int recordsPerPage = 5;
        Long id = 1L;

        SearchDetails searchDetailsByDate = SearchDetails.builder()
                .searchType(SearchType.BY_DATE)
                .page(page)
                .recordsPerPage(recordsPerPage)
                .dateFrom(LocalDate.parse("2021-10-10"))
                .dateTo(LocalDate.parse("2021-10-11"))
                .build();

        Exhibition exhibition = TestDataUtil.createExhibition(id);
        List<Exhibition> exhibitions = new ArrayList<>();
        exhibitions.add(exhibition);
        when(exhibitionRepository.findAllByDate(searchDetailsByDate.getDateFrom(),
                searchDetailsByDate.getDateTo(), page, recordsPerPage)).thenReturn(exhibitions);

        List<ExhibitionDto> exhibitionDtoList = exhibitionService.getFilteredExhibitions(searchDetailsByDate);
        assertThat(exhibitionDtoList.get(0), allOf(
                hasProperty("description", equalTo(exhibition.getDescription())),
                hasProperty("price", equalTo(exhibition.getPrice())),
                hasProperty("dateTo", equalTo(exhibition.getDateTo())),
                hasProperty("dateFrom", equalTo(exhibition.getDateFrom())),
                hasProperty("exbId", equalTo(id))
        ));
        assertThat(exhibitionDtoList.get(0).getExbId(), is(equalTo(id)));

    }

    @Test
    void getExhibitionDetailsSuccessfulTest() {
        Long exbId = 1L;
        Exhibition exhibition = TestDataUtil.createExhibition(exbId);
        when(exhibitionRepository.findById(exbId)).thenReturn(Optional.of(exhibition));

        when(ticketRepository.getTicketCountForExhibition(exbId)).thenReturn(1L);

        ExhibitionDto exhibitionDto = exhibitionService.getExhibitionDetails(exbId);
        assertThat(exhibitionDto, allOf(
                hasProperty("description", equalTo(exhibition.getDescription())),
                hasProperty("price", equalTo(exhibition.getPrice())),
                hasProperty("dateTo", equalTo(exhibition.getDateTo())),
                hasProperty("dateFrom", equalTo(exhibition.getDateFrom()))
        ));
    }

    @Test
    void getExhibitionDetailsExceptionTest() {
        when(exhibitionRepository.findById(0L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ExhibitionNotFoundException.class, () -> exhibitionService.getExhibitionDetails(0L));
    }

    @Test
    void addExhibitionTest() {
        Long exbId = 1L;
        Exhibition exhibition = TestDataUtil.createExhibition(exbId);
        ExhibitionDto exhibitionDto = TestDataUtil.createExhibitionDto(exbId);
        when(exhibitionRepository.save(exhibition)).thenReturn(exhibition);

        when(ticketRepository.getTicketCountForExhibition(exbId)).thenReturn(1L);

        ExhibitionDto createdExhibition = exhibitionService.addExhibition(exhibitionDto);
        assertThat(createdExhibition, allOf(
                hasProperty("exbId", equalTo(exhibition.getExbId())),
                hasProperty("description", equalTo(exhibition.getDescription())),
                hasProperty("price", equalTo(exhibition.getPrice())),
                hasProperty("dateTo", equalTo(exhibition.getDateTo())),
                hasProperty("dateFrom", equalTo(exhibition.getDateFrom()))
        ));
    }

    @Test
    void deleteExhibitionTest() {
        Long exbId = 1L;
        Exhibition exhibition = TestDataUtil.createExhibition(exbId);
        ExhibitionDto exhibitionDto = TestDataUtil.createExhibitionDto(exbId);

        when(exhibitionRepository.findById(exbId)).thenReturn(Optional.of(exhibition));
        doNothing().when(exhibitionRepository).delete(exhibition);

        exhibitionService.deleteExhibition(exbId);

        verify(exhibitionRepository, times(1)).delete(exhibition);
    }

    @Test
    void updateExhibitionSuccessfulTest() {
        Long exbId = 1L;
        Exhibition exhibition = TestDataUtil.createExhibition(exbId);
        ExhibitionDto exhibitionDto = TestDataUtil.createExhibitionDto(exbId);

        when(exhibitionRepository.findById(exbId)).thenReturn(Optional.of(exhibition));
        when(exhibitionRepository.save(exhibition)).thenReturn(exhibition);
        when(ticketRepository.getTicketCountForExhibition(exbId)).thenReturn(1L);

        ExhibitionDto updatedExhibition = exhibitionService.updateExhibition(exbId, exhibitionDto);

        assertThat(updatedExhibition, allOf(
                hasProperty("exbId", equalTo(exhibitionDto.getExbId())),
                hasProperty("description", equalTo(exhibitionDto.getDescription())),
                hasProperty("price", equalTo(exhibitionDto.getPrice())),
                hasProperty("dateTo", equalTo(exhibitionDto.getDateTo())),
                hasProperty("dateFrom", equalTo(exhibitionDto.getDateFrom()))
        ));
    }

    @Test
    void updateExhibitionExceptionTest() {
        ExhibitionDto exhibitionDto = TestDataUtil.createExhibitionDto(1L);
        when(exhibitionRepository.findById(0L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ExhibitionNotFoundException.class,
                () -> exhibitionService.updateExhibition(0L, exhibitionDto));

    }
}