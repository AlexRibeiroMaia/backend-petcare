package com.petcare.pet_care.adapters.outbound.client;

import feign.Headers;
import feign.RequestLine;

import java.util.Map;

public interface SesFeignClient {
    @RequestLine("POST /v2/email/outbound-emails")
    @Headers("Content-Type: application/json")
    Map<String, Object> sendEmail(Map<String, Object> body);
}
