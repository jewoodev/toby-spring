package tobyspring.hellospring.adapter.out.config.external;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.adapter.out.converter.JsonConverter;
import tobyspring.hellospring.adapter.out.external.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.adapter.out.external.exrate.WebApiExRateProvider;
import tobyspring.hellospring.adapter.out.external.exrate.stub.CachedExRateProviderStub;
import tobyspring.hellospring.application.required.api.ApiTemplate;

@Configuration
public class TestExRateConfig {
    @Bean
    public CachedExRateProviderStub cachedExRateProvider() {
        return new CachedExRateProviderStub();
    }

    @Bean
    public WebApiExRateProvider webApiExRateProvider(ApiTemplate apiTemplate) {
        return new WebApiExRateProvider(apiTemplate);
    }

    @Bean
    public JsonConverter jsonConverter() {
        return new JsonConverter();
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
