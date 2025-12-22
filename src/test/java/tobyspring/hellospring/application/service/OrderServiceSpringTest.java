package tobyspring.hellospring.application.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.adapter.out.config.PersistenceConfig;
import tobyspring.hellospring.adapter.out.config.external.ApiConfig;
import tobyspring.hellospring.adapter.out.config.external.ExRateConfig;
import tobyspring.hellospring.application.config.ApplicationConfig;
import tobyspring.hellospring.application.service.vo.OrderRequest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ApplicationConfig.class, PersistenceConfig.class, ExRateConfig.class, ApiConfig.class
})
public class OrderServiceSpringTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DataSource dataSource;

    @AfterEach
    void tearDown() {
        this.orderService.truncate();
    }

    @Test
    void createOrder() {
        var order = this.orderService.createOrder("test", BigDecimal.valueOf(1000));
        var found = this.orderService.getOrder(order.getOrderId());
        assertNotNull(found);
        assertEquals(1L , found.getOrderId());
        assertThat(order.getTotalAmount()).isEqualByComparingTo(found.getTotalAmount());
    }

    @Test
    void createOrders() {
        var orderRequests = List.of(
                new OrderRequest("test", BigDecimal.valueOf(1000)),
                new OrderRequest("test2", BigDecimal.valueOf(2000))
        );

        var createdOrders = this.orderService.createOrders(orderRequests);
        assertThat(createdOrders).hasSize(2);
        createdOrders.forEach(order -> {
            assertThat(order.getOrderId()).isGreaterThan(0);
            assertThat(order.getTotalAmount()).isGreaterThan(BigDecimal.ZERO);
        });
    }

    @Test
    void createDuplicatedOrders() {
        var orderRequests = List.of(
                new OrderRequest("test", BigDecimal.valueOf(1000)),
                new OrderRequest("test", BigDecimal.valueOf(2000))
        );

        assertThatThrownBy(() -> this.orderService.createOrders(orderRequests))
                .isInstanceOf(DuplicateKeyException.class);

        var jdbcClient = JdbcClient.create(dataSource);

        var cnt = jdbcClient.sql("SELECT COUNT(*) FROM orders WHERE orderName = 'test'")
                .query(Long.class)
                .single();
        assertEquals(0L, cnt);
    }
}
