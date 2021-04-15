package com.Kiiko.ExhibitionsApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ExhibitionDto {
    public interface NewExb {
    }

    public interface UpdateExb {
    }

    private int exbId;
    private int ticketsBought;

    private String exbTheme;

    @NotNull(groups = {NewExb.class, UpdateExb.class})
    private String description;

    @NotNull(groups = {NewExb.class, UpdateExb.class})
    private double price;

    @NotNull(groups = {NewExb.class, UpdateExb.class})
    private List<Integer> rooms;

    @NotNull(groups = {NewExb.class, UpdateExb.class})
    private LocalDate dayFrom;

    @NotNull(groups = {NewExb.class, UpdateExb.class})
    private LocalDate dayTo;
}
