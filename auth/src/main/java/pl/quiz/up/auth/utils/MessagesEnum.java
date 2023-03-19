package pl.quiz.up.auth.utils;

import lombok.Getter;

public enum MessagesEnum {
    BAD_EMAIL(removeBracelets(Messages.BAD_EMAIL)),
    EMPTY_FIELD(removeBracelets(Messages.EMPTY_FIELD)),
    EXISTS_EMAIL(removeBracelets(Messages.EXISTS_EMAIL));

    @Getter
    private final String code;

    MessagesEnum(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MessagesEnum{" +
                "code='" + code + '\'' +
                '}';
    }

    private static String removeBracelets(String string) {
        return string.replace("{", "").replace("}", "");
    }
}
