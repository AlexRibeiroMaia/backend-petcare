package com.petcare.pet_care.adapters.outbound.service;

import com.petcare.pet_care.adapters.inbound.dtos.emailDtos.ConsultationCreatedEmailQueueDto;
import com.petcare.pet_care.adapters.outbound.client.SesFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SesEmailService {

    private final SesFeignClient sesFeignClient;

    @Value("${app.email.from:${SES_FROM_EMAIL}}")
    private String fromEmail;

    public void sendConsultationCreatedEmail(ConsultationCreatedEmailQueueDto message) {
        if (fromEmail == null || fromEmail.isBlank()) {
            throw new IllegalStateException("SES_FROM_EMAIL is required to send emails via AWS SES");
        }

        if (message.getTutorEmail() == null || message.getTutorEmail().isBlank()) {
            throw new IllegalArgumentException("Tutor email is required to send consultation confirmation");
        }

        Map<String, Object> request = Map.of(
                "FromEmailAddress", fromEmail,
                "Destination", Map.of(
                        "ToAddresses", List.of(message.getTutorEmail())
                ),
                "Content", Map.of(
                        "Simple", Map.of(
                                "Subject", Map.of("Data", "Confirmacao de consulta - PetCare"),
                                "Body", Map.of(
                                        "Text", Map.of("Data", buildBody(message))
                                )
                        )
                )
        );

        sesFeignClient.sendEmail(request);
    }

    private String buildBody(ConsultationCreatedEmailQueueDto message) {
        return """
                Ola!

                Sua consulta foi criada com sucesso.

                ID da consulta: %s
                Data da consulta: %s
                Pet: %s

                Equipe PetCare
                """.formatted(
                message.getConsultationId(),
                message.getConsultationDate(),
                message.getPetName() != null ? message.getPetName() : message.getPetId()
        );
    }
}
