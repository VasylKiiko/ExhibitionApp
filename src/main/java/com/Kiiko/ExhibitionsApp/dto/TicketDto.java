package com.Kiiko.ExhibitionsApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto {
    private Long id;
    private Long userId;
    @NotNull
    private LocalDate visitingDate;
    @NotNull
    private int visitorsNumber;

    @NotNull
    private Long exhibitionId;

    private double price;
    private LocalDateTime creationTime;
}
