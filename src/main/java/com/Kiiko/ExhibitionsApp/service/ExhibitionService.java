package com.Kiiko.ExhibitionsApp.service;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;

import java.time.LocalDate;
import java.util.List;

public interface ExhibitionService {

    List<ExhibitionDto> getFilteredExhibitions(SearchDetails searchDetails);

    ExhibitionDto getExhibitionDetails(int exbId);

    ExhibitionDto addExhibition(ExhibitionDto exhibition);

    void deleteExhibition(int exbId);

    ExhibitionDto updateExhibition(int exbId, ExhibitionDto exhibition);
}
