package pl.quiz.up.quiz.exception;

public class QuizTitleAlreadyExistsException extends RuntimeException{

    public QuizTitleAlreadyExistsException() { super(); }

    public QuizTitleAlreadyExistsException(String message) { super(message); }

    public QuizTitleAlreadyExistsException(String message, Throwable t) { super(message, t); }

}
