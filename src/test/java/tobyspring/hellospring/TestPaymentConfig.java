package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;
import tobyspring.hellospring.application.PaymentService;
import tobyspring.hellospring.application.provided.exrate.stub.SimpleExRateProviderStub;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(simpleExRateProvider(), clock());
    }

    @Bean
    public SimpleExRateProviderStub simpleExRateProvider() {
        return new SimpleExRateProviderStub(
                new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now(clock()).plusDays(1))
        );
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
