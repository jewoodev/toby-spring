package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.adapter.ErApiExRateExtractor;
import tobyspring.hellospring.adapter.SimpleApiExecutor;
import tobyspring.hellospring.application.PaymentService;
import tobyspring.hellospring.application.provided.exrate.CachedExRateProvider;
import tobyspring.hellospring.application.provided.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.application.required.api.ApiTemplate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

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
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Bean
    public CachedExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public ApiTemplate apiTemplate() {
        return new ApiTemplate(new SimpleApiExecutor(), new ErApiExRateExtractor());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new JdkClientHttpRequestFactory());
    }
}
