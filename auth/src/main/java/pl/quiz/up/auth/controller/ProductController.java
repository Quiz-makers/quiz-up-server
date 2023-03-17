package pl.quiz.up.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.up.auth.utils.annotation.UserAuthority;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/all")
    @UserAuthority
    public List<String> getAllTheProducts() {
        return List.of("String");
    }

}
