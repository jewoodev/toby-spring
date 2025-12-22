package tobyspring.hellospring.domain.order;

import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Entity
@Table(name = "orders", indexes = @Index(name = "UK_orderName", columnList = "orderName", unique = true))
public class Order {
    @Id
    @GeneratedValue
    private Long orderId;

    private String orderName;

    private BigDecimal totalAmount;

    public Order(String orderName, BigDecimal totalAmount) {
        this.orderName = orderName;
        this.totalAmount = totalAmount;
    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public void beforePersist(Long orderId) {
        this.orderId = orderId;
    }
}
