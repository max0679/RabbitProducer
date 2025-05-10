package ru.maslenikov.manualtest1.conrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
