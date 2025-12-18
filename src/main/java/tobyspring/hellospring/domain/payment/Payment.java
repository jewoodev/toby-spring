package tobyspring.hellospring.domain.payment;

import tobyspring.hellospring.domain.payment.vo.ExRate;
import tobyspring.hellospring.domain.payment.vo.PaymentDecimal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private Long orderId;
    private String currency;
    private BigDecimal foreignCurrencyAmount;
    private BigDecimal exRate;
    private BigDecimal convertedAmount;
    private LocalDateTime validUntil;

    private Payment(Long orderId, String currency,
                   PaymentDecimal paymentDecimal,
                   LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = paymentDecimal.foreignCurAmount();
        this.exRate = paymentDecimal.exRate();
        this.convertedAmount = paymentDecimal.convertedAmount();
        this.validUntil = validUntil;
    }

    public static Payment createPrepared(Long orderId, String currency, BigDecimal foreignCurrencyAmount,
                                         ExRateProvider exRateProvider) {
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

    public boolean isValid() {
        return this.validUntil.isAfter(LocalDateTime.now());
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
