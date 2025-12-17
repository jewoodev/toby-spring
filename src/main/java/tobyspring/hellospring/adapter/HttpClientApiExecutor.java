package tobyspring.hellospring.adapter;

import tobyspring.hellospring.application.required.api.ApiExecutor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApiExecutor implements ApiExecutor {
    @Override
    public String execute(URI uri) {
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try (HttpClient client = HttpClient.newBuilder().build()) {
            return client.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
