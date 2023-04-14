package pl.quiz.up.quiz.dto.response;


import java.sql.Timestamp;

public interface QuizDto {

    Long getQuizId();

    String getQuizCode();

    String getOwnerName();

    String getOwnerSurname();

    String getTitle();

    String getMetaTitle();

    String getSummary();

    String getDescription();

    String getType();

    String getCategory();

    Integer getScore();

    String getSlug();

    Boolean getPublicAvailable();

    Timestamp getCreatedAt();

    Timestamp getUpdatedAt();

    Timestamp getPublicFrom();

    Long getQuizTime();

    Timestamp getStartsAt();

    Timestamp getEndsAt();

}
