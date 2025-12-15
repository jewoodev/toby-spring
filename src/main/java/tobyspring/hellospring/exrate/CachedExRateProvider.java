package tobyspring.hellospring.exrate;

import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;

public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider exRateProvider;
    private ExRate cache;

    public CachedExRateProvider(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    @Override
    public ExRate getExRate(String currency) throws IOException {
        if (cache.isValid()) {
            System.out.println("Cache is used.");
            return cache;
        }

        ExRate exRate = exRateProvider.getExRate(currency);
        cache = exRate;
        System.out.println("Cache is updated.");

        return exRate;
    }
}
