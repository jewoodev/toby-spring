package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.adapter.out.config.PersistenceConfig;
import tobyspring.hellospring.adapter.out.config.external.ApiConfig;
import tobyspring.hellospring.adapter.out.config.external.ExRateConfig;
import tobyspring.hellospring.application.config.ApplicationConfig;
import tobyspring.hellospring.application.service.PaymentService;
import tobyspring.hellospring.domain.payment.Payment;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args)  {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(
                ApiConfig.class, ExRateConfig.class, ApplicationConfig.class, PersistenceConfig.class
        );
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment1);
    }
}
