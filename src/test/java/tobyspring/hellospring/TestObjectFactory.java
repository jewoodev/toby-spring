package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.PaymentService;
import tobyspring.hellospring.payment.stub.ExRateProviderStub;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class TestObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProviderStub exRateProvider() {
        return new ExRateProviderStub(new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now().plusDays(1)));
    }
}
