package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.domain.order.Order;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args)  {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        var orderRepository = beanFactory.getBean(OrderRepository.class);
        var transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        try {
            new TransactionTemplate(transactionManager).execute(status -> {
                var order = new Order("100", BigDecimal.TEN);
                orderRepository.save(order);

                var dupNameOrder = new Order("100", BigDecimal.ONE);
                orderRepository.save(dupNameOrder);
                return null;
            });
        } catch (DataIntegrityViolationException e) {
            System.out.println("Duplicated order's name repairing job");
        }
    }
}
