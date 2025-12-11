package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PaymentServiceTest {
    @Test
    void prepare() throws IOException {
        var paymentService = new PaymentService(new WebApiExRateProvider());

        Payment payment = paymentService.prepare(1000L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isNotNull();

        assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));

        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusDays(2));
    }
}