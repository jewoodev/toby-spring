package tobyspring.hellospring.vo;

import lombok.Builder;

import java.math.BigDecimal;

public class PaymentDecimal {
    private BigDecimal foriegnCurAmount;
    private BigDecimal exRate;
    private BigDecimal convertedAmount;

    @Builder
    public PaymentDecimal(BigDecimal foriegnCurAmount, BigDecimal exRate, BigDecimal convertedAmount) {
        this.foriegnCurAmount = foriegnCurAmount;
        this.exRate = exRate;
        this.convertedAmount = convertedAmount;
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
}
