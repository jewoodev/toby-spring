package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.application.required.api.ApiExecutor;
import tobyspring.hellospring.adapter.SimpleApiExecutor;
import tobyspring.hellospring.application.provided.exrate.CachedExRateProvider;
import tobyspring.hellospring.domain.payment.ExRateProvider;
import tobyspring.hellospring.application.provided.exrate.WebApiExRateProvider;
import tobyspring.hellospring.application.PaymentService;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

    @Bean
    public CachedExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Bean
    public ApiExecutor apiExecutor() {
        return new SimpleApiExecutor();
    }
}
