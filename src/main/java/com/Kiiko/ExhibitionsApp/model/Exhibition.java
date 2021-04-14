package com.Kiiko.ExhibitionsApp.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Exhibition {
    private int exbId;
    private String exbTheme;
    private String description;

    private double price;
    private int ticketsBought;
    private List<Integer> rooms;

    private LocalDate dayFrom;
    private LocalDate dayTo;
}
