package com.Kiiko.ExhibitionsApp.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Kiiko.ExhibitionsApp.controller.ExhibitionController;
import com.Kiiko.ExhibitionsApp.controller.model.ExhibitionModel;
import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ExhibitionAssembler extends RepresentationModelAssemblerSupport<ExhibitionDto, ExhibitionModel> {

    public ExhibitionAssembler() {
        super(ExhibitionController.class, ExhibitionModel.class);
    }

    @Override
    public ExhibitionModel toModel(ExhibitionDto entity) {
        ExhibitionModel exhibitionModel = new ExhibitionModel(entity);

        SearchDetails searchDetails = new SearchDetails();

        Link getDetails = linkTo(methodOn(ExhibitionController.class).getExhibitionDetails(entity.getExbId())).withRel("get details");
        Link getList = linkTo(methodOn(ExhibitionController.class).getFilteredExhibitions(searchDetails)).withRel("get filtered list");
        Link create = linkTo(methodOn(ExhibitionController.class).createExhibition(entity)).withRel("create");
        Link update = linkTo(methodOn(ExhibitionController.class).updateExhibition(entity.getExbId(), entity)).withRel("update");
        Link delete = linkTo(methodOn(ExhibitionController.class).deleteExhibition(entity.getExbId())).withRel("delete");

        exhibitionModel.add(getDetails, getList, create, update, delete);
        return exhibitionModel;
    }
}
