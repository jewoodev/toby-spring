package tobyspring.hellospring.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.domain.payment.Payment;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;
import tobyspring.hellospring.application.provided.exrate.stub.SimpleExRateProviderStub;

import java.math.BigDecimal;
import java.time.*;

import static org.assertj.core.api.Assertions.*;

class PaymentServiceTest {
    private Clock clock;

    @BeforeEach
    void setUp() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    void checkPayments() {
        LocalDateTime validUntil = LocalDateTime.now(this.clock).plusDays(1);
        ExRate exRate1 = new ExRate(BigDecimal.valueOf(1500), validUntil);
        ExRate exRate2 = new ExRate(BigDecimal.valueOf(1000), validUntil);
        ExRate exRate3 = new ExRate(BigDecimal.valueOf(3000), validUntil);

        checkPayment(exRate1, BigDecimal.valueOf(15_000));
        checkPayment(exRate2, BigDecimal.valueOf(10_000));
        checkPayment(exRate3, BigDecimal.valueOf(30_000));
    }

    private void checkPayment(ExRate exRate, BigDecimal convertedAmount) {
        var paymentService = new PaymentService(
                new SimpleExRateProviderStub(exRate), this.clock
        );

        Payment payment = paymentService.prepare(1000L, "USD", BigDecimal.TEN);

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