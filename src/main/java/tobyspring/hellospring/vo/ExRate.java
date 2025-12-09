package tobyspring.hellospring.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExRate(BigDecimal value, LocalDateTime nextUpdateAt) {
}
