package tobyspring.hellospring.exrate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;
import tobyspring.hellospring.exrate.stub.CachedExRateProviderStub;
import tobyspring.hellospring.exrate.vo.ExRate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPaymentConfig.class})
class CachedExRateProviderTest {

    @Autowired
    private CachedExRateProviderStub exRateProvider;

    @Autowired
    private Clock clock;

    @Test
    public void useCache() throws InterruptedException {
        exRateProvider.getExRate("USD");
        TimeUnit.SECONDS.sleep(1);
        assertThat(exRateProvider.getExRate("USD")).isTrue();
    }

    @Test
    public void canNotUseCache() {
        ExRate exRate = new ExRate(BigDecimal.valueOf(1500), LocalDateTime.now(this.clock));
        exRateProvider.setExRate(exRate);
        assertThat(exRateProvider.getExRate("USD")).isFalse();
    }
}