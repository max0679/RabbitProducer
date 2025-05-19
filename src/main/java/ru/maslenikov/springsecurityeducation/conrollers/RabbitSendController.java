package ru.maslenikov.springsecurityeducation.conrollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.maslenikov.springsecurityeducation.config.rabbit.MQConfig;
import ru.maslenikov.springsecurityeducation.config.rabbit.MQConfig2;

@RestController
@Slf4j
@RequestMapping("/send")
public class RabbitSendController {

    private final RabbitTemplate rabbitTemplate;
    private static long messCounter = 0L;

    public RabbitSendController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/test4")
    public String ciao2() throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

    @GetMapping("/qe1")
    public ResponseEntity<String> qe1(@RequestParam(required = true) String message) {

        //log.info("message {} send ({})", message, ++messCounter);
        rabbitTemplate.convertAndSend(MQConfig2.exchangeName, "first_key", message);
        return ResponseEntity.ok("OK (q1): " + message);
    }

    @GetMapping("/qe2")
    public ResponseEntity<String> qe2(@RequestParam String message) {
        rabbitTemplate.convertAndSend(MQConfig.exchangeName, "second_key", message);
        //log.info("message {} send ({})", message, ++messCounter);
        return ResponseEntity.ok("OK (q2): " + message);
    }

}
