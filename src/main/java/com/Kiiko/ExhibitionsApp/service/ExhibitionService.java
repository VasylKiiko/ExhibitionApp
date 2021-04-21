package com.Kiiko.ExhibitionsApp.service;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;

import java.util.List;

public interface ExhibitionService {

    List<ExhibitionDto> getFilteredExhibitions(SearchDetails searchDetails);

    ExhibitionDto getExhibitionDetails(Long exbId);

    ExhibitionDto addExhibition(ExhibitionDto exhibition);

    void deleteExhibition(Long exbId);

    ExhibitionDto updateExhibition(Long exbId, ExhibitionDto exhibition);
}
