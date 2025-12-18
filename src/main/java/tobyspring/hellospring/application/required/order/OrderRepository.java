package tobyspring.hellospring.application.required.order;

import tobyspring.hellospring.domain.order.Order;

public interface OrderRepository {
    void save(Order order);
}
