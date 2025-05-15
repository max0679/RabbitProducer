package ru.maslenikov.manualtest1.conrollers;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslenikov.manualtest1.test.Person;

import java.util.concurrent.*;
import java.util.function.Supplier;

@RestController
@RequestMapping("")
public class HomeController {

    private final HomeController homeController;

    public HomeController(@Lazy HomeController homeController) {
        this.homeController = homeController;
    }

    @GetMapping("/test2")
    public String test2() {

        Person person = new Person();
        person
            .custom1(p -> {
                p.setAge(16);
                p.setName("max");
            })
        .custom2(p -> {p.setProperty("aga");});

        return "OK";
    }

    @GetMapping("/test")
    public String ciao() throws Exception {
        Callable<String> task = () -> {
            System.out.println("executing task");
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };
        //ExecutorService e = Executors.newCachedThreadPool();
        DelegatingSecurityContextExecutorService e = new DelegatingSecurityContextExecutorService(Executors.newCachedThreadPool());
        try {
            //var contextTask = new DelegatingSecurityContextCallable<>(task);
            return "Ciao, " + e.submit(task).get() + "!";
            //return "Ciao, " + e.submit(contextTask).get() + "!";
        } finally {
            System.out.println("shutting down");
            e.shutdown();
        }
    }

    @GetMapping("/hello")
    public String hello(Authentication authentication) throws Exception {
/*        System.out.println("security context in main test: " + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("result: " + getTestResult());*/
        //homeController.asyncFunc();

        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "hello!";
        };
        System.out.println(callable.call());

        return "Hello World";
    }

    @Async
    public void asyncFunc() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("async func");
        System.out.println("security context in async func: " + SecurityContextHolder.getContext().getAuthentication());
    }


    private String getTestResult() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("security context in other thread: " + SecurityContextHolder.getContext().getAuthentication());
        }).start();
        System.out.println("security context in getTestResult: " + SecurityContextHolder.getContext().getAuthentication());
        return "yess";
    }


}
