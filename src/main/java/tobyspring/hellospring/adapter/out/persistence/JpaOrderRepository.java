package tobyspring.hellospring.adapter.out.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.domain.order.Order;

public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Order order) {
        em.persist(order);
    }

    @Override
    public Order get(Long orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    @Query(value = "TRUNCATE TABLE orders", nativeQuery = true)
    public void truncate() {
    }
}
