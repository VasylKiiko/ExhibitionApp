package com.Kiiko.ExhibitionsApp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Kiiko.ExhibitionsApp.api.ExhibitionApi;
import com.Kiiko.ExhibitionsApp.controller.assembler.ExhibitionAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.ExhibitionModel;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExhibitionController implements ExhibitionApi {
    private final ExhibitionService exhibitionService;
    private final ExhibitionAssembler exhibitionAssembler;

    @Override
    public ExhibitionModel getExhibitionDetails(Long exhibitionId) {
        log.info("Getting exhibition details for exhibition with id = {}", exhibitionId);
        ExhibitionDto exbDto = exhibitionService.getExhibitionDetails(exhibitionId);
        return exhibitionAssembler.toModel(exbDto);
    }

    @Override
    public CollectionModel<ExhibitionModel> getFilteredExhibitions(SearchDetails searchDetails) {
        log.info("Getting exhibitions using search details {}", searchDetails);
        List<ExhibitionModel> exhibitionModels = exhibitionService.getFilteredExhibitions(searchDetails).stream()
                .map(exhibitionAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(exhibitionModels,
                linkTo(methodOn(ExhibitionController.class).getFilteredExhibitions(searchDetails)).withSelfRel());
    }

    @Override
    public ExhibitionModel createExhibition(ExhibitionDto exhibitionDto) {
        log.info("Creating new exhibition - {}", exhibitionDto);
        ExhibitionDto createdExb = exhibitionService.addExhibition(exhibitionDto);
        return exhibitionAssembler.toModel(createdExb);
    }

    @Override
    public ExhibitionModel updateExhibition(Long exhibitionId, ExhibitionDto exhibition) {
        log.info("Updating exhibition with id = {} and dto - {}", exhibitionId, exhibitionId);
        ExhibitionDto updatedExb = exhibitionService.updateExhibition(exhibitionId, exhibition);
        return exhibitionAssembler.toModel(updatedExb);
    }

    @Override
    public ResponseEntity<Void> deleteExhibition(Long exhibitionId) {
        log.info("Deleting exhibition with id = {}", exhibitionId);
        exhibitionService.deleteExhibition(exhibitionId);
        return ResponseEntity.noContent().build();
    }
}
