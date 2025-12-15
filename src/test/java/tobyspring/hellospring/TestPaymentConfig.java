package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.exrate.stub.CachedExRateProviderStub;
import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.PaymentService;
import tobyspring.hellospring.exrate.stub.SimpleExRateProviderStub;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public SimpleExRateProviderStub exRateProvider() {
        return new SimpleExRateProviderStub(
                new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now(clock()).plusDays(1))
        );
    }

    @Bean
    public CachedExRateProviderStub cachedExRateProvider() {
        return new CachedExRateProviderStub(
                LocalDateTime.now(clock()).plusDays(1)
        );
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
