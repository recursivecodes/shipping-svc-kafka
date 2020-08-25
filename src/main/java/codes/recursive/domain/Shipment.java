package codes.recursive.domain;

import io.micronaut.core.annotation.Introspected;

import java.util.Date;

@Introspected
public class Shipment {
    private Long id;
    private Long orderId;
    private Date shippedOn;

    public Shipment(Long id, Long orderId, Date shippedOn) {
        this.id = id;
        this.orderId = orderId;
        this.shippedOn = shippedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getShippedOn() {
        return shippedOn;
    }

    public void setShippedOn(Date shippedOn) {
        this.shippedOn = shippedOn;
    }
}
