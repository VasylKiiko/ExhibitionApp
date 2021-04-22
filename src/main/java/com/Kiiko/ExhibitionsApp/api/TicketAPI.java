package com.Kiiko.ExhibitionsApp.api;

import com.Kiiko.ExhibitionsApp.controller.model.TicketModel;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Tickets management API")
@RequestMapping("api/v1/users/{userId}/tickets")
public interface TicketAPI {
    @ApiImplicitParam(name = "ticketId", paramType = "path", required = true, value = "Ticket ID")
    @ApiOperation("Get tickets details by email")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{ticketId}")
    TicketModel getTicketInfo(@PathVariable Long userId,
                              @PathVariable Long ticketId);

    @ApiImplicitParam(name = "userId", required = true, paramType = "path", value = "User ID")
    @ApiOperation("Get list of users tickets")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    CollectionModel<TicketModel> getUsersTickets(@PathVariable Long userId);

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creating ticket for user")
    @PostMapping
    TicketModel createTicket(@Valid @RequestBody TicketDto ticketDto, @PathVariable Long userId);

    @ApiOperation("Delete ticket with ticketId")
    @DeleteMapping("/{ticketId}")
    ResponseEntity<Void> deleteTicket(@PathVariable Long userId,
                                      @PathVariable Long ticketId);
}
