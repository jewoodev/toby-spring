package tobyspring.hellospring;

import tobyspring.hellospring.vo.ExRate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SimpleExRateProvider implements ExRateProvider {
    @Override
    public ExRate getExRate(String currency) {
        if (currency.equals("USD")) {
            return new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now().plusMinutes(30));
        }

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
