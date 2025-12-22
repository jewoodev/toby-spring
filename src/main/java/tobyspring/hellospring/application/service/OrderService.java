package tobyspring.hellospring.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.application.service.vo.OrderRequest;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PlatformTransactionManager transactionManager;

    public OrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String orderName, BigDecimal totalAmount) {
        var order = new Order(orderName, totalAmount);

        this.orderRepository.save(order);
        return order;
    }

    public List<Order> createOrders(List<OrderRequest> requests) {

        return new TransactionTemplate(this.transactionManager).execute(status ->
                requests.stream().map(request -> this.createOrder(request.orderName(), request.totalAmount()))
                .toList());
    }

    public Order getOrder(Long orderId) {
        return this.orderRepository.get(orderId);
    }

    public void truncate() {
        this.orderRepository.truncate();
    }
}
