package tobyspring.hellospring.adapter.exrate.stub;

import tobyspring.hellospring.adapter.exrate.vo.ExRate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExRateProviderStub {
    private ExRate cache;

    public boolean getExRate(String currency) {
        if (cache.isValid()) return true;

        return false;
    }

    public void setExRate(ExRate exRate) {
        this.cache = exRate;
    }
}