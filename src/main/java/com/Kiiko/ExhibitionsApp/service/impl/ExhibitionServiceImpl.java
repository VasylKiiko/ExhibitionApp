package com.Kiiko.ExhibitionsApp.service.impl;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.exceptions.ExhibitionNotFoundException;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.repository.ExhibitionRepository;
import com.Kiiko.ExhibitionsApp.repository.TicketRepository;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionRepository exbRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<ExhibitionDto> getFilteredExhibitions(SearchDetails searchDetails) {
        List<Exhibition> filteredExhibitions = new ArrayList<>();
        switch (searchDetails.getSearchType()) {
            case BY_DATE:
                filteredExhibitions = exbRepository.findAllByDate(searchDetails.getDateFrom(), searchDetails.getDateTo(),
                        searchDetails.getPage(), searchDetails.getRecordsPerPage());
                break;
            case BY_NAME:
                filteredExhibitions = exbRepository.findAllByExbThemeContains(searchDetails.getName(),
                        PageRequest.of(searchDetails.getPage(), searchDetails.getRecordsPerPage()));
                break;
            case BY_PRICE:
                filteredExhibitions = exbRepository.findAllByPriceBetween(searchDetails.getPriceFrom(), searchDetails.getPriceTo(),
                        PageRequest.of(searchDetails.getPage(), searchDetails.getRecordsPerPage()));
        }

        return filteredExhibitions.stream()
                .map(this::mapExhibitionToExhibitionDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExhibitionDto getExhibitionDetails(Long exbId) {
        return mapExhibitionToExhibitionDto(exbRepository.findById(exbId).orElseThrow(ExhibitionNotFoundException::new));
    }

    @Override
    public ExhibitionDto addExhibition(ExhibitionDto exhibition) {
        Exhibition exbToAdd = mapExhibitionDtoToExhibition(exhibition);
        return mapExhibitionToExhibitionDto(exbRepository.save(exbToAdd));
    }

    @Override
    public void deleteExhibition(Long exbId) {
        Exhibition exhibitionToDelete = exbRepository.findById(exbId).orElseThrow(ExhibitionNotFoundException::new);
        exbRepository.delete(exhibitionToDelete);
    }

    @Override
    public ExhibitionDto updateExhibition(Long exbId, ExhibitionDto exhibition) {
        Exhibition exbToUpdate = mapExhibitionDtoToExhibition(exhibition);
        Exhibition exbFromDB = exbRepository.findById(exbId).orElseThrow(ExhibitionNotFoundException::new);
        exbToUpdate.setExbId(exbFromDB.getExbId());
        exbToUpdate = exbRepository.save(exbToUpdate);

        return mapExhibitionToExhibitionDto(exbToUpdate);
    }

    private long getTicketsBought(Long exbId) {
        Long sum = ticketRepository.getTicketCountForExhibition(exbId);
        return sum != null ? sum : 0L;
    }

    private ExhibitionDto mapExhibitionToExhibitionDto(Exhibition exb) {
        return ExhibitionDto.builder()
                .exbId(exb.getExbId())
                .exbTheme(exb.getExbTheme())
                .description(exb.getDescription())
                .dateFrom(exb.getDateFrom())
                .dateTo(exb.getDateTo())
                .price(exb.getPrice())
                .ticketsBought(getTicketsBought(exb.getExbId()))
                .build();
    }

    private Exhibition mapExhibitionDtoToExhibition(ExhibitionDto exbDto) {
        return Exhibition.builder()
                .exbId(exbDto.getExbId())
                .exbTheme(exbDto.getExbTheme())
                .description(exbDto.getDescription())
                .dateFrom(exbDto.getDateFrom())
                .dateTo(exbDto.getDateTo())
                .price(exbDto.getPrice())
                .build();
    }
}
