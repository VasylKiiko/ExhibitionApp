package com.Kiiko.ExhibitionsApp.repository;

import com.Kiiko.ExhibitionsApp.model.Exhibition;

import java.time.LocalDate;
import java.util.List;

public interface ExhibitionRepository {
    List<Exhibition> getExhibitionsByDate(LocalDate fromDate, LocalDate toDate);
    List<Exhibition> getExhibitionsByPrice(double price);
    List<Exhibition> getExhibitionsByName(String name);

    Exhibition getExhibitionDetails(int exbId);
    Exhibition addExhibition(Exhibition exhibition);
    void deleteExhibition(int exbId);
    Exhibition updateExhibition(int exbId, Exhibition exhibition);
}
