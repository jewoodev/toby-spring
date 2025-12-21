package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.application.config.ApplicationConfig;
import tobyspring.hellospring.application.service.OrderService;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;

public class OrderClient {
    public static void main(String[] args)  {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var orderService = beanFactory.getBean(OrderService.class);

        Order order = orderService.createOrder("0010", BigDecimal.TEN);
        System.out.println(order);
    }
}
