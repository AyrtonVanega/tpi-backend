package ar.edu.utn.frc.backend.solicitudes.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
