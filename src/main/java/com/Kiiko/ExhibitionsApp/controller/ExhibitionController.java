package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {
    private final ExhibitionService exhibitionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{exhibitionId}")
    public ExhibitionDto getExhibitionDetails(@PathVariable int exhibitionId) {
        return exhibitionService.getExhibitionDetails(exhibitionId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/filter")
    public List<ExhibitionDto> getFilteredExhibitions(@Valid @RequestBody SearchDetails searchDetails) {
        return exhibitionService.getFilteredExhibitions(searchDetails);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ExhibitionDto createExhibition(@RequestBody ExhibitionDto exhibitionDto) {
        return exhibitionService.addExhibition(exhibitionDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{exhibitionId}")
    public ExhibitionDto updateExhibition(@PathVariable int exhibitionId, @RequestBody ExhibitionDto exhibition) {
        return exhibitionService.updateExhibition(exhibitionId, exhibition);
    }

    @DeleteMapping("/{exhibitionId}")
    public ResponseEntity<Void> deleteExhibition(@PathVariable int exhibitionId) {
        exhibitionService.deleteExhibition(exhibitionId);
        return ResponseEntity.noContent().build();
    }
}
