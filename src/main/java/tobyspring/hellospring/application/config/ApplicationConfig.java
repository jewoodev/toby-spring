package tobyspring.hellospring.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.application.service.OrderService;
import tobyspring.hellospring.application.service.OrderServiceImpl;
import tobyspring.hellospring.application.service.PaymentService;
import tobyspring.hellospring.domain.payment.ExRateProvider;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
    @Bean
    public PaymentService paymentService(ExRateProvider exRateProvider) {
        return new PaymentService(exRateProvider);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new OrderServiceImpl(orderRepository);
    }
}
