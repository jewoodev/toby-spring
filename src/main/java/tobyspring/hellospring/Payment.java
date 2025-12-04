package tobyspring.hellospring;

import tobyspring.hellospring.vo.PaymentDecimal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    private Long orderId;
    private String currency;
    private BigDecimal foriegnCurAmount;
    private BigDecimal exRate;
    private BigDecimal convertedAmount;
    private LocalDateTime validUntil;

    public Payment(Long orderId, String currency,
                   PaymentDecimal paymentDecimal,
                   LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foriegnCurAmount = paymentDecimal.getForiegnCurAmount();
        this.exRate = paymentDecimal.getExRate();
        this.convertedAmount = paymentDecimal.getConvertedAmount();
        this.validUntil = validUntil;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForiegnCurAmount() {
        return foriegnCurAmount;
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
                ", foriegnCurAmount=" + foriegnCurAmount +
                ", exRate=" + exRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
