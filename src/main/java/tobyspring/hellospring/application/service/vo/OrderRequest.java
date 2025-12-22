package tobyspring.hellospring.application.service.vo;

import java.math.BigDecimal;

public record OrderRequest(String orderName, BigDecimal totalAmount) {
}
