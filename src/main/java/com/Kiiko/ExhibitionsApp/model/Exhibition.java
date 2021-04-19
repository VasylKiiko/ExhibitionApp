package com.Kiiko.ExhibitionsApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exbId;

    private String exbTheme;
    private String description;

    private double price;
    @Transient
    private int ticketsBought;
    // private List<Integer> rooms;

    private LocalDate dateFrom;
    private LocalDate dateTo;
}
