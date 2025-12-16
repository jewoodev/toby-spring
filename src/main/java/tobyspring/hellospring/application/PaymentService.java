package tobyspring.hellospring.application;

import tobyspring.hellospring.domain.payment.ExRateProvider;
import tobyspring.hellospring.domain.payment.Payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;

public class PaymentService {
    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRateProvider);
    }
}
