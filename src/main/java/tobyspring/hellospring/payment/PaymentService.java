package tobyspring.hellospring.payment;

import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.vo.PaymentDecimal;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class PaymentService {
    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        ExRate exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate.value());
        LocalDateTime validUntil = exRate.nextUpdateAt().minusSeconds(1);

        var paymentDecimal = PaymentDecimal.builder()
                .foreignCurAmount(foreignCurrencyAmount)
                .exRate(exRate.value())
                .convertedAmount(convertedAmount)
                .build();

        return new Payment(orderId, currency, paymentDecimal, validUntil);
    }
}
