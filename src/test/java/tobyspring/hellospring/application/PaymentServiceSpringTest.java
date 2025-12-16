package tobyspring.hellospring.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;
import tobyspring.hellospring.domain.payment.Payment;
import tobyspring.hellospring.adapter.exrate.vo.ExRate;
import tobyspring.hellospring.adapter.exrate.stub.SimpleExRateProviderStub;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPaymentConfig.class})
class PaymentServiceSpringTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SimpleExRateProviderStub exRateProvider;

    @Autowired
    private Clock clock;

    @Test
    void checkPayments() {
        LocalDateTime now = LocalDateTime.now();

        var exRate1 = new ExRate(BigDecimal.valueOf(1500), now.plusDays(1));
        var exRate2 = new ExRate(BigDecimal.valueOf(1000), now.plusDays(1));
        var exRate3 = new ExRate(BigDecimal.valueOf(3000), now.plusDays(1));

        exRateProvider.setExRate(exRate1);
        checkPayment(BigDecimal.valueOf(15_000));
        exRateProvider.setExRate(exRate2);
        checkPayment(BigDecimal.valueOf(10_000));
        exRateProvider.setExRate(exRate3);
        checkPayment(BigDecimal.valueOf(30_000));
    }

    private void checkPayment(BigDecimal convertedAmount) {
        Payment payment = paymentService.prepare(1000L, "USD", BigDecimal.TEN);
        ExRate exRate = exRateProvider.getExRate();

        // 환율정보 검증
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate.value());

        // 원화환산금액 검증
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);

        // 환율 유효기간 검증
        assertThat(payment.getValidUntil()).isEqualTo(exRate.nextUpdateAt().minusSeconds(1));
    }

    @Test
    void validUntil() {
        LocalDateTime validUntil = LocalDateTime.now(this.clock).plusDays(1);
        ExRate exRate = new ExRate(BigDecimal.valueOf(1500), validUntil);
        var paymentService = new PaymentService(
                new SimpleExRateProviderStub(exRate), this.clock
        );

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime expected = LocalDateTime.now(this.clock).plusDays(1).minusSeconds(1);

        assertThat(payment.getValidUntil()).isEqualTo(expected);
    }
}