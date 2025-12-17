package tobyspring.hellospring.adapter;

import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;

public interface ExRateExtractor {
    ErExRateData extract(String response);
}
