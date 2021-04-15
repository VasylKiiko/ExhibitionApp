package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {
    private final ExhibitionService exhibitionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{exhibitionId}")
    public ExhibitionDto getExhibitionDetails(@PathVariable int exhibitionId) {
        log.info("Getting exhibition details for exhibition with id = {}", exhibitionId);
        return exhibitionService.getExhibitionDetails(exhibitionId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public List<ExhibitionDto> getFilteredExhibitions(@Valid @RequestBody SearchDetails searchDetails) {
        log.info("Getting exhibitions using search details {}", searchDetails);
        return exhibitionService.getFilteredExhibitions(searchDetails);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Validated(ExhibitionDto.NewExb.class)
    public ExhibitionDto createExhibition(@Valid @RequestBody ExhibitionDto exhibitionDto) {
        log.info("Creating new exhibition - {}", exhibitionDto);
        return exhibitionService.addExhibition(exhibitionDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{exhibitionId}")
    @Validated(ExhibitionDto.UpdateExb.class)
    public ExhibitionDto updateExhibition(@PathVariable int exhibitionId, @Valid @RequestBody ExhibitionDto exhibition) {
        log.info("Updating exhibition with id = {} and dto - {}", exhibitionId, exhibitionId);
        return exhibitionService.updateExhibition(exhibitionId, exhibition);
    }

    @DeleteMapping("/{exhibitionId}")
    public ResponseEntity<Void> deleteExhibition(@PathVariable int exhibitionId) {
        log.info("Deleting exhibition with id = {}", exhibitionId);
        exhibitionService.deleteExhibition(exhibitionId);
        return ResponseEntity.noContent().build();
    }
}
