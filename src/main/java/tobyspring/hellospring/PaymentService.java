package tobyspring.hellospring;

import tobyspring.hellospring.vo.ExRate;
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
        ExRate exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foriegnCurrencyAmount.multiply(exRate.value());
        LocalDateTime validUntil = exRate.nextUpdateAt().minusSeconds(1);

        var paymentDecimal = PaymentDecimal.builder()
                .foriegnCurAmount(foriegnCurrencyAmount)
                .exRate(exRate.value())
                .convertedAmount(convertedAmount)
                .build();

        return new Payment(orderId, currency, paymentDecimal, validUntil);
    }
}
