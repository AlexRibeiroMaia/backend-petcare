package com.petcare.pet_care.adapters.inbound.messaging;

import com.petcare.pet_care.adapters.inbound.dtos.emailDtos.ConsultationCreatedEmailQueueDto;
import com.petcare.pet_care.adapters.outbound.service.SesEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultationCreatedEmailConsumer {

    private final SesEmailService sesEmailService;

    @RabbitListener(queues = "${app.messaging.consultation-created.queue:emailSender}")
    public void consume(ConsultationCreatedEmailQueueDto message) {
        sesEmailService.sendConsultationCreatedEmail(message);
    }
}
