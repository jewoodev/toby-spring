package tobyspring.hellospring;

import java.math.BigDecimal;

public class SimpleExRatePaymentService {
    public BigDecimal getWebExRate(String currency) {
        if (currency.equals("USD")) return BigDecimal.valueOf(1000);

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
