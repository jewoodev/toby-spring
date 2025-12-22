package tobyspring.hellospring.adapter.out.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.GlobalTestConfig;
import tobyspring.hellospring.adapter.out.config.external.ApiConfig;
import tobyspring.hellospring.adapter.out.config.external.TestExRateConfig;
import tobyspring.hellospring.adapter.out.external.exrate.WebApiExRateProvider;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestExRateConfig.class, ApiConfig.class, GlobalTestConfig.class})
class WebApiExRateProviderTest {
    @Autowired
    private WebApiExRateProvider provider;

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