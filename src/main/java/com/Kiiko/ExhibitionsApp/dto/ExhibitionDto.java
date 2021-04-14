package com.Kiiko.ExhibitionsApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ExhibitionDto {
    public interface UserInfo { }
    public interface AdminInfo { }

    public interface NewExb { }
    public interface ExistedExb { }

    private int exbId;
    private String exbTheme;
    private String description;

    private double price;
    private int ticketsBought;
    private List<Integer> rooms;

    private LocalDate dayFrom;
    private LocalDate dayTo;
}
