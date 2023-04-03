package pl.quiz.up.quiz.exception;

public final class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException() { super(); }

    public QuizNotFoundException(String message) { super(message); }

    public QuizNotFoundException(String message, Throwable t) { super(message, t); }

}
