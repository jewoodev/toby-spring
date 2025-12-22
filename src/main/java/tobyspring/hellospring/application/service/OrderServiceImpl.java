package tobyspring.hellospring.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.application.service.vo.OrderRequest;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Order createOrder(String orderName, BigDecimal totalAmount) {
        var order = new Order(orderName, totalAmount);

        this.orderRepository.save(order);
        return order;
    }

    @Transactional
    @Override
    public List<Order> createOrders(List<OrderRequest> requests) {
        return requests.stream().map(request ->
                        this.createOrder(request.orderName(), request.totalAmount()))
                .toList();
    }

    public Order getOrder(Long orderId) {
        return this.orderRepository.get(orderId);
    }

    public void truncate() {
        this.orderRepository.truncate();
    }
}
