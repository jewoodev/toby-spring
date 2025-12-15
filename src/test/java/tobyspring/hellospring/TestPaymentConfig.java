package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.PaymentService;
import tobyspring.hellospring.payment.stub.ExRateProviderStub;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProviderStub exRateProvider() {
        return new ExRateProviderStub(new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now(clock()).plusDays(1)));
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
