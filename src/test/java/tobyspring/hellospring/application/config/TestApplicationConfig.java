package tobyspring.hellospring.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.adapter.out.external.exrate.stub.SimpleExRateProviderStub;
import tobyspring.hellospring.application.service.PaymentService;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Configuration
public class TestApplicationConfig {
    @Bean
    public PaymentService paymentService(SimpleExRateProviderStub simpleExRateProvider) {
        return new PaymentService(simpleExRateProvider);
    }

    @Bean
    public SimpleExRateProviderStub simpleExRateProvider(Clock clock) {
        return new SimpleExRateProviderStub(
                new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now(clock).plusDays(1))
        );
    }
}
