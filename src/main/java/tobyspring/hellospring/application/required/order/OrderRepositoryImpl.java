package tobyspring.hellospring.application.required.order;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import tobyspring.hellospring.domain.order.Order;

public class OrderRepositoryImpl implements OrderRepository {
    private final EntityManagerFactory emf;

    public OrderRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Order order) {
        var em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        System.out.println("Transaction : " + transaction);

        transaction.begin();
        try {
            em.persist(order);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
                System.out.println("Rollback!");
            }
            throw e;
        } finally {
            if (transaction.isActive()) em.close();
        }
    }
}
