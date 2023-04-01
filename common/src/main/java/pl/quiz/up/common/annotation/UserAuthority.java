package pl.quiz.up.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAnyAuthority(T(pl.quiz.up.common.entity.RolesEnum).ROLE_USER.name())")
public @interface UserAuthority {
    @AliasFor(annotation = PreAuthorize.class)
    String value() default "hasAnyAuthority(T(pl.quiz.up.common.entity.RolesEnum).ROLE_USER.name())";
}
