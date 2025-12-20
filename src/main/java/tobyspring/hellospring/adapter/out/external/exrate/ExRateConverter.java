package tobyspring.hellospring.adapter.out.external.exrate;

import tobyspring.hellospring.adapter.out.external.exrate.dto.ErApiResponse;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ExRateConverter {
    public ExRate convert(ErApiResponse erApiResponse) {
        BigDecimal krwRate = erApiResponse.rates().get("KRW");

        Instant nextUpdate = Instant.ofEpochSecond(erApiResponse.time_next_update_unix());
        LocalDateTime nextUpdateAt = LocalDateTime.ofInstant(nextUpdate, ZoneId.of("Asia/Seoul"));

        return new ExRate(krwRate, nextUpdateAt);
    }
}
