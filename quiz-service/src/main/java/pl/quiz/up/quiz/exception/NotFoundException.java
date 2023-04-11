package pl.quiz.up.quiz.exception;

public final class NotFoundException extends RuntimeException {

    public NotFoundException() { super(); }

    public NotFoundException(String message) { super(message); }

    public NotFoundException(String message, Throwable t) { super(message, t); }

}
