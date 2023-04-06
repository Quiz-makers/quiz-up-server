package pl.quiz.up.quiz.dto.response;


import java.sql.Timestamp;

public interface QuizDto {

    Long getQuizId();

    String getOwnerName();

    String getOwnerSurname();

    String getTitle();

    String getMetaTitle();

    String getSummary();

    String getDescription();

    String getType();

    String getCategory();

    Short getScore();

    String getSlug();

    Boolean getPublicAvailable();

    Timestamp getCreatedAt();

    Timestamp getUpdatedAt();

    Timestamp getPublicFrom();

    Long getquizTime();

    Timestamp getStartsAt();

    Timestamp getEndsAt();

}
