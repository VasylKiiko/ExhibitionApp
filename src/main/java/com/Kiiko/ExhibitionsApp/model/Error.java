package com.Kiiko.ExhibitionsApp.model;

import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Error {
    private String message;
    private ErrorType errorType;
    private LocalDateTime timeStamp;
}
