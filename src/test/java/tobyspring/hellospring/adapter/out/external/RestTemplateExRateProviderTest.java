package tobyspring.hellospring.adapter.out.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.adapter.out.config.external.TestApiConfig;
import tobyspring.hellospring.adapter.out.config.external.TestExRateConfig;
import tobyspring.hellospring.adapter.out.external.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestExRateConfig.class, TestApiConfig.class})
class RestTemplateExRateProviderTest {
    @Autowired
    private RestTemplateExRateProvider provider;

    @Autowired
    private Clock clock;

    @Test
    void validateApi() {
        ExRate exRate = provider.getExRate("USD");

        assertThat(exRate.value()).isNotNull();
        assertThat(exRate.nextUpdateAt().getDayOfMonth())
                .isEqualTo(LocalDateTime.now(clock).plusDays(1).getDayOfMonth());
    }
}