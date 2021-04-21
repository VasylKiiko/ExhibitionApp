package com.Kiiko.ExhibitionsApp.exceptions;

import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;

public class TicketNotFoundException extends ServiceException {
    private static final String DEFAULT_MESSAGE = "Ticket not found ";

    public TicketNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public TicketNotFoundException(String ticketId) {
        super(DEFAULT_MESSAGE + ticketId);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR;
    }
}
