package tobyspring.hellospring.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.application.service.OrderService;
import tobyspring.hellospring.application.service.OrderServiceImpl;
import tobyspring.hellospring.application.service.OrderServiceTxProxy;
import tobyspring.hellospring.application.service.PaymentService;
import tobyspring.hellospring.domain.payment.ExRateProvider;

@Configuration
public class ApplicationConfig {
    @Bean
    public PaymentService paymentService(ExRateProvider exRateProvider) {
        return new PaymentService(exRateProvider);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        return new OrderServiceTxProxy(
                new OrderServiceImpl(orderRepository),
                transactionManager
        );
    }
}
