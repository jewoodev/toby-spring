package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;

public class DataClient {
    private final OrderRepository orderRepository;

    public DataClient(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public static void main(String[] args)  {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        var orderRepository = beanFactory.getBean(OrderRepository.class);

        var order = new Order("100", BigDecimal.TEN);
        orderRepository.save(order);

        var dupNameOrder = new Order("100", BigDecimal.ONE);
        orderRepository.save(dupNameOrder);
    }
}
