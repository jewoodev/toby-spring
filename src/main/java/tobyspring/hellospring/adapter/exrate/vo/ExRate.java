package tobyspring.hellospring.adapter.exrate.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExRate(BigDecimal value, LocalDateTime nextUpdateAt) {
    public boolean isValid() {
        return this.nextUpdateAt.isAfter(LocalDateTime.now());
    }
}
