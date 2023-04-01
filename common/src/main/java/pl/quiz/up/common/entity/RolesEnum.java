package pl.quiz.up.common.entity;

import lombok.Getter;

public enum RolesEnum {
    ROLE_ADMIN(1),
    ROLE_MODERATOR(2),
    ROLE_USER(3);

    @Getter
    private final int identity;

    RolesEnum(int identity) {
        this.identity = identity;
    }

}
