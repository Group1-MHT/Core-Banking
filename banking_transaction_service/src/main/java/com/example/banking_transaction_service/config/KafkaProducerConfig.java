package com.example.banking_transaction_service.config;

import com.example.banking_transaction_service.dto.TransactionDTO;
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

import java.util.HashMap;
import java.util.Map;
@Configuration
public class  KafkaProducerConfig {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("ask-balance-topic",3,(short) 1);
    }

    @Bean
    @Qualifier("transactionAskProducerFactory")
    public ProducerFactory<String, TransactionDTO> transactionAskProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Qualifier("transactionAskKafkaTemplate")
    public KafkaTemplate<String, TransactionDTO> transactionAskKafkaTemplate() {
        return new KafkaTemplate<>(transactionAskProducerFactory());
    }
}
