package com.Kiiko.ExhibitionsApp.dto;

import com.Kiiko.ExhibitionsApp.validation.annotation.ValidDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@ValidDate
public class ExhibitionDto {
    private Long exbId;
    private long ticketsBought;

    @NotNull
    private String exbTheme;

    @NotNull
    private String description;

    @NotNull
    private double price;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;
}
