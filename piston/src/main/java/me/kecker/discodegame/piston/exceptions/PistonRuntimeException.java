package me.kecker.discodegame.piston.exceptions;

public class PistonRuntimeException extends RuntimeException {

    public PistonRuntimeException() {
    }

    public PistonRuntimeException(String message) {
        super(message);
    }

    public PistonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PistonRuntimeException(Throwable cause) {
        super(cause);
    }
}
