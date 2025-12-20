package tobyspring.hellospring.adapter.out.config.external;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.adapter.out.converter.JsonConverter;
import tobyspring.hellospring.adapter.out.external.api.HttpClientApiExecutor;
import tobyspring.hellospring.adapter.out.external.api.HttpExRateApiTemplate;
import tobyspring.hellospring.application.required.api.ApiExecutor;
import tobyspring.hellospring.application.required.api.ApiTemplate;

@Configuration
public class ApiConfig {
    @Bean
    public ApiExecutor apiExecutor() {
        return new HttpClientApiExecutor();
    }

    @Bean
    ApiTemplate exRateApiTemplate(ApiExecutor apiExecutor, JsonConverter jsonConverter) {
        return new HttpExRateApiTemplate(apiExecutor, jsonConverter);
    }
}
