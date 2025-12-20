package tobyspring.hellospring.adapter.out.external.exrate.stub;

import tobyspring.hellospring.domain.payment.vo.ExRate;

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