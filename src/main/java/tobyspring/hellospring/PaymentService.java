package tobyspring.hellospring;

import tobyspring.hellospring.vo.PaymentDecimal;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

abstract public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foriegnCurrencyAmount) throws IOException {
        BigDecimal krwRate = getExRate(currency);
        BigDecimal convertedAmount = foriegnCurrencyAmount.multiply(krwRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        var paymentDecimal = PaymentDecimal.builder()
                .foriegnCurAmount(foriegnCurrencyAmount)
                .exRate(krwRate)
                .convertedAmount(convertedAmount)
                .build();

        return new Payment(orderId, currency, paymentDecimal, validUntil);
    }

    abstract public BigDecimal getExRate(String currency) throws IOException;
}
