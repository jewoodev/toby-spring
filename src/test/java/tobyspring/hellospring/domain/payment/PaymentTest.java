package tobyspring.hellospring.domain.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;
import tobyspring.hellospring.adapter.exrate.stub.SimpleExRateProviderStub;
import tobyspring.hellospring.adapter.exrate.vo.ExRate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPaymentConfig.class})
class PaymentTest {
    @Autowired
    private Clock clock;

    @Autowired
    private SimpleExRateProviderStub exRateProvider;

    @Test
    void createPrepared() {
        exRateProvider.setExRate(new ExRate(BigDecimal.TEN, LocalDateTime.now(clock)));
        var payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, exRateProvider);

        BigDecimal exRateValue = exRateProvider.getExRate().value();

        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.TEN.multiply(exRateValue));
        assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(this.clock).minusSeconds(1));
    }

    @Test
    void isValid() {
        exRateProvider.setExRate(new ExRate(BigDecimal.TEN, LocalDateTime.now(clock).plusDays(1)));
        var payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, exRateProvider);

        assertThat(payment.isValid()).isTrue();
    }

    @Test
    void notValid() {
        exRateProvider.setExRate(new ExRate(BigDecimal.TEN, LocalDateTime.now(clock).minusDays(1)));
        var payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, exRateProvider);

        assertThat(payment.isValid()).isFalse();
    }
}