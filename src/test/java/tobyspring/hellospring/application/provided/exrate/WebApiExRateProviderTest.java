package tobyspring.hellospring.application.provided.exrate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.domain.payment.vo.ExRate;
import tobyspring.hellospring.TestExRateConfig;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestExRateConfig.class})
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