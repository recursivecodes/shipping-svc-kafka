package codes.recursive.messaging;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(offsetReset = OffsetReset.LATEST)
public class OrderConsumer {

    @Topic("order-topic")
    public void receive(@KafkaKey String key, String message, long offset, int partition, String topic, long timestamp) {

    }
}