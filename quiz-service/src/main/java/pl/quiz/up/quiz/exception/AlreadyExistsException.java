package pl.quiz.up.quiz.exception;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException() { super(); }

    public AlreadyExistsException(String message) { super(message); }

    public AlreadyExistsException(String message, Throwable t) { super(message, t); }

}
