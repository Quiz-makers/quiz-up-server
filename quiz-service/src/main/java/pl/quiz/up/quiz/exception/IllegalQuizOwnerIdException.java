package pl.quiz.up.quiz.exception;

public final class IllegalQuizOwnerIdException extends RuntimeException {

    public IllegalQuizOwnerIdException() { super(); }

    public IllegalQuizOwnerIdException(String message) { super(message); }

    public IllegalQuizOwnerIdException(String message, Throwable t) { super(message, t); }

}
