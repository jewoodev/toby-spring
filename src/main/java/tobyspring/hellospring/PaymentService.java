package tobyspring.hellospring;

import org.springframework.stereotype.Component;
import tobyspring.hellospring.vo.PaymentDecimal;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foriegnCurrencyAmount) throws IOException {
        BigDecimal krwRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foriegnCurrencyAmount.multiply(krwRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        var paymentDecimal = PaymentDecimal.builder()
                .foriegnCurAmount(foriegnCurrencyAmount)
                .exRate(krwRate)
                .convertedAmount(convertedAmount)
                .build();

        return new Payment(orderId, currency, paymentDecimal, validUntil);
    }
}
