package tobyspring.hellospring.application.service;

import tobyspring.hellospring.domain.payment.ExRateProvider;
import tobyspring.hellospring.domain.payment.Payment;

import java.math.BigDecimal;
import java.time.Clock;

public class PaymentService {
    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRateProvider);
    }
}
