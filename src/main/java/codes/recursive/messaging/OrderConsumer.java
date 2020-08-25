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

import java.util.UUID;

@KafkaListener(offsetReset = OffsetReset.LATEST)
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    private final ShippingService shippingService;
    private final ShipmentProducer shipmentProducer;

    public OrderConsumer(ShippingService shippingService, ShipmentProducer shipmentProducer) {
        this.shippingService = shippingService;
        this.shipmentProducer = shipmentProducer;
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
            LOG.info("Shipment created!");
            LOG.info("Sending shipment message...");
            shipmentProducer.sendMessage(UUID.randomUUID().toString(), shipment);
            LOG.info("Shipment message sent!");
        });
    }
}