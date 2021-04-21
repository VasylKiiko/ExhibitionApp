package com.Kiiko.ExhibitionsApp.controller.model;

import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TicketModel extends RepresentationModel<TicketModel> {
    @JsonUnwrapped
    private TicketDto ticketDto;
}
