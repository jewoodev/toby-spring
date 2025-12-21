package tobyspring.hellospring.adapter.out.config.external;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.adapter.out.converter.JsonConverter;
import tobyspring.hellospring.adapter.out.external.exrate.WebApiExRateProvider;
import tobyspring.hellospring.application.required.api.ApiTemplate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

@Configuration
public class ExRateConfig {
    @Bean
    public ExRateProvider exRateProvider(ApiTemplate apiTemplate) {
        return new WebApiExRateProvider(apiTemplate);
    }

    @Bean
    public JsonConverter jsonConverter() {
        return new JsonConverter();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new JdkClientHttpRequestFactory());
    }
}
