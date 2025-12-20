package tobyspring.hellospring.adapter.out.external.exrate;

import tobyspring.hellospring.domain.payment.ExRateProvider;
import tobyspring.hellospring.domain.payment.vo.ExRate;

public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider exRateProvider;
    private ExRate cache;

    public CachedExRateProvider(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    @Override
    public ExRate getExRate(String currency) {
        if (cache != null && cache.isValid()) {
            System.out.println("Cache is used.");
            return cache;
        }

        ExRate exRate = exRateProvider.getExRate(currency);
        cache = exRate;
        System.out.println("Cache is updated.");

        return exRate;
    }
}
