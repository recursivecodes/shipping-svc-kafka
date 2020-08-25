package codes.recursive.service;

import codes.recursive.domain.Order;
import codes.recursive.domain.Shipment;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class ShippingService {

    private List<Shipment> shipments = new ArrayList<>();

    public ShippingService() {
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
        return shipment;
    }

}
