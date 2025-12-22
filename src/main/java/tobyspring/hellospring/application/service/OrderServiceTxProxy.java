package tobyspring.hellospring.application.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.application.service.vo.OrderRequest;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceTxProxy implements OrderService {
    private final OrderService target;
    private final PlatformTransactionManager transactionManager;

    public OrderServiceTxProxy(OrderService orderService, PlatformTransactionManager transactionManager) {
        this.target = orderService;
        this.transactionManager = transactionManager;
    }

    @Override
    public Order createOrder(String orderName, BigDecimal totalAmount) {
        return new TransactionTemplate(this.transactionManager)
                .execute(status -> target.createOrder(orderName, totalAmount));
    }

    @Override
    public List<Order> createOrders(List<OrderRequest> requests) {
        return new TransactionTemplate(this.transactionManager)
                .execute(status -> target.createOrders(requests));
    }

    @Override
    public Order getOrder(Long orderId) {
        return target.getOrder(orderId);
    }

    @Override
    public void truncate() {
        target.truncate();
    }
}
