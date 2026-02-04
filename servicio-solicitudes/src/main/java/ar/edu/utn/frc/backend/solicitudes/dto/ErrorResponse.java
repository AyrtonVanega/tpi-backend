package ar.edu.utn.frc.backend.solicitudes.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
