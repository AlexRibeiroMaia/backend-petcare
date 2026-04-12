package com.petcare.pet_care.infra.rabbitMQ;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.messaging.consultation-created.queue:emailSender}")
    private String queue;

    @Value("${app.messaging.consultation-created.exchange:emailSender-ex}")
    private String exchange;

    @Value("${app.messaging.consultation-created.routing-key:emailSender-rk}")
    private String routingKey;

    @Value("${app.messaging.consultation-created.dlq.queue:emailSender.dlq}")
    private String deadLetterQueue;

    @Value("${app.messaging.consultation-created.dlq.exchange:emailSender-dlx}")
    private String deadLetterExchange;

    @Value("${app.messaging.consultation-created.dlq.routing-key:emailSender-dlq-rk}")
    private String deadLetterRoutingKey;

    @Bean
    public Queue consultationCreatedQueue() {
        return QueueBuilder.durable(queue)
                .deadLetterExchange(deadLetterExchange)
                .deadLetterRoutingKey(deadLetterRoutingKey)
                .build();
    }

    @Bean
    public Exchange consultationCreatedExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding consultationCreatedBinding(
            Queue consultationCreatedQueue,
            Exchange consultationCreatedExchange
    ) {
        return BindingBuilder.bind(consultationCreatedQueue).to(consultationCreatedExchange)
                .with(routingKey)
                .noargs();
    }

    @Bean
    public Queue consultationCreatedDeadLetterQueue() {
        return QueueBuilder.durable(deadLetterQueue).build();
    }

    @Bean
    public Exchange consultationCreatedDeadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Binding consultationCreatedDeadLetterBinding(
            Queue consultationCreatedDeadLetterQueue,
            Exchange consultationCreatedDeadLetterExchange
    ) {
        return BindingBuilder.bind(consultationCreatedDeadLetterQueue).to(consultationCreatedDeadLetterExchange)
                .with(deadLetterRoutingKey)
                .noargs();
    }

    @Bean
    public Advice rabbitRetryAdvice() {
        return RetryInterceptorBuilder.stateless()
                .maxRetries(5)
                .backOffOptions(2000, 2.0, 30000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter rabbitMessageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(rabbitMessageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter rabbitMessageConverter,
            Advice rabbitRetryAdvice
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(rabbitMessageConverter);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setDefaultRequeueRejected(false);
        factory.setAdviceChain(rabbitRetryAdvice);
        return factory;
    }
}
