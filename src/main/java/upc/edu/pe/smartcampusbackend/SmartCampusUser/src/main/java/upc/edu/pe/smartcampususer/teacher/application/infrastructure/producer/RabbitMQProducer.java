package upc.edu.pe.smartcampususer.teacher.application.infrastructure.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import upc.edu.pe.smartcampususer.teacher.domain.dto.EmailMessage;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {

    private final AmqpTemplate rabbitTemplate;

    private final String exchange = "smartcampus.exchange";
    private final String routingKey = "notification.email";

    public void sendEmailMessage(EmailMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Email event sent to RabbitMQ for: " + message.getParentEmail());
    }


}
