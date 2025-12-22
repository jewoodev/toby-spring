package tobyspring.hellospring.adapter.out.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.GlobalTestConfig;
import tobyspring.hellospring.adapter.out.config.external.ApiConfig;
import tobyspring.hellospring.adapter.out.config.external.TestExRateConfig;
import tobyspring.hellospring.adapter.out.external.exrate.stub.CachedExRateProviderStub;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestExRateConfig.class, ApiConfig.class, GlobalTestConfig.class})
class CachedExRateProviderTest {

    @Autowired
    private CachedExRateProviderStub exRateProvider;

    @Autowired
    private Clock clock;

    @Test
    void useCache() throws InterruptedException {
        exRateProvider.setExRate(new ExRate(BigDecimal.valueOf(1500), LocalDateTime.now(this.clock).plusDays(1)));
        exRateProvider.getExRate("USD");
        TimeUnit.SECONDS.sleep(1);
        assertThat(exRateProvider.getExRate("USD")).isTrue();
    }

    @Test
    void canNotUseCache() {
        ExRate exRate = new ExRate(BigDecimal.valueOf(1500), LocalDateTime.now(this.clock));
        exRateProvider.setExRate(exRate);
        assertThat(exRateProvider.getExRate("USD")).isFalse();
    }
}