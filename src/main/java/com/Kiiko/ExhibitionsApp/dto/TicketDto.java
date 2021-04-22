package com.Kiiko.ExhibitionsApp.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
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
