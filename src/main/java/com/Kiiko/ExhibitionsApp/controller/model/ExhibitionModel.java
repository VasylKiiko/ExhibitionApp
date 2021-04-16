package com.Kiiko.ExhibitionsApp.controller.model;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExhibitionModel extends RepresentationModel<ExhibitionModel> {
    @JsonUnwrapped
    private ExhibitionDto exhibitionDto;
}
