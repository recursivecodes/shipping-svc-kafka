package codes.recursive.messaging;

import codes.recursive.domain.Order;
import codes.recursive.domain.Shipment;
import codes.recursive.service.ShippingService;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(offsetReset = OffsetReset.LATEST)
public class OrderConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);
    private final ShippingService shippingService;

    public OrderConsumer(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Topic("order-topic")
    public Single<Order> receive(
            @KafkaKey String key,
            Single<Order> orderFlowable) {

        return orderFlowable.doOnSuccess(order -> {
            LOG.info("Order with key {} received!", key);
            LOG.info("Creating shipment...");
            /* shipping is slow! */
            Thread.sleep(15*1000);
            Shipment shipment = shippingService.newShipment(order);
        });
    }
}