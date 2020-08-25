package codes.recursive.messaging;
import codes.recursive.domain.Order;
import codes.recursive.service.ShippingService;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Single;

@KafkaListener(offsetReset = OffsetReset.LATEST)
public class OrderConsumer {

    private final ShippingService shippingService;

    public OrderConsumer(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Topic("order-topic")
    public Single<Order> receive(
            @KafkaKey String key,
            Single<Order> orderFlowable,
            long offset,
            int partition,
            String topic,
            long timestamp) {

        return orderFlowable.doOnSuccess(order -> {
            System.out.printf("Order with key %s received at %s!%n", key, timestamp);
            System.out.println("Creating shipment...");
            shippingService.newShipment(order);
            System.out.println("Shipment created!");
        });
    }
}