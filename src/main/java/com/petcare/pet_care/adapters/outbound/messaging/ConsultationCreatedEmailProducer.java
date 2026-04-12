package com.petcare.pet_care.adapters.outbound.messaging;

import com.petcare.pet_care.adapters.inbound.dtos.emailDtos.ConsultationCreatedEmailQueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultationCreatedEmailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.messaging.consultation-created.exchange:emailSender-ex}")
    private String exchange;

    @Value("${app.messaging.consultation-created.routing-key:emailSender-rk}")
    private String routingKey;

    public void publish(ConsultationCreatedEmailQueueDto message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
