package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.application.provided.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.application.provided.exrate.WebApiExRateProvider;
import tobyspring.hellospring.application.provided.exrate.stub.CachedExRateProviderStub;
import tobyspring.hellospring.application.required.api.ApiTemplate;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TestExRateConfig {
    @Bean
    public CachedExRateProviderStub cachedExRateProvider() {
        return new CachedExRateProviderStub();
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Bean
    public WebApiExRateProvider webApiExRateProvider() {
        return new WebApiExRateProvider(apiTemplate());
    }

    @Bean
    public ApiTemplate apiTemplate() {
        return new ApiTemplate();
    }

    @Bean
    public RestTemplateExRateProvider restTemplateExRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
