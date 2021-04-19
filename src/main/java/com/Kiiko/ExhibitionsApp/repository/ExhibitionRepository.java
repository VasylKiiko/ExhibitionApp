package com.Kiiko.ExhibitionsApp.repository;

import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.model.Exhibition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    @Query(value = "SELECT DISTINCT * FROM #{#entityName} as exb " +
            "WHERE exb.date_from BETWEEN :fromDate AND :toDate " +
            "OR exb.date_to BETWEEN :fromDate AND :toDate " +
            "OR (exb.date_from <= :fromDate AND exb.date_to >= :toDate) " +
            "ORDER BY exb.date_from ASC LIMIT :page,:num", nativeQuery = true)
    List<Exhibition> findAllByDate(@Param("fromDate") LocalDate fromDate,
                                   @Param("toDate") LocalDate toDate,
                                   @Param("page") int page,
                                   @Param("num") int number);

    List<Exhibition> findAllByPriceBetween(double priceFrom, double priceTo, Pageable pageable);

    List<Exhibition> findAllByExbThemeContains(String name, Pageable pageable);
}
