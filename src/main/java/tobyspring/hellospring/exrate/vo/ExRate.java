package tobyspring.hellospring.exrate.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExRate(BigDecimal value, LocalDateTime nextUpdateAt) {
    public boolean isValid() {
        return this != null && this.nextUpdateAt.isAfter(LocalDateTime.now());
    }
}
