package tobyspring.hellospring.vo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentDecimal(
        BigDecimal foriegnCurAmount,
        BigDecimal exRate,
        BigDecimal convertedAmount
) {
}
