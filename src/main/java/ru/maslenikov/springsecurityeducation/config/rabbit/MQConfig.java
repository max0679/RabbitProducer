package ru.maslenikov.springsecurityeducation.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String exchangeName = "testExchange1";
    static final String queueName1 = "firstQueueEx1";
    static final String queueName2 = "secondQueueEx1";

    // позволяет создать очередь если ее нет или подключиться к существующей
    @Bean("first_queue")
    public Queue myQueue1() {
        return new Queue(queueName1, true, false, false);
    }

    @Bean("second_queue")
    public Queue myQueue2() {
        return new Queue(queueName2, true, false, false);
    }

    // точкой обмена, распределяет сообщения между очередями на основе связей (bindings)
    @Bean("ex1")
    public Exchange myExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    // создает связь между Exchange и Queue
    @Bean("binding_first_queue")
    public Binding myBinding1(@Qualifier("first_queue") Queue myQueue, @Qualifier("ex1") Exchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with("first_key").noargs();
    }

    @Bean("binding_second_queue")
    public Binding myBinding2(@Qualifier("second_queue") Queue myQueue, @Qualifier("ex1") Exchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with("second_key").noargs();
    }

}
