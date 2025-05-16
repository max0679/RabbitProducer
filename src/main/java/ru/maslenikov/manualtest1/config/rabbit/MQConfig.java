package ru.maslenikov.manualtest1.config.rabbit;

//import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

//    static final String queueName = "firstQueue";
//    public static final String exchangeName = "testExchange";
//
//    // позволяет создать очередь если ее нет или подключиться к существующей
//    @Bean
//    public Queue myQueue() {
//        return new Queue(queueName, true, false, false);
//    }
//
//    // точкой обмена, распределяет сообщения между очередями на основе связей (bindings)
//    @Bean
//    public Exchange myExchange() {
//        return new DirectExchange(exchangeName, true, false);
//    }
//
//    // создает связь между Exchange и Queue
//    @Bean
//    public Binding myBinding(Queue myQueue, Exchange myExchange) {
//        return BindingBuilder.bind(myQueue).to(myExchange).with("first_key").noargs();
//    }

}
