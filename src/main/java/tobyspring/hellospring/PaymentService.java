package tobyspring.hellospring;

import tobyspring.hellospring.vo.PaymentDecimal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foriegnCurAmount) {
        // 환율 가져오기
        // 금액 계산
        // 유효 시간 계산

        var paymentDecimal = PaymentDecimal.builder()
                .foriegnCurAmount(foriegnCurAmount)
                .exRate(BigDecimal.ZERO)
                .convertedAmount(BigDecimal.ZERO)
                .build();

        return new Payment(orderId, currency, paymentDecimal, LocalDateTime.now());
    }

    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
