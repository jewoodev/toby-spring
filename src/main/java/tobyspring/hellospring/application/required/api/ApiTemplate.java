package tobyspring.hellospring.application.required.api;

import tobyspring.hellospring.adapter.out.external.exrate.dto.ErApiResponse;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class ApiTemplate {

    public final ExRate get(String url) {
        URI uri = this.createURI(url);
        String response = this.executeApi(uri);
        ErApiResponse apiResponse = this.convertResponse(response);
        return this.convertToExRate(apiResponse);
    }

    private URI createURI(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String executeApi(URI uri);
    protected abstract ErApiResponse convertResponse(String response);
    protected abstract ExRate convertToExRate(ErApiResponse apiResponse);
}
