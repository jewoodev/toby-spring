package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.stub.ExRateProviderStub;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PaymentServiceTest {
    @Test
    void checkPayments() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        ExRate exRate1 = new ExRate(BigDecimal.valueOf(1500), now.plusDays(1));
        ExRate exRate2 = new ExRate(BigDecimal.valueOf(1000), now.plusDays(1));
        ExRate exRate3 = new ExRate(BigDecimal.valueOf(3000), now.plusDays(1));

        checkPayment(exRate1, BigDecimal.valueOf(15_000));
        checkPayment(exRate2, BigDecimal.valueOf(10_000));
        checkPayment(exRate3, BigDecimal.valueOf(30_000));
    }

    private static void checkPayment(ExRate exRate, BigDecimal convertedAmount) throws IOException {
        var paymentService = new PaymentService(
                new ExRateProviderStub(exRate)
        );

        Payment payment = paymentService.prepare(1000L, "USD", BigDecimal.TEN);

        // 환율정보 검증
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate.value());

        // 원화환산금액 검증
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);

        // 환율 유효기간 검증
        assertThat(payment.getValidUntil()).isEqualTo(exRate.nextUpdateAt().minusSeconds(1));
    }
}