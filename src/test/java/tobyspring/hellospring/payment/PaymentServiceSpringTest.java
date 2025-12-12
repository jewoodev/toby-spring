package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestObjectFactory;
import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.stub.ExRateProviderStub;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestObjectFactory.class})
class PaymentServiceSpringTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ExRateProviderStub exRateProvider;

    @Test
    void checkPayments() throws IOException {
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

    private void checkPayment(BigDecimal convertedAmount) throws IOException {
        Payment payment = paymentService.prepare(1000L, "USD", BigDecimal.TEN);
        ExRate exRate = exRateProvider.getExRate();

        // 환율정보 검증
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate.value());

        // 원화환산금액 검증
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);

        // 환율 유효기간 검증
        assertThat(payment.getValidUntil()).isEqualTo(exRate.nextUpdateAt().minusSeconds(1));
    }
}