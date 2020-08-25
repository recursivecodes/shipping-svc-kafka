package codes.recursive.messaging;

import codes.recursive.domain.Shipment;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface ShipmentProducer {
    @Topic("shipping-topic")
    void sendMessage(@KafkaKey String key, Shipment shipment);
}