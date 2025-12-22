package tobyspring.hellospring.adapter.out.persistence;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.domain.order.Order;

import javax.sql.DataSource;

public class JdbcOrderRepository implements OrderRepository {
    private final JdbcClient jdbcClient;

    public JdbcOrderRepository(DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @PostConstruct
    void ininDb() {
        jdbcClient.sql("""
            CREATE TABLE orders (orderId bigint NOT NULL, orderName VARCHAR(255), totalAmount NUMERIC(38, 2), PRIMARY KEY (orderId));
            ALTER TABLE if EXISTS orders DROP CONSTRAINT if EXISTS UK_orderName;
            ALTER TABLE if EXISTS orders ADD CONSTRAINT UK_orderName UNIQUE (orderName);
            CREATE SEQUENCE orders_SEQ START WITH 1 INCREMENT BY 50;
        """).update();
    }

    @Override
    public void save(Order order) {
        var orderSeq = jdbcClient.sql("SELECT NEXT VALUE FOR orders_SEQ").query(Long.class).single();
        order.beforePersist(orderSeq);

        jdbcClient.sql("INSERT INTO orders (orderId, orderName, totalAmount) VALUES (?,?,?)")
                        .params(order.getOrderId(), order.getOrderName(), order.getTotalAmount())
                                .update();
    }

    public Order get(Long orderId) {
        return jdbcClient.sql("SELECT * FROM orders WHERE orderId = ?")
                .param(orderId)
                .query(Order.class)
                .single();
    }

    public void truncate() {
        jdbcClient.sql("TRUNCATE TABLE orders").update();
    }
}
