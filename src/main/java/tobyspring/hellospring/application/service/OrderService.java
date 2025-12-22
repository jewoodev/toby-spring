package tobyspring.hellospring.application.service;

import tobyspring.hellospring.application.service.vo.OrderRequest;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrder(String orderName, BigDecimal totalAmount);
    List<Order> createOrders(List<OrderRequest> requests);
    Order getOrder(Long orderId);
    void truncate();
}
