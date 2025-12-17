package tobyspring.hellospring.application.required.api;

import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;

public interface ExRateExtractor {
    ErExRateData extract(String response);
}
