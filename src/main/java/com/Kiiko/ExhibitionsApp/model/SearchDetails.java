package com.Kiiko.ExhibitionsApp.model;

import com.Kiiko.ExhibitionsApp.model.enums.SearchType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SearchDetails {
    @NotNull
    private SearchType searchType;

    private double price;
    private String name;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
