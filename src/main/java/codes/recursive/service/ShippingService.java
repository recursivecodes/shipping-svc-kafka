package codes.recursive.service;

import codes.recursive.domain.Order;
import codes.recursive.domain.Shipment;
import codes.recursive.messaging.ShipmentProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Singleton
public class ShippingService {
    private static final Logger LOG = LoggerFactory.getLogger(ShippingService.class);
    
    private final ShipmentProducer shipmentProducer;
    private List<Shipment> shipments = new ArrayList<>();

    public ShippingService(ShipmentProducer shipmentProducer) {
        this.shipmentProducer = shipmentProducer;
    }

    public Shipment getShipmentById(Long id) {
        return shipments.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Shipment> listShipments() {
        return shipments;
    }

    public void updateShipment(Shipment shipment) {
        Shipment existingShipment = getShipmentById(shipment.getId());
        int i = shipments.indexOf(existingShipment);
        shipments.set(i, shipment);
    }
    
    public Shipment newShipment(Order order) {
        Shipment shipment = new Shipment((long) shipments.size(), order.getId(), new Date());
        shipments.add(shipment);
        LOG.info("Shipment created!");
        LOG.info("Sending shipment message...");
        shipmentProducer.sendMessage(UUID.randomUUID().toString(), shipment);
        LOG.info("Shipment message sent!");
        return shipment;
    }

}
