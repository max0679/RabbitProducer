package ru.maslenikov.manualtest1.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig2 {

    public static final String exchangeName = "testExchange2";
    static final String queueName1 = "firstQueueEx2";
    static final String queueName2 = "secondQueueEx2";

    // позволяет создать очередь если ее нет или подключиться к существующей
    @Bean("first_queue_ex2")
    public Queue myQueue1() {
        return new Queue(queueName1, true, false, false);
    }

    // точкой обмена, распределяет сообщения между очередями на основе связей (bindings)
    @Bean("ex2")
    public Exchange myExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    // создает связь между Exchange и Queue
    @Bean("binding_first_queue_ex2")
    public Binding myBinding1(@Qualifier("first_queue_ex2") Queue myQueue, @Qualifier("ex2") Exchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with("first_key").noargs();
    }


}
