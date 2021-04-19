package com.Kiiko.ExhibitionsApp.model;

import com.Kiiko.ExhibitionsApp.model.enums.SearchType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SearchDetails {
    @NotNull
    private SearchType searchType;
    @NotNull
    private int page;
    @NotNull
    private int recordsPerPage;

    private double priceFrom;
    private double priceTo;

    private String name;

    private LocalDate dateFrom;
    private LocalDate dateTo;
}
