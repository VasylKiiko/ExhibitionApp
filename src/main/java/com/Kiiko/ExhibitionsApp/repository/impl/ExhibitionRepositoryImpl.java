package com.Kiiko.ExhibitionsApp.repository.impl;

import com.Kiiko.ExhibitionsApp.exceptions.ExhibitionNotFoundException;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import com.Kiiko.ExhibitionsApp.repository.ExhibitionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExhibitionRepositoryImpl implements ExhibitionRepository {
    private final List<Exhibition> exhibitionList = new ArrayList<>();
    private static int exhibitionCounter = 0;

    {
        exhibitionList.add(Exhibition.builder()
                .exbId(100)
                .exbTheme("Theme")
                .description("Description")
                .ticketsBought(0)
                .price(10)
                .dayFrom(LocalDate.parse("2021-04-14"))
                .dayTo(LocalDate.parse("2021-04-20"))
                .rooms(Arrays.asList(1, 2, 3))
                .build());
    }

    @Override
    public List<Exhibition> getExhibitionsByDate(LocalDate fromDate, LocalDate toDate) {
        return exhibitionList.stream()
                .filter(exb -> exb.getDayFrom().isAfter(fromDate) && exb.getDayTo().isBefore(toDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exhibition> getExhibitionsByPrice(double price) {
        return exhibitionList.stream()
                .filter(exb -> exb.getPrice() >= (price * 1.05) && exb.getPrice() <= (price * 1.05))
                .collect(Collectors.toList());
    }

    @Override
    public List<Exhibition> getExhibitionsByName(String name) {
        return exhibitionList.stream()
                .filter(exb -> exb.getExbTheme().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Exhibition getExhibitionDetails(int exbId) {
        return exhibitionList.stream()
                .filter(exhibition -> exhibition.getExbId() == exbId)
                .findFirst()
                .orElseThrow(() -> new ExhibitionNotFoundException("Exhibition with id = " + exbId + " not found"));
    }

    @Override
    public Exhibition addExhibition(Exhibition exhibition) {
        exhibition.setExbId(++exhibitionCounter);
        exhibitionList.add(exhibition);
        return exhibition;
    }

    @Override
    public void deleteExhibition(int exbId) {
        exhibitionList.removeIf(exhibition -> exhibition.getExbId() == exbId);
    }

    @Override
    public Exhibition updateExhibition(int exbId, Exhibition exhibition) {
        boolean isDeleted = exhibitionList.removeIf(exhib -> exhib.getExbId() == exbId);
        if (isDeleted) {
            exhibition.setExbId(exbId);
            exhibitionList.add(exhibition);
        } else {
            throw new ExhibitionNotFoundException("Exhibition with id " + exbId + "doesn't exists");
        }
        return exhibition;
    }
}
