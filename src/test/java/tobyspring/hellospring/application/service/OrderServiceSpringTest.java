package tobyspring.hellospring.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.adapter.out.config.PersistenceConfig;
import tobyspring.hellospring.adapter.out.config.external.ApiConfig;
import tobyspring.hellospring.adapter.out.config.external.ExRateConfig;
import tobyspring.hellospring.application.config.ApplicationConfig;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ApplicationConfig.class, PersistenceConfig.class, ExRateConfig.class, ApiConfig.class
})
public class OrderServiceSpringTest {
    @Autowired
    private OrderService orderService;

    @Test
    void createOrder() {
        Order order = orderService.createOrder("test", BigDecimal.valueOf(1000));
        assertNotNull(order);
        assertEquals(1L , order.getOrderId());
        assertEquals(BigDecimal.valueOf(1000), order.getTotalAmount());
    }
}
