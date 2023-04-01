package pl.quiz.up.auth.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAnyAuthority(T(pl.quiz.up.auth.entity.RolesEnum).ROLE_MODERATOR.name())")
public @interface ModeratorAuthority {
    @AliasFor(annotation = PreAuthorize.class)
    String value() default "hasAnyAuthority(T(pl.quiz.up.auth.entity.RolesEnum).ROLE_MODERATOR.name())";
}
