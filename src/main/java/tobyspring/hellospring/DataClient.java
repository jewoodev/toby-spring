package tobyspring.hellospring;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args)  {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        var emf = beanFactory.getBean(EntityManagerFactory.class);

        var em = emf.createEntityManager();

        em.getTransaction().begin();

        var order = new Order("100", BigDecimal.TEN);
        System.out.println(order);

        em.persist(order);

//        var dpcOrdName = new Order("100", BigDecimal.TEN);
//        em.persist(dpcOrdName);

        System.out.println(order);

        em.getTransaction().commit();
        em.close();
    }
}
