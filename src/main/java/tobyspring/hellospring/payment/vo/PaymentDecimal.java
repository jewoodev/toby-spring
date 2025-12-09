package tobyspring.hellospring.payment.vo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentDecimal(
        BigDecimal foreignCurAmount,
        BigDecimal exRate,
        BigDecimal convertedAmount
) {
}
