package tobyspring.hellospring.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.adapter.out.external.exrate.CachedExRateProvider;
import tobyspring.hellospring.application.service.PaymentService;

import java.time.Clock;

@Configuration
public class ApplicationConfig {
    @Bean
    public PaymentService paymentService(CachedExRateProvider cachedExRateProvider, Clock clock) {
        return new PaymentService(cachedExRateProvider, clock);
    }
}
