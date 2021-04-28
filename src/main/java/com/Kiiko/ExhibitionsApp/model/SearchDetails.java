package com.Kiiko.ExhibitionsApp.model;

import com.Kiiko.ExhibitionsApp.model.enums.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
