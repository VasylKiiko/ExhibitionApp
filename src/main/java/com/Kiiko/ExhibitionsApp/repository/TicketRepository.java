package com.Kiiko.ExhibitionsApp.repository;

import com.Kiiko.ExhibitionsApp.model.Ticket;
import com.Kiiko.ExhibitionsApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT SUM(ticket.visitors_number) FROM #{#entityName} as ticket WHERE ticket.exhibition_id = :exbId",
            nativeQuery = true)
    int getTicketCountForExhibition(@Param("exbId") Long exhibitionId);

    List<Ticket> findAllByUserEquals(User user);
}
