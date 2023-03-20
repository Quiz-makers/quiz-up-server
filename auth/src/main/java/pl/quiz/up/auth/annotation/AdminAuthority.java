package pl.quiz.up.auth.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAnyAuthority(T(pl.quiz.up.auth.entity.RolesEnum).USER_ROLE.name(), T(pl.quiz.up.auth.entity.RolesEnum).MODERATOR_ROLE.name(), T(pl.quiz.up.auth.entity.RolesEnum).ADMIN_ROLE.name())")
public @interface AdminAuthority {
    @AliasFor(annotation = PreAuthorize.class)
    String value() default "hasAnyAuthority(T(pl.quiz.up.auth.entity.RolesEnum).USER_ROLE.name(), T(pl.quiz.up.auth.entity.RolesEnum).MODERATOR_ROLE.name(), T(pl.quiz.up.auth.entity.RolesEnum).ADMIN_ROLE.name())";
}
