package com.petcare.pet_care.infra.awsConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcare.pet_care.adapters.outbound.client.SesFeignClient;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Configuration
public class SesFeignConfig {

    @Bean
    public Region awsRegion(@Value("${AWS_REGION}") String region) {
        return Region.of(region);
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(
            @Value("${AWS_ACCESS_KEY_ID}") String accessKeyId,
            @Value("${AWS_SECRET_ACCESS_KEY}") String secretAccessKey,
            @Value("${AWS_SESSION_TOKEN:}") String sessionToken
    ) {
        if (sessionToken == null || sessionToken.isBlank()) {
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));
        }
        return StaticCredentialsProvider.create(
                AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken)
        );
    }

    @Bean
    public RequestInterceptor sesSigV4RequestInterceptor(
            AwsCredentialsProvider awsCredentialsProvider,
            Region awsRegion
    ) {
        return new SesSigV4RequestInterceptor(awsCredentialsProvider, awsRegion);
    }

    @Bean
    public SesFeignClient sesFeignClient(
            ObjectMapper objectMapper,
            RequestInterceptor sesSigV4RequestInterceptor,
            @Value("${app.aws.ses-endpoint:https://email.${AWS_REGION}.amazonaws.com}") String sesEndpoint
    ) {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .requestInterceptor(sesSigV4RequestInterceptor)
                .target(SesFeignClient.class, sesEndpoint);
    }
}
