package com.Kiiko.ExhibitionsApp.service.impl;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.repository.ExhibitionRepository;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionRepository exbRepository;

    @Override
    public List<ExhibitionDto> getFilteredExhibitions(SearchDetails searchDetails) {
        List<ExhibitionDto> exhibitionDtoList = new ArrayList<>();
        List<Exhibition> filteredExhibitions = new ArrayList<>();
        switch (searchDetails.getSearchType()) {
            case BY_DATE:
                filteredExhibitions = exbRepository.getExhibitionsByDate(searchDetails.getDateFrom(), searchDetails.getDateTo());
                break;
            case BY_NAME:
                filteredExhibitions = exbRepository.getExhibitionsByName(searchDetails.getName());
                break;
            case BY_PRICE:
                filteredExhibitions = exbRepository.getExhibitionsByPrice(searchDetails.getPrice());
        }

        for (Exhibition exhibition : filteredExhibitions) {
            exhibitionDtoList.add(mapExhibitionToExhibitionDto(exhibition));
        }
        return exhibitionDtoList;
    }

    @Override
    public ExhibitionDto getExhibitionDetails(int exbId) {
        return mapExhibitionToExhibitionDto(exbRepository.getExhibitionDetails(exbId));
    }

    @Override
    public ExhibitionDto addExhibition(ExhibitionDto exhibition) {
        Exhibition exbToAdd = mapExhibitionDtoToExhibition(exhibition);
        return mapExhibitionToExhibitionDto(exbRepository.addExhibition(exbToAdd));
    }

    @Override
    public void deleteExhibition(int exbId) {
        exbRepository.deleteExhibition(exbId);
    }

    @Override
    public ExhibitionDto updateExhibition(int exbId, ExhibitionDto exhibition) {
        Exhibition exbToUpdate = mapExhibitionDtoToExhibition(exhibition);

        return mapExhibitionToExhibitionDto(exbRepository.updateExhibition(exbId, exbToUpdate));
    }

    private ExhibitionDto mapExhibitionToExhibitionDto(Exhibition exb) {
        return ExhibitionDto.builder()
                .exbId(exb.getExbId())
                .exbTheme(exb.getExbTheme())
                .description(exb.getDescription())
                .dayFrom(exb.getDayFrom())
                .dayTo(exb.getDayTo())
                .price(exb.getPrice())
                .ticketsBought(exb.getTicketsBought())
                .rooms(exb.getRooms())
                .build();
    }

    private Exhibition mapExhibitionDtoToExhibition(ExhibitionDto exbDto) {
        return Exhibition.builder()
                .exbId(exbDto.getExbId())
                .exbTheme(exbDto.getExbTheme())
                .description(exbDto.getDescription())
                .dayFrom(exbDto.getDayFrom())
                .dayTo(exbDto.getDayTo())
                .price(exbDto.getPrice())
                .rooms(exbDto.getRooms())
                .build();
    }
}
