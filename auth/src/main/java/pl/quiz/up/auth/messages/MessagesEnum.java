package pl.quiz.up.auth.messages;

import lombok.Getter;

public enum MessagesEnum {
    BAD_EMAIL(removeBracelets(Messages.BAD_EMAIL)),
    EMPTY_FIELD(removeBracelets(Messages.EMPTY_FIELD)),
    EXISTS_EMAIL(removeBracelets(Messages.EXISTS_EMAIL)),
    EXISTS_USER_NAME(removeBracelets(Messages.EXISTS_USER_NAME)),
    INVALID_EMAIL_OR_PASSWORD(removeBracelets(Messages.INVALID_EMAIL_OR_PASSWORD));

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
