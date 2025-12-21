package tobyspring.hellospring.application.service;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final JpaTransactionManager transactionManager;

    public OrderService(OrderRepository orderRepository, JpaTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String orderName, BigDecimal totalAmount) {
        var order = new Order(orderName, totalAmount);

        return new TransactionTemplate(transactionManager).execute(status -> {
            this.orderRepository.save(order);
            return order;
        });
    }
}
