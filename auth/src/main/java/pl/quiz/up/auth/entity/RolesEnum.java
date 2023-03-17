package pl.quiz.up.auth.entity;

import lombok.Getter;

public enum RolesEnum {
    ADMIN_ROLE(1),
    MODERATOR_ROLE(2),
    USER_ROLE(3);

    @Getter
    private final int identity;

    RolesEnum(int identity) {
        this.identity = identity;
    }

}
