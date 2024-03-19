package com.example.balancelogservice.Config;

import com.example.balancelogservice.Dto.TransactionResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TransactionKafkaProducerConfig {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("transaction-topic",3,(short) 1);
    }

    @Bean
    @Qualifier("transactionProducerFactory")
    public ProducerFactory<String, TransactionResponse> transactionProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Qualifier("transactionKafkaTemplate")
    public KafkaTemplate<String, TransactionResponse> transactionKafkaTemplate() {
        return new KafkaTemplate<>(transactionProducerFactory());
    }
}
